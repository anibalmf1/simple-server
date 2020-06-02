package br.com.anibal.armory.simpleserver.entities;

public class Curso {

    private String id;

    private String nome;

    public Curso() {
    }

    public Curso(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
