import models.Alumno;
import models.AlumnoCurso;
import models.AlumnoCursoId;
import models.Curso;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Alumno alu = new Alumno("Juanbravioi", "Pérez", 20);
        Curso asig = new Curso("Matemáticas", "6");
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(asig);
            alu.setCurso(asig);
            session.persist(alu);
            tx.commit();// Guardamos la asignatura
            // Relacionamos (Matrícula)
            AlumnoCursoId idRelacion = new
                    AlumnoCursoId();
            idRelacion.setAlumnoId(alu.getId());
            idRelacion.setAsignaturaId(asig.getId());
            AlumnoCurso matricula = new
                    AlumnoCurso(idRelacion);
            matricula.setAlumno(alu);
            matricula.setAsignatura(asig);
            session.persist(matricula);
            tx.commit();
            System.out.println(" C: Alumno y Matrícula creados.");
        }
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            // Leer uno por ID
            Alumno buscado = session.get(Alumno.class, alu.getId());
            System.out.println(" R: Leído alumno: " +
                    buscado.getNombre());
            // Leer todos (HQL)
            List<Alumno> lista = session.createQuery("from Alumno",
                    Alumno.class).list();
            System.out.println("Total alumnos: " + lista.size());
        }


    }
}