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
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MetasRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MovimentacaoMetaRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.MovimentacaoMetaResponseDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Metas;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service.MetasService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/metas")
@CrossOrigin("*")
public class MetasController {
    private final MetasService service;

    public MetasController(MetasService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Metas>> listAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Metas> incluir(@RequestBody MetasRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.incluir(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metas> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.BuscarPorId(id)) ;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Metas> atualizar(@PathVariable Long id, @RequestBody MetasRequestDto dto ) {     
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @PostMapping("/{id}/entradas")
    public ResponseEntity<Metas> guardar(@PathVariable Long id, @RequestBody MovimentacaoMetaRequestDto dto) {
        return ResponseEntity.ok(service.registrarEntrada(id, dto));
    }

    @PostMapping("/{id}/saidas")
    public ResponseEntity<Metas> retirar(@PathVariable Long id, @RequestBody MovimentacaoMetaRequestDto dto) {
        return ResponseEntity.ok(service.registrarSaida(id, dto));
    }

    @GetMapping("/{id}/movimentacoes")
    public ResponseEntity<List<MovimentacaoMetaResponseDto>> movimentacoes(@PathVariable Long id) {
        return ResponseEntity.ok(service.movimentacoes(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Metas> excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
