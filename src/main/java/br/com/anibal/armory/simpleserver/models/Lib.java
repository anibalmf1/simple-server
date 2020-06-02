package br.com.anibal.armory.simpleserver.models;

public class Lib {
    String name;
    String version;

    public Lib(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public Lib() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
