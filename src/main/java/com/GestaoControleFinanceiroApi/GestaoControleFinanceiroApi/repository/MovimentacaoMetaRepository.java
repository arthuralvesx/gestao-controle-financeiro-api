package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.MovimentacaoMeta;

public interface MovimentacaoMetaRepository extends JpaRepository<MovimentacaoMeta, Long> {
    List<MovimentacaoMeta> findByMetaIdOrderByDataDescIdDesc(Long metaId);
    List<MovimentacaoMeta> findByDataLessThanEqual(LocalDate data);
    void deleteByMetaId(Long metaId);
}
