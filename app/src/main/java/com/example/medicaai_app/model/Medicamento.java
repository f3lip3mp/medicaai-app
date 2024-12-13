package com.example.medicaai_app.model;

import com.google.firebase.firestore.DocumentId;

public class Medicamento {

    @DocumentId // A anotação para o ID do documento no Firestore
    private String id; // Campo para armazenar o ID do medicamento no Firestore

    private int medicamentoImg;
    private String medicamentoNome;
    private String medicamentoDescricao;
    private String medicamentoIndicacao;
    private String classeFarmaceutica;
    private String contraIndicacao;

    // Construtor vazio necessário para o Firestore
    public Medicamento() {}

    public String getClasseFarmaceutica() {
        return classeFarmaceutica;
    }

    public void setClasseFarmaceutica(String classeFarmaceutica) {
        this.classeFarmaceutica = classeFarmaceutica;
    }

    public String getContraIndicacao() {
        return contraIndicacao;
    }

    public void setContraIndicacao(String contraIndicacao) {
        this.contraIndicacao = contraIndicacao;
    }

    // Construtor para criar um objeto Medicamento
    public Medicamento(int medicamentoImg, String medicamentoNome, String medicamentoDescricao, String medicamentoIndicacao, String classeFarmaceutica, String contraIndicacao) {
        this.medicamentoImg = medicamentoImg;
        this.medicamentoNome = medicamentoNome;
        this.medicamentoDescricao = medicamentoDescricao;
        this.medicamentoIndicacao = medicamentoIndicacao;
        this.classeFarmaceutica = classeFarmaceutica;
        this.contraIndicacao = contraIndicacao;
    }

    // Getters e Setters para cada campo

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMedicamentoImg() {
        return medicamentoImg;
    }

    public void setMedicamentoImg(int medicamentoImg) {
        this.medicamentoImg = medicamentoImg;
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

    public String getMedicamentoIndicacao() {
        return medicamentoIndicacao;
    }

    public void setMedicamentoIndicacao(String medicamentoIndicacao) {
        this.medicamentoIndicacao = medicamentoIndicacao;
    }
}
