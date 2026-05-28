package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MetasRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.exception.DespesaNotFoundException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Metas;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.MetasRepository;

@Service
public class MetasService {

    private final MetasRepository repository;

    public MetasService(MetasRepository repository) {
        this.repository = repository;
    }
    
        public List<Metas> findAll() {
        return repository.findAll();
    }

    public Metas incluir(MetasRequestDto dto) {
        var metas = new Metas (
            dto.valorMeta(),
            dto.valor(),
            dto.metaCategoria()
        );
        return repository.save(metas);
    }
    
    public Metas BuscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new DespesaNotFoundException(id));
    }

    public Metas atualizar(Long id, MetasRequestDto dto) {
        var metas = BuscarPorId(id);

        metas.setValorMeta(dto.valorMeta());
        metas.setValor(dto.valor());
        metas.setMetaCategoria(dto.metaCategoria());

        return repository.save(metas);
    }

    public void excluir(Long id) {
        var metas = BuscarPorId(id);
        repository.delete(metas);
    }
}

