package com.chengbo.notereg;

import java.util.Date;

public class Registos {

    private int idR;
    private double montante;
    private Date data;
    private String decricao;


    public Registos(int idR, double montante, Date data, String decricao) {
        this.idR = idR;
        this.montante = montante;
        this.data = data;
        this.decricao = decricao;
    }

    public Date getData() {
        return data;
    }

    public double getMontante() {
        return montante;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public void setMontante(double montante) {
        this.montante = montante;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }
}

