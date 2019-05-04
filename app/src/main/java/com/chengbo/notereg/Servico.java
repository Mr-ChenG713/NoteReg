package com.chengbo.notereg;

public class Servico {

    private int idS;
    private String Tipo_servico;


    public Servico(int idS, String tipo_servico) {
        this.idS = idS;
        Tipo_servico = tipo_servico;
    }

    public String getTipo_servico() {
        return Tipo_servico;
    }

    public void setTipo_servico(String tipo_servico) {
        Tipo_servico = tipo_servico;
    }

    public int getIdS() {
        return idS;
    }

    public void setIdS(int idS) {
        this.idS = idS;
    }
}
