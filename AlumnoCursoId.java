package models;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class AlumnoCursoId implements Serializable {
    private static final long serialVersionUID = -
            6206397753338092478L;
    @Column(name = "alumno_id", nullable = false)
    private Long alumnoId;
    @Column(name = "asignatura_id", nullable = false)
    private Long asignaturaId;
    public Long getAlumnoId() {
        return alumnoId;
    }
    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }
    public Long getAsignaturaId() {
        return asignaturaId;
    }
    public void setAsignaturaId(Long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        AlumnoCursoId entity = (AlumnoCursoId) o;
        return Objects.equals(this.alumnoId, entity.alumnoId) &&
                Objects.equals(this.asignaturaId,
                        entity.asignaturaId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(alumnoId, asignaturaId);
    }
}
