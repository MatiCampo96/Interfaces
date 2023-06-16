package ar.org.centro8.curso.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.org.centro8.curso.entities.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Integer>{
    List<Alumno>findByApellidoContainingIgnoreCase(String apellido);
}
