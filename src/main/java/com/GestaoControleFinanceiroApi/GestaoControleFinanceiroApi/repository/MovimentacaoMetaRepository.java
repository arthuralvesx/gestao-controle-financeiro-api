package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.MovimentacaoMeta;

public interface MovimentacaoMetaRepository extends JpaRepository<MovimentacaoMeta, Long> {
    List<MovimentacaoMeta> findByMetaIdOrderByDataDescIdDesc(Long metaId);
    List<MovimentacaoMeta> findByDataLessThanEqual(LocalDate data);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from MovimentacaoMeta movimentacao where movimentacao.meta.id = :metaId")
    void deleteAllByMetaId(@Param("metaId") Long metaId);
}
