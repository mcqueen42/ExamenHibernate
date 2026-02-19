package models;


public class AlumnoCursoDTO {
    private String nombre;
    private int edad;
    private String curso;

    public AlumnoCursoDTO(String nombre, int edad, String curso) {
        this.nombre = nombre;
        this.edad = edad;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return nombre + " - " + edad + " años - Curso: " + curso;
    }
}
