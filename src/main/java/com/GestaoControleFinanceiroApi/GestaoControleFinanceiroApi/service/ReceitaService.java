package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.ReceitaRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.exception.DespesaNotFoundException;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Receita;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.ReceitaRepository;

@Service
public class ReceitaService {

    private final ReceitaRepository repository;

    public ReceitaService(ReceitaRepository repository) {
        this.repository = repository;
    }
    
        public List<Receita> findAll() {
        return repository.findAll();
    }

    public Receita incluir(ReceitaRequestDto dto) {
        var receita = new Receita (
            dto.data(),
            dto.valor()
        );
        return repository.save(receita);
    }
    
    public Receita BuscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new DespesaNotFoundException(id));
    }

    public Receita atualizar(Long id, ReceitaRequestDto dto) {
        var receita = BuscarPorId(id);

        receita.setData(dto.data());
        receita.setValor(dto.valor());

        return repository.save(receita);
    }

    public void excluir(Long id) {
        var receita = BuscarPorId(id);
        repository.delete(receita);
    }
}
