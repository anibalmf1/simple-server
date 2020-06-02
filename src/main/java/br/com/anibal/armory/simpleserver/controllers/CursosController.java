package br.com.anibal.armory.simpleserver.controllers;

import br.com.anibal.armory.simpleserver.entities.Curso;
import br.com.anibal.armory.simpleserver.exceptions.ConflictException;
import br.com.anibal.armory.simpleserver.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(CursosController.REQUEST_MAPPING)
@CrossOrigin(allowedHeaders = "*")
public class CursosController {

    public static final String REQUEST_MAPPING = "/cursos";

    @Autowired
    CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> listaCursos() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.getCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCurso(@PathVariable String id) throws ExecutionException, InterruptedException {
        return service.getCurso(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> criarCurso(@RequestBody Curso curso) throws ExecutionException, InterruptedException {
        try {
            Curso created = service.criarCurso(curso);

            return ResponseEntity.ok(created);
        } catch ( ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity apagarCurso(@PathVariable String id) throws ExecutionException, InterruptedException {
        if(service.deleteCurso(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable String id, @RequestBody Curso curso) throws ExecutionException, InterruptedException {
        return service.atualizarCurso(id, curso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
