# API de Consulta de Noticias – ABC

Este proyecto es una API REST en Spring Boot que permite consultar noticias desde el sitio de ABC Color utilizando `Jsoup`.

## 🧰 Requisitos Previos

- Java 17 o superior
- Maven 3.8+
- Internet (para acceder a https://www.abc.com.py)

## 🚀 Cómo Iniciar el Servicio

1. Cloná el repositorio o descargá el proyecto.
2. Navegá a la carpeta del proyecto donde se encuentra el archivo `pom.xml`.
3. Ejecutá los siguientes comandos:

```bash
mvn clean install
mvn spring-boot:run
```

El servicio se iniciará en: `http://localhost:8080`

## 🔍 Endpoint Disponible

### `GET /api/v1/consulta?q={texto}`

Consulta noticias de ABC filtradas por el texto provisto.

#### Parámetros:

| Nombre | Tipo   | Requerido | Descripción                      |
|--------|--------|-----------|----------------------------------|
| q      | string | ✅        | Término de búsqueda de noticias |

#### Respuesta Exitosa – `200 OK`

```json
[
  {
    "fecha": "14 de agosto de 2025",
    "link": "https://www.abc.com.py/noticia/123",
    "foto": "https://www.abc.com.py/imagen.jpg",
    "titulo": "Título de la noticia",
    "resumen": "Resumen de la noticia"
  },
  ...
]
```

## ❗ Posibles Errores

### `400 Bad Request`

```json
{
  "codigo": "g268",
  "error": "Parámetros inválidos"
}
```

> Ocurre si `q` no está presente o está vacío.

---

### `404 Not Found`

```json
{
  "codigo": "g267",
  "error": "No se encuentran noticias para el texto: {TEXTO DE BÚSQUEDA}"
}
```

> Ocurre si no se encuentran noticias relacionadas al término `q`.

---

### `500 Internal Server Error`

```json
{
  "codigo": "g100",
  "error": "Error interno del servidor"
}
```

> Error genérico ante cualquier excepción inesperada.

---

## 🧪 Ejemplo de Uso

```bash
curl -X GET "http://localhost:8080/api/v1/consulta?q=paraguay"
```

