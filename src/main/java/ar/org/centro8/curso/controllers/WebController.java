package ar.org.centro8.curso.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.org.centro8.curso.entities.Alumno;
import ar.org.centro8.curso.entities.Curso;
import ar.org.centro8.curso.repositories.AlumnoRepository;
import ar.org.centro8.curso.repositories.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;

@Controller
public class WebController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    private String mensajeCursos = "Ingrese un nuevo curso!";
    private String mensajeAlumnos = "Ingrese un nuevo alumno!";


    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @Operation(description = "Obtener lista de cursos")
    @GetMapping("/cursos")
    public String getCursos(@RequestParam(name = "buscarTitulo", defaultValue = "", required = false) String buscarTitulo,
            Model model) {
            model.addAttribute("curso", new Curso());
            model.addAttribute("mensajeCursos", mensajeCursos);
            List<Curso> cursos = cursoRepository.findByTituloContainingIgnoreCase(buscarTitulo);
    
            List<Integer> cantidadAlumnos = new ArrayList<>();
             for (Curso curso : cursos) {
            int cantidad = cursoRepository.getCantidadAlumnosByIdCurso(curso.getId());
              cantidadAlumnos.add(cantidad);
    }
    
    model.addAttribute("likeTitulo", cursos);
    model.addAttribute("cantidadAlumnos", cantidadAlumnos);
    
    return "cursos";
    }

    @Operation(description = "Obtener lista de alumnos")
    @GetMapping("/alumnos")
    public String getAlumnos(
            @RequestParam(name = "buscarApellido", defaultValue = "", required = false) String buscarApellido,
            Model model) {
        Alumno alumno = new Alumno();
        alumno.setEdad(18);
        model.addAttribute("alumno", alumno);
        model.addAttribute("mensajeAlumnos", mensajeAlumnos);
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("likeApellido",
                ((List<Alumno>) alumnoRepository.findByApellidoContainingIgnoreCase(buscarApellido)));
        return "alumnos";
    }

    @Operation(description = "Guardar curso")
    @PostMapping("/saveCurso")
    public String saveCurso(@ModelAttribute Curso curso) {
        try {
            cursoRepository.save(curso);
            mensajeCursos = "Se ingreso un nuevo curso id: " + curso.getId();
        } catch (Exception e) {
            mensajeCursos = "Ocurrio un error";
            System.out.println("******************************************");
            System.out.println(e);
            System.out.println("******************************************");
        }
        return "redirect:cursos";
    }

    @Operation(description = "Guardar alumno")
    @PostMapping("/saveAlumno")
    public String saveAlumno(@ModelAttribute Alumno alumno) {
        try {
            alumnoRepository.save(alumno);
            mensajeAlumnos = "Se ingreso un nuevo alumno id: " + alumno.getId();
        } catch (Exception e) {
            mensajeAlumnos = "Ocurrio un error";
            System.out.println("******************************************");
            System.out.println(e);
            System.out.println("******************************************");
        }
        return "redirect:alumnos";
    }

    @Operation(description = "Eliminar curso")
    @GetMapping("/removeCurso")
    public String removeCurso(@RequestParam(name="idBorrar", defaultValue = "0", required = false) int idBorrar, Model model){
        long cantidadAlumnos=((List<Alumno>)alumnoRepository
                                                .findAll())
                                                .stream()
                                                .filter(alumno->alumno.getIdCurso()==idBorrar)
                                                .count();
        model.addAttribute("cantidadAlumnos", cantidadAlumnos);

        if(cantidadAlumnos==0)
            cursoRepository.delete(cursoRepository.findById(idBorrar).get());   
        else
            mensajeCursos="No se puede borrar el cursos, por que tiene alumnos inscriptos!";
        return "redirect:cursos";
    }

    @Operation(description = "Eliminar alumno")
    @GetMapping("/removeAlumno")
    public String removeAlumno(@RequestParam(name="idBorrar", defaultValue = "0", required = false) int idBorrar, Model model){
           
        alumnoRepository.delete(alumnoRepository.findById(idBorrar).get());
        return "redirect:alumnos";
    }
}