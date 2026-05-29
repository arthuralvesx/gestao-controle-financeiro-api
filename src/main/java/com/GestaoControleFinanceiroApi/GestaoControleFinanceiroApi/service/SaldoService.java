package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.stereotype.Service;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.DespesaRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.MetasRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.MovimentacaoMetaRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.ReceitaRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.TipoMovimentacaoMeta;

@Service
public class SaldoService {
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    private final MetasRepository metasRepository;
    private final MovimentacaoMetaRepository movimentacaoMetaRepository;

    public SaldoService(
        ReceitaRepository receitaRepository,
        DespesaRepository despesaRepository,
        MetasRepository metasRepository,
        MovimentacaoMetaRepository movimentacaoMetaRepository
    ) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
        this.metasRepository = metasRepository;
        this.movimentacaoMetaRepository = movimentacaoMetaRepository;
    }

    public double totalReceitas() {
        return receitaRepository.findAll().stream().mapToDouble(r -> safe(r.getValor())).sum();
    }

    public double totalDespesas() {
        return despesaRepository.findAll().stream().mapToDouble(d -> safe(d.getValor())).sum();
    }

    public double totalGuardadoMetas() {
        return metasRepository.findAll().stream().mapToDouble(m -> safe(m.getValor())).sum();
    }

    public double totalGuardadoMetasAte(LocalDate data) {
        if (movimentacaoMetaRepository.count() == 0) {
            return totalGuardadoMetas();
        }

        return movimentacaoMetaRepository.findByDataLessThanEqual(data).stream()
            .mapToDouble(m -> m.getTipo() == TipoMovimentacaoMeta.ENTRADA ? safe(m.getValor()) : -safe(m.getValor()))
            .sum();
    }

    public double totalGuardadoMetasNoMes(YearMonth mes) {
        return movimentacaoMetaRepository.findAll().stream()
            .filter(movimentacao -> movimentacao.getData() != null && YearMonth.from(movimentacao.getData()).equals(mes))
            .mapToDouble(movimentacao -> movimentacao.getTipo() == TipoMovimentacaoMeta.ENTRADA
                ? safe(movimentacao.getValor())
                : -safe(movimentacao.getValor()))
            .sum();
    }

    public double saldoDisponivel() {
        return totalReceitas() - totalDespesas() - totalGuardadoMetas();
    }

    private double safe(Double value) {
        return value == null ? 0 : value;
    }
}
