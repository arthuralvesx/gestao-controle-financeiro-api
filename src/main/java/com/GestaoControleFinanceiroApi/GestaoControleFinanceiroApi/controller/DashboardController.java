package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.DashboardResumoDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service.DashboardService;

@RestController
@RequestMapping("api/dashboard")
@CrossOrigin("*")
public class DashboardController {
    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<DashboardResumoDto> resumo() {
        return ResponseEntity.ok(service.resumo());
    }
}
