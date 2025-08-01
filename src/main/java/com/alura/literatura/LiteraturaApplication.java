package com.alura.literatura;

import com.alura.literatura.entidad.Autor;
import com.alura.literatura.entidad.Libro;
import com.alura.literatura.model.ConsolaUtils;
import com.alura.literatura.service.IAutorRepo;
import com.alura.literatura.service.ILibroRepo;
import com.alura.literatura.service.LibroAutorBusquedaService;
import com.alura.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroService libroService;
	@Autowired
	private LibroAutorBusquedaService gutendexService;
	@Autowired
	private ILibroRepo libroRepo;
	@Autowired
	private IAutorRepo autorRepo;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner sc = new Scanner(System.in);
		int opcion;
		do {
			System.out.println("\n--- Menú LiterAlura ---");
			System.out.println("1. Buscar libro por título");
			System.out.println("2. Listar todos los libros");
			System.out.println("3. Listar libros por idioma");
			System.out.println("4. Listar autores");
			System.out.println("5. Listar autores vivos en un año");
			System.out.println("0. Salir");
			opcion = Integer.parseInt(sc.nextLine());

			switch (opcion) {
				case 1 -> {
					System.out.print("Título: ");
					String titulo = sc.nextLine();

					// Buscamos todos los libros con ese título
					List<Libro> librosExistentes = libroRepo.findByTituloIgnoreCase(titulo);
					if (!librosExistentes.isEmpty()) {
						System.out.println("El libro ya está registrado:");
						librosExistentes.forEach(ConsolaUtils::imprimirLibro);
						break;
					}

					// Si no está, lo traemos desde gutendex(api)
					Optional<Libro> libroApi = gutendexService.buscarLibroPorTitulo(titulo);
					if (libroApi.isPresent()) {
						Libro libroApiData = libroApi.get();

						// Sustituimos los autores nuevos por versiones gestionadas
						List<Autor> autoresPersistidos = new ArrayList<>();
						for (Autor autor : libroApiData.getAutores()) {
							Optional<Autor> existente = autorRepo.findByNombreIgnoreCase(autor.getNombre());
							Autor autorPersistido = existente.orElseGet(() -> autorRepo.save(autor));
							autoresPersistidos.add(autorPersistido);
						}

						// Creamos nuevo libro con autores ya persistidos
						Libro libro = new Libro();
						libro.setTitulo(libroApiData.getTitulo());
						libro.setDescargas(libroApiData.getDescargas());
						libro.setIdiomas(libroApiData.getIdiomas());
						libro.setAutores(autoresPersistidos);

						libroRepo.save(libro);

						System.out.println("Libro guardado correctamente.");
						ConsolaUtils.imprimirLibro(libro);
					} else {
						System.out.println("No se encontró ningún libro con ese título.");
					}
				}
				case 2 -> libroService.obtenerTodos().forEach(ConsolaUtils::imprimirLibro);


				case 3 -> {
					System.out.println("Ingrese el idioma para buscar los libros:");
					System.out.println("- es: español");
					System.out.println("- en: inglés");
					System.out.println("- fr: francés");
					System.out.println("- pt: portugués");
					System.out.print("Idioma: ");
					String idioma = sc.nextLine();
					libroRepo.findByIdiomasContainingIgnoreCase(idioma).forEach(ConsolaUtils::imprimirLibro);
				}
				case 4 -> autorRepo.findAll().forEach(ConsolaUtils::imprimirAutorConLibros);
				case 5 -> {
					System.out.print("Año: ");
					int año = Integer.parseInt(sc.nextLine());
					autorRepo.findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(año, año)
							.forEach(ConsolaUtils::imprimirAutorConLibros);
				}
			}
		} while (opcion != 0);
	}
}