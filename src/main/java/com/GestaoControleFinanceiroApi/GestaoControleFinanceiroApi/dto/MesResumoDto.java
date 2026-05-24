package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

import java.util.List;

public record MesResumoDto(
    String mes,
    Double receitas,
    Double despesas,
    Double saldo,
    List<CategoriaResumoDto> categorias
) {}
