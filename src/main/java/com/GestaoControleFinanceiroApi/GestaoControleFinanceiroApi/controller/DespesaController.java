package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.DespesaRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Despesa;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service.DespesaService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/despesa")
@CrossOrigin("*")
public class DespesaController {
    private final DespesaService service;

    public DespesaController(DespesaService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Despesa>> listAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Despesa> incluir(@RequestBody DespesaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.incluir(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.BuscarPorId(id)) ;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Despesa> atualizar(@PathVariable Long id, @RequestBody DespesaRequestDto dto ) {     
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Despesa> excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
