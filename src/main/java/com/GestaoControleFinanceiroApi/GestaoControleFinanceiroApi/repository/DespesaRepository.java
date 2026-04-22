package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}