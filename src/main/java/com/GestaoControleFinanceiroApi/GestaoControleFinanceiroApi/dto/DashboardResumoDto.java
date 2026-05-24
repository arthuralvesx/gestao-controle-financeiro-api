package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

import java.util.List;

public record DashboardResumoDto(
    Double totalReceitas,
    Double totalDespesas,
    Double saldoTotal,
    MesResumoDto mesMaisGasto,
    List<MesResumoDto> meses
) {}
