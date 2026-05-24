package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

public record CategoriaResumoDto(
    String categoria,
    Double total,
    Double percentual
) {}
