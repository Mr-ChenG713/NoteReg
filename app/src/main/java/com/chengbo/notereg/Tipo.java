package com.chengbo.notereg;

public class Tipo {

    private int idT;
    private String genero;


    public Tipo(int idT, String genero) {
        this.idT = idT;
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdT() {
        return idT;
    }

    public void setIdT(int idT) {
        this.idT = idT;
    }
}
