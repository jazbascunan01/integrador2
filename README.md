# 🎓 Proyecto de Gestión de Estudiantes y Carreras con JPA y JPQL

Este proyecto es una aplicación en Java que utiliza **JPA** y **JPQL** para interactuar con una base de datos relacional. Gestiona entidades principales como **Estudiantes**, **Carreras** y sus relaciones en **Estudiante\_Carrera**.

---

## 🧑‍💻 Grupo de Trabajo
Este proyecto fue desarrollado por el **Grupo N° 7**

---

## 🤝 Integrantes del Grupo
- **Bascuñan Jazmín**
- **Fernández Mateo**

---

## ✅ Requisitos

1. **Java**: JDK 11 o superior.
2. **Maven**: Para gestionar las dependencias del proyecto.
3. **Base de Datos**: Una instancia de base de datos en ejecución.
4. **Librerías**:
    - `jakarta.persistence` para la implementación de JPA.
    - `opencsv` para procesar los archivos **CSV**.

---

## 📂 Estructura del Proyecto

### 1. **📌 Código Principal**
- **`Main.java`**:
    - Punto de entrada del programa.
    - Inicializa la base de datos, llena las tablas con datos desde CSV y realiza consultas como:
        - Estudiantes por género.
        - Estudiantes por carrera y ciudad.
        - Carreras con mayor cantidad de inscriptos.

### 2. **⚙️ Paquetes Principales**
- **`utils`**:
    - Contiene la clase `Menu` para gestionar la interacción con el usuario.
- **`modelo`**:
    - Clases que representan las entidades de la base de datos: `Estudiante`, `Carrera`, `Estudiante_Carrera`.
- **`repository`**:
    - Implementación de operaciones y consultas avanzadas sobre las entidades.
- **`dto`**:
    - Clases para transferir datos entre las capas de la aplicación.

### 3. **📜 Archivos CSV**
- `estudiantes.csv`, `carreras.csv`, `estudianteCarrera.csv`:
    - Datos iniciales para rellenar las tablas de la base de datos.

---

## ⚙️ Ejecución del Proyecto

### Configuración Inicial
1. Asegúrate de que tu servidor MySQL esté corriendo en `localhost`.
2. Crea una base de datos llamada `integrador2`:
    ```sql
    CREATE DATABASE integrador2;
    ```
3. Actualiza las credenciales de conexión en el código:
    - Usuario: `root`
    - Contraseña: *(cadena vacia)*


---

## 📖 Funcionalidades Principales
- **Gestión de Estudiantes y Carreras**:
    - Alta de estudiantes.
    - Matriculación de estudiantes en carreras.
- **Consultas Avanzadas**:
    - Estudiantes por género.
    - Estudiantes por carrera y ciudad.
    - Carreras con estudiantes inscriptos.
    - Reporte de inscriptos y egresados por año.
- **Manejo de Datos Iniciales**:
    - Carga de datos desde archivos CSV.
    - Creación dinámica de registros en la base de datos.

---

## 📂 Ejemplo de Interacción
1. **Menú Principal**:
    ```plaintext
    ╔═════════════════════════════════════════════╗
    ║                    MENÚ                     ║
    ╠═════════════════════════════════════════════╣
    ║ 1. Insertar un estudiante                   ║
    ║ 2. Matricular un estudiante en una carrera  ║
    ║ 3. Buscar estudiante por Libreta            ║
    ║ 4. Buscar estudiantes por género            ║
    ║ 5. Buscar estudiantes por carrera y ciudad  ║
    ║ 6. Salir                                    ║
    ╚═════════════════════════════════════════════╝
    ```

2. **Consulta de Estudiantes por Género**:
    - Ingresar el género deseado (por ejemplo, `Male` o `Female`).
    - Mostrar los resultados.

3. **Reporte de Carreras**:
    - Generar un listado de carreras con inscriptos y egresados por año, ordenado alfabética y cronológicamente.

---