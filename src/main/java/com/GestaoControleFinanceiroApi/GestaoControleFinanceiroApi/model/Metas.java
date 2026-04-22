package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "metas")
public class Metas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    //fazer validacao
    private Double valorMeta;
    private Double valor;
    private String metaCategoria; //virar enum

    public Metas(){}
    
    public Metas(Double valorMeta, Double valor, String metaCategoria) {
        this.valorMeta = valorMeta;
        this.valor = valor;
        this.metaCategoria = metaCategoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getValorMeta() { return valorMeta; }
    public void setValorMeta(Double valorMeta) { this.valorMeta = valorMeta; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getMetaCategoria() { return metaCategoria; }
    public void setMetaCategoria(String metaCategoria) { this.metaCategoria = metaCategoria; }
}

