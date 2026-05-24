package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

public record AuthResponseDto(
    boolean autenticado,
    Long usuarioId,
    String email,
    String mensagem
) {}
