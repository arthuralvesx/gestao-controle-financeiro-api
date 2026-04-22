package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

import java.time.LocalDate;

public record DespesaRequestDto (
    String nome,
    Double valor,
    LocalDate data,
    String categoria 
) {}
