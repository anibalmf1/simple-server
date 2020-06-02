package br.com.anibal.armory.simpleserver.controllers.hateoas;

import br.com.anibal.armory.simpleserver.controllers.hateoas.assemblers.CursoModelAssembler;
import br.com.anibal.armory.simpleserver.entities.Curso;
import br.com.anibal.armory.simpleserver.exceptions.ConflictException;
import br.com.anibal.armory.simpleserver.models.CursoModel;
import br.com.anibal.armory.simpleserver.services.CursoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(HateoasCursosController.REQUEST_MAPPING)
@CrossOrigin(allowedHeaders = "*")
public class HateoasCursosController {

    public static final String REQUEST_MAPPING = "/hateoas/cursos";

    Logger logger = LoggerFactory.getLogger(HateoasCursosController.class);

    @Autowired
    CursoService service;

    @Autowired
    CursoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<CursoModel>> listaCursos() throws ExecutionException, InterruptedException {
        CollectionModel<CursoModel> collectionModel = assembler.toCollectionModel(service.getCursos());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoModel> getCurso(@PathVariable String id) throws ExecutionException, InterruptedException {
        return service.getCurso(id)
                .map(curso -> ResponseEntity.ok(assembler.toModel(curso)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CursoModel> criarCurso(@RequestBody Curso curso) throws ExecutionException, InterruptedException {
        try {
            Curso created = service.criarCurso(curso);

            CursoModel model = assembler.toModel(created);

            return ResponseEntity
                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(model);
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
    public ResponseEntity<CursoModel> atualizarCurso(@PathVariable String id, @RequestBody Curso curso) throws ExecutionException, InterruptedException {
        return service.atualizarCurso(id, curso)
                .map(updated -> ResponseEntity.ok(assembler.toModel(updated)))
                .orElse(ResponseEntity.notFound().build());
    }
}
