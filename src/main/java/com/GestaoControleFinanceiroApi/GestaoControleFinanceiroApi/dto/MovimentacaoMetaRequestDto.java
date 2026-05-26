package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

import java.time.LocalDate;

public record MovimentacaoMetaRequestDto(
    Double valor,
    LocalDate data,
    String descricao
) {}
