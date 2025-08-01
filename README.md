
# 📚 LiterAlura - Biblioteca de consola

Proyecto de consola desarrollado en Java con Spring Boot que permite consultar libros utilizando la API pública de [Gutendex](https://gutendex.com/), almacenarlos en una base de datos PostgreSQL y explorarlos desde un menú interactivo.

---

## 🚀 Funcionalidades

- Buscar libro por título (consulta API y guarda localmente)
- Listar libros registrados
- Filtrar libros por idioma
- Listar todos los autores registrados
- Consultar autores que estaban vivos en un año determinado

---

## 🛠️ Tecnologías utilizadas

- Java 17+
- Spring Boot 3
- PostgreSQL
- JPA (Hibernate)
- Maven

---

## 🗂️ Estructura del proyecto

```bash
src/
├── main/
│   ├── java/com/alura/literatura/
│   │   ├── entidad/         # Entidades JPA (Libro, Autor)
│   │   ├── model/           # Modelos para impresión y utilidades
│   │   ├── service/         # Servicios y repositorios
│   │   └── LiteraturaApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

---

## 🧪 Ejecución local

### Requisitos:

- PostgreSQL instalado y corriendo
- Java 17+
- Maven

### Configurar base de datos

1. Crea una base de datos vacía llamada `literatura_alura` (o el nombre que prefieras).
2. Edita el archivo `application.properties` con tus credenciales locales:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literatura_alura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```


---

## ✨ Ejemplo del menú

```text
--- Menú LiterAlura ---
1. Buscar libro por título
2. Listar todos los libros
3. Listar libros por idioma
4. Listar autores
5. Listar autores vivos en un año
0. Salir
```

---

## 📦 API externa

Se utiliza [Gutendex](https://gutendex.com/) para obtener datos abiertos de libros del Proyecto Gutenberg.

---

## 📌 Notas

- El proyecto utiliza persistencia en base de datos, por lo que se recomienda tener PostgreSQL corriendo antes de ejecutar.
- Se evita duplicar autores en la base de datos gracias a búsquedas por nombre.

---

## 📝 Licencia

Este proyecto es parte del curso **Alura: Inmersión Java** y tiene fines educativos.
