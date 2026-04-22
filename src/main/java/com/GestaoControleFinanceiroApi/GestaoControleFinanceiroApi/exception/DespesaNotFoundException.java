package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.exception;

public class DespesaNotFoundException extends  RuntimeException {
    public DespesaNotFoundException(Long id){
        super("Despesa não encontrada");
    }
}
