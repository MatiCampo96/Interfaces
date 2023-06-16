package ar.org.centro8.curso.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.org.centro8.curso.entities.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Integer> {

        //La implementación de la query es automatica en el autowired
        List<Curso>findByTitulo(String titulo);

        //@Query("select c from cursos c where c.titulo like  %?1%")
        List<Curso>findByTituloContainingIgnoreCase(String titulo);
    
        List<Curso>findByProfesorContainingIgnoreCase(String profesor);

         @Query(value = "SELECT COUNT(*) FROM alumnos WHERE idCurso = :idCurso", nativeQuery = true)
         int getCantidadAlumnosByIdCurso(@Param("idCurso") int idCurso);
        
        /*
    
            Diferencias entre extender desde CrudRepository o JpaRepository
            https://www.geeksforgeeks.org/spring-boot-difference-between-crudrepository-and-jparepository/
    
            formas de escribir las queries en el nombre del método
            https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    
        */

}
