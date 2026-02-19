package models;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@Table(name = "Alumno_Asignatura")
public class AlumnoCurso {
    @EmbeddedId
    private AlumnoCursoId id;
    @MapsId("alumnoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;
    @MapsId("asignaturaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Curso asignatura;
    public AlumnoCurso(AlumnoCursoId id) {
        this.id = id;
    }
    public AlumnoCurso() {
    }
    public AlumnoCursoId getId() {
        return id;
    }
    public void setId(AlumnoCursoId id) {
        this.id = id;
    }
    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    public Curso getAsignatura() {
        return asignatura;
    }
    public void setAsignatura(Curso asignatura) {
        this.asignatura = asignatura;
    }
}