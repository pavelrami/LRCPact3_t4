# LRCPact3_t4 — CRUD con Spring Boot 4.1.2, JPA y MySQL

**Autor:** León Ramírez Carlos Pavel · **Materia:** Programación Web · **Actividad 3**

## Objetivo
Aplicación monolítica Spring Boot: vistas Thymeleaf y REST dentro del mismo proyecto, con persistencia real en MySQL y relación entre Categoria (1) y Materia (N). No utiliza React ni un frontend separado.

## Modelo de datos

- **Categoria:** id (PK), nombre, descripcion.
- **Materia:** id (PK), nombre, semestre, descripcion, categoria_id (FK).
- `Categoria` contiene `@OneToMany(mappedBy="categoria")`.
- `Materia` contiene `@ManyToOne` y `@JoinColumn(name="categoria_id")`.
- **Regla:** una categoría puede tener muchas materias; cada materia pertenece a una categoría.

## Tecnologías
Java 21; Spring Boot 4.1.2; Spring MVC; Thymeleaf; Spring Data JPA (Hibernate); MySQL; Maven; Postman; Ubuntu VPS.

## Capas

- `entity/`: `Materia` y `Categoria` con relación JPA.
- `repository/`: dos interfaces que extienden `JpaRepository`.
- `service/`: validación, transacciones y lógica del CRUD.
- `dto/`: `MateriaDTO`, `MateriaRespuesta`, `CategoriaRespuesta`.
- `controller/`: controladores MVC/REST y manejo de errores.
- `resources/templates`: listado, formulario y detalle HTML.

## Base de datos local
Instala MySQL Server en Windows e inicia el servicio. Puedes administrar la base con MySQL Workbench o la consola de MySQL. En su cliente SQL ejecuta (sustituye la contraseña y conserva la clave en privado):

```sql
CREATE DATABASE IF NOT EXISTS lrcp_act3 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'lrcp_act3'@'localhost' IDENTIFIED BY 'CAMBIA_POR_UNA_CLAVE_SEGURA';
CREATE USER IF NOT EXISTS 'lrcp_act3'@'127.0.0.1' IDENTIFIED BY 'CAMBIA_POR_UNA_CLAVE_SEGURA';
GRANT ALL PRIVILEGES ON lrcp_act3.* TO 'lrcp_act3'@'localhost';
GRANT ALL PRIVILEGES ON lrcp_act3.* TO 'lrcp_act3'@'127.0.0.1';
```

En Git Bash, antes de iniciar Spring Boot:

```bash
read -r -s -p "Clave MySQL: " DB_PASSWORD; echo
export DB_PASSWORD
./mvnw.cmd spring-boot:run
```

Asegura que los comandos `mvnw.cmd`, `mvnw` y la carpeta `.mvn` existan; si usaste el ZIP de acompañamiento, cópialos de tu proyecto Act2.

## Web local

- Listar y filtrar: http://localhost:8080/materias
- Crear: http://localhost:8080/materias/nueva
- Detalle: http://localhost:8080/materias/1
- Editar: http://localhost:8080/materias/1/editar

## REST y Postman

| Método | URL | Función |
| --- | --- | --- |
| GET | `/api/categorias` | Ver categorías con sus identificadores |
| GET | `/api/materias` | Listar materias con `categoriaNombre` |
| GET | `/api/materias/1` | Ver detalle |
| POST | `/api/materias` | Crear |
| PUT | `/api/materias/1` | Actualizar |
| DELETE | `/api/materias/1` | Eliminar |

Ejemplo POST (elige el `categoriaId` devuelto por GET `/api/categorias`):

```json
{"nombre":"Bases de datos","semestre":7,"categoriaId":1,"descripcion":"Diseño de bases de datos relacionales"}
```

Importa `postman/LRCPact3_t4.postman_collection.json`. Ejecuta primero GET categorías, luego POST, GET, PUT y DELETE. El POST guarda automáticamente el ID retornado en la variable `materiaId`. No reutilices un ID eliminado.

## Persistencia demostrable

1. Crea una materia desde la web o Postman.
2. Comprueba el nombre de su categoría en el catálogo.
3. En SQL ejecuta `scripts/consulta_bd.sql` para observar la relación y su clave foránea.
4. Detén Spring Boot, vuelve a iniciarlo y comprueba que el registro aún existe.
5. Actualiza y elimina desde web o Postman y verifica los cambios en SQL.

> Se generan tres categorías y tres materias de demostración únicamente al iniciar una BD sin categorías. `spring.jpa.hibernate.ddl-auto=update` se utiliza para el prototipo académico; no es una estrategia de migraciones de producción.

## Azure VPS

- Act1: `http://65.52.8.72:8081/proyecto`
- Act2: `http://65.52.8.72:8083/mvc/materias`
- Act3, después de desplegar: `http://65.52.8.72:8084/materias`

El servidor MySQL del VPS debe escuchar solo en `127.0.0.1:3306`; **no abrir 3306 en las reglas de Azure**. Las credenciales de la base de datos se pasan con variables de entorno, nunca mediante el código ni Git.

## Evidencias pendientes

**Antes de entregar:** agrega capturas reales a `docs/capturas/` y sustituye estas líneas por enlaces a los PNG:

- Entidades y anotaciones JPA; creación; lista con `categoriaNombre`; actualización; eliminación.
- Consulta SQL que muestre FK y JOIN; Postman (con POST/PUT/DELETE); puertos 8081/8083/8084.

La ausencia de imágenes reales en este README implica que **todavía no está completado el requisito de evidencias**.

## Nota de MySQL

El proyecto usa el driver `com.mysql:mysql-connector-j` y la URL JDBC `jdbc:mysql://127.0.0.1:3306/lrcp_act3`. No publiques contraseñas en GitHub ni abras el puerto 3306 al internet. En Windows y el VPS debes instalar MySQL Server y crear la base y el usuario por separado.
