package br.com.anibal.armory.simpleserver.models;

import java.util.List;

public class LibResponse {
    List<Lib> results;

    public LibResponse(List<Lib> results) {
        this.results = results;
    }

    public LibResponse() {
    }

    public List<Lib> getResults() {
        return results;
    }

    public void setResults(List<Lib> results) {
        this.results = results;
    }
}
