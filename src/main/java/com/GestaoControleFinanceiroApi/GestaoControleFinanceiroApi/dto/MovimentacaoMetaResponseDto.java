package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

import java.time.LocalDate;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.MovimentacaoMeta;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.TipoMovimentacaoMeta;

public record MovimentacaoMetaResponseDto(
    Long id,
    Long metaId,
    TipoMovimentacaoMeta tipo,
    Double valor,
    LocalDate data,
    String descricao
) {
    public static MovimentacaoMetaResponseDto from(MovimentacaoMeta movimentacao) {
        return new MovimentacaoMetaResponseDto(
            movimentacao.getId(),
            movimentacao.getMeta().getId(),
            movimentacao.getTipo(),
            movimentacao.getValor(),
            movimentacao.getData(),
            movimentacao.getDescricao()
        );
    }
}
