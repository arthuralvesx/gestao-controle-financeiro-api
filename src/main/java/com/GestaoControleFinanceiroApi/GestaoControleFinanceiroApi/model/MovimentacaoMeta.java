package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimentacoes_meta")
public class MovimentacaoMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meta_id", nullable = false)
    private Metas meta;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoMeta tipo;

    private Double valor;
    private LocalDate data;
    private String descricao;

    public MovimentacaoMeta() {}

    public MovimentacaoMeta(Metas meta, TipoMovimentacaoMeta tipo, Double valor, LocalDate data, String descricao) {
        this.meta = meta;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Metas getMeta() { return meta; }
    public void setMeta(Metas meta) { this.meta = meta; }
    public TipoMovimentacaoMeta getTipo() { return tipo; }
    public void setTipo(TipoMovimentacaoMeta tipo) { this.tipo = tipo; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
