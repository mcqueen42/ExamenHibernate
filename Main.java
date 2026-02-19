import models.Alumno;
import models.Curso;
import models.AlumnoCurso;
import models.AlumnoCursoId;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ======================
        // 1. INSERT: Crear Alumno asociado a curso id=1
        // ======================
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Buscar curso con id=1
            Curso curso1 = session.get(Curso.class, 1L);
            if (curso1 == null) {
                System.out.println("No existe curso con id=1");
                tx.rollback();
            } else {
                Alumno alumno = new Alumno("NuevoAlumno", "nuevo@dam.com", 20);
                alumno.setCurso(curso1);  // asociar al curso
                session.persist(alumno);
                tx.commit();
                System.out.println("INSERT: Alumno creado con id=" + alumno.getId());
            }
        }

        // ======================
        // 2. LIST: listar todos los alumnos por nombre
        // ======================
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Alumno> alumnos = session.createQuery("FROM Alumno a ORDER BY a.nombre", Alumno.class)
                    .list();
            System.out.println("LIST: Todos los alumnos");
            for (Alumno a : alumnos) {
                System.out.println(a.getId() + " - " + a.getNombre() + " - Curso: " + a.getCurso().getNombre());
            }
        }

        // ======================
        // 3. UPDATE: cambiar curso de alumno existente a curso_id=2
        // ======================
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Tomamos el primer alumno de ejemplo
            Alumno aModificar = session.get(Alumno.class, 1L);
            Curso nuevoCurso = session.get(Curso.class, 2L);

            if (aModificar != null && nuevoCurso != null) {
                aModificar.setCurso(nuevoCurso);
                session.merge(aModificar); // sincroniza cambios
                tx.commit();
                System.out.println("UPDATE: Alumno " + aModificar.getNombre() + " ahora en curso " + nuevoCurso.getNombre());
            } else {
                tx.rollback();
                System.out.println("UPDATE: Alumno o curso no encontrado");
            }
        }

        // ======================
        // 4. DELETE: eliminar un alumno por id
        // ======================
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Alumno aEliminar = session.get(Alumno.class, 1L); // ejemplo
            if (aEliminar != null) {
                session.remove(aEliminar);
                tx.commit();
                System.out.println("DELETE: Alumno eliminado");
            } else {
                tx.rollback();
                System.out.println("DELETE: Alumno no encontrado");
            }
        }

        // ======================
        // HQL Preparadas
        // ======================

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // A1: Listar alumnos de un curso por id, ordenados por edad descendente
            List<Alumno> listaCurso = session.createQuery(
                            "FROM Alumno a WHERE a.curso.id = :cursoId ORDER BY a.edad DESC", Alumno.class)
                    .setParameter("cursoId", 2L)
                    .list();
            System.out.println("A1: Alumnos curso 2");
            listaCurso.forEach(a -> System.out.println(a.getNombre() + " - " + a.getEdad()));

            // A2: Buscar por dominio email case-insensitive
            List<Alumno> listaEmail = session.createQuery(
                            "FROM Alumno a WHERE lower(a.email) LIKE :dom ORDER BY a.nombre", Alumno.class)
                    .setParameter("dom", "%@dam.com%")
                    .list();
            System.out.println("A2: Alumnos con email @dam.com");
            listaEmail.forEach(a -> System.out.println(a.getNombre() + " - " + a.getEmail()));

            // A3: Proyección a Object[] (nombre, curso)
            List<Object[]> listaProyeccion = session.createQuery(
                            "SELECT a.nombre, a.curso.nombre FROM Alumno a WHERE a.edad >= :minEdad ORDER BY a.nombre", Object[].class)
                    .setParameter("minEdad", 18)
                    .list();
            System.out.println("A3: Proyección nombre y curso (edad>=18)");
            listaProyeccion.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));

            // A4: DTO (AlumnoCursoDTO)
            // Necesitas crear la clase com.dam.dto.AlumnoCursoDTO con constructor (String nombre, int edad, String curso)
            List<models.AlumnoCursoDTO> listaDTO = session.createQuery(
                            "SELECT new models.AlumnoCursoDTO(a.nombre, a.edad, a.curso.nombre) " +
                                    "FROM Alumno a WHERE a.curso.nivel = :nivel ORDER BY a.edad DESC",
                            models.AlumnoCursoDTO.class)
                    .setParameter("nivel", "3")
                    .list();
            System.out.println("A4: DTO Alumnos curso nivel 3");
            listaDTO.forEach(dto -> System.out.println(dto));
        }


        HibernateUtil.shutdown();
    }
}
