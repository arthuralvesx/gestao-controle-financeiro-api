package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto;

import java.util.List;

public record DashboardResumoDto(
    Double totalReceitas,
    Double totalDespesas,
    Double totalGuardadoMetas,
    Double saldoTotal,
    MesResumoDto mesMaisGasto,
    List<MesResumoDto> meses
) {}
