package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.CategoriaResumoDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.DashboardResumoDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MesResumoDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Despesa;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Receita;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.DespesaRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.ReceitaRepository;

@Service
public class DashboardService {
    private final DespesaRepository despesaRepository;
    private final ReceitaRepository receitaRepository;
    private final SaldoService saldoService;

    public DashboardService(
        DespesaRepository despesaRepository,
        ReceitaRepository receitaRepository,
        SaldoService saldoService
    ) {
        this.despesaRepository = despesaRepository;
        this.receitaRepository = receitaRepository;
        this.saldoService = saldoService;
    }

    public DashboardResumoDto resumo() {
        var despesas = despesaRepository.findAll();
        var receitas = receitaRepository.findAll();
        var meses = new TreeMap<YearMonth, MesData>();

        for (Despesa despesa : despesas) {
            if (despesa.getData() == null) {
                continue;
            }
            var mes = YearMonth.from(despesa.getData());
            var data = meses.computeIfAbsent(mes, key -> new MesData());
            data.despesas += safe(despesa.getValor());
            data.categorias.merge(normalizeCategoria(despesa.getCategoria()), safe(despesa.getValor()), Double::sum);
        }

        for (Receita receita : receitas) {
            if (receita.getData() == null) {
                continue;
            }
            var mes = YearMonth.from(receita.getData());
            meses.computeIfAbsent(mes, key -> new MesData()).receitas += safe(receita.getValor());
        }

        var resumos = new ArrayList<MesResumoDto>();
        for (Map.Entry<YearMonth, MesData> entry : meses.entrySet()) {
            resumos.add(toResumo(entry.getKey(), entry.getValue()));
        }
        resumos.sort(Comparator.comparing(MesResumoDto::mes).reversed());

        double totalReceitas = saldoService.totalReceitas();
        double totalDespesas = saldoService.totalDespesas();
        double totalGuardadoMetas = saldoService.totalGuardadoMetas();
        double saldoTotal = saldoService.saldoDisponivel();

        resumos.replaceAll(mes -> new MesResumoDto(
            mes.mes(),
            mes.receitas(),
            mes.despesas(),
            saldoService.totalGuardadoMetasAte(YearMonth.parse(mes.mes()).atEndOfMonth()),
            mes.receitas() - mes.despesas() - saldoService.totalGuardadoMetasAte(YearMonth.parse(mes.mes()).atEndOfMonth()),
            mes.categorias()
        ));

        var mesMaisGasto = resumos.stream()
            .max(Comparator.comparing(MesResumoDto::despesas))
            .orElse(null);

        return new DashboardResumoDto(totalReceitas, totalDespesas, totalGuardadoMetas, saldoTotal, mesMaisGasto, resumos);
    }

    private MesResumoDto toResumo(YearMonth mes, MesData data) {
        var categorias = data.categorias.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .map(entry -> new CategoriaResumoDto(
                entry.getKey(),
                entry.getValue(),
                data.despesas > 0 ? (entry.getValue() / data.despesas) * 100 : 0
            ))
            .collect(Collectors.toList());

        return new MesResumoDto(mes.toString(), data.receitas, data.despesas, 0.0, data.receitas - data.despesas, categorias);
    }

    private double safe(Double value) {
        return value == null ? 0 : value;
    }

    private String normalizeCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return "Outros";
        }
        return categoria.trim();
    }

    private static class MesData {
        double receitas;
        double despesas;
        Map<String, Double> categorias = new LinkedHashMap<>();
    }
}
