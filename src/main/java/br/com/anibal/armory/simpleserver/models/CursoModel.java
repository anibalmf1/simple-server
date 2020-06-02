package br.com.anibal.armory.simpleserver.models;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cursos", itemRelation = "curso")
public class CursoModel extends RepresentationModel<CursoModel> {

    String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
