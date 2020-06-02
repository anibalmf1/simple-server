package br.com.anibal.armory.simpleserver.controllers.hateoas.assemblers;

import br.com.anibal.armory.simpleserver.controllers.hateoas.HateoasCursosController;
import br.com.anibal.armory.simpleserver.entities.Curso;
import br.com.anibal.armory.simpleserver.models.CursoModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CursoModelAssembler extends RepresentationModelAssemblerSupport<Curso, CursoModel> {

    public CursoModelAssembler() {
        super(HateoasCursosController.class, CursoModel.class);
    }

    @Override
    public CursoModel toModel(Curso entity) {
        CursoModel model = createModelWithId(entity.getId(), entity);
        model.setNome(entity.getNome());
        return model;
    }

    @Override
    public CollectionModel<CursoModel> toCollectionModel(Iterable<? extends Curso> entities) {
        CollectionModel<CursoModel> collectionModel = super.toCollectionModel(entities);
        return collectionModel;
    }
}
