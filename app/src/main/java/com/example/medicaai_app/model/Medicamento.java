package com.example.medicaai_app.model;

public class Medicamento {
    int medicamentoImg;
    String medicamentoNome;
    String medicamentoDescricao;
    String medicamentoIndicacao;

    public Medicamento(int medicamentoImg, String medicamentoNome, String medicamentoDescricao, String medicamentoIndicacao) {
        this.medicamentoImg = medicamentoImg;
        this.medicamentoNome = medicamentoNome;
        this.medicamentoDescricao = medicamentoDescricao;
        this.medicamentoIndicacao = medicamentoIndicacao;
    }

    public int getMedicamentoImg() {
        return medicamentoImg;
    }

    public void setMedicamentoImg(int medicamentoImg) {
        this.medicamentoImg = medicamentoImg;
    }

    public String getMedicamentoIndicacao() {
        return medicamentoIndicacao;
    }

    public void setMedicamentoIndicacao(String medicamentoIndicacao) {
        this.medicamentoIndicacao = medicamentoIndicacao;
    }

    public String getMedicamentoNome() {
        return medicamentoNome;
    }

    public void setMedicamentoNome(String medicamentoNome) {
        this.medicamentoNome = medicamentoNome;
    }

    public String getMedicamentoDescricao() {
        return medicamentoDescricao;
    }

    public void setMedicamentoDescricao(String medicamentoDescricao) {
        this.medicamentoDescricao = medicamentoDescricao;
    }
}
