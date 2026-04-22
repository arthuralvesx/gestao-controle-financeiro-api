package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

import java.time.LocalDate;

public record ReceitaRequestDto (
    LocalDate data,
    Double valor
) {}
