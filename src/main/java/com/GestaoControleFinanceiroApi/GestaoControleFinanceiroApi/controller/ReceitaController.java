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
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.ReceitaRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Receita;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service.ReceitaService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/receita")
@CrossOrigin("*")
public class ReceitaController {
    
    private ReceitaService service;

    public ReceitaController(ReceitaService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Receita>> listAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Receita> incluir(@RequestBody ReceitaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.incluir(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.BuscarPorId(id)) ;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizar(@PathVariable Long id, @RequestBody ReceitaRequestDto dto ) {     
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Receita> excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}