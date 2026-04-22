package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

public record UsuarioRequestDto (  
    //fazer validacao de dado
    String email,
    String senha
) {}
