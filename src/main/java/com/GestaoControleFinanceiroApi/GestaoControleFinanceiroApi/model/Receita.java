package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "receita")
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private Double valor;
    
    public Receita(){}

    public Receita(LocalDate data, Double valor) {
        this.data = data;
        this.valor = valor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}
