package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}

