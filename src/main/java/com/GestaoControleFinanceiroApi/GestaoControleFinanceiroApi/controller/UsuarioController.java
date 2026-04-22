package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.UsuarioRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Usuario;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service.UsuarioService;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin("*")
public class UsuarioController {
    private final UsuarioService service;

     public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    //fazer validacao de dado
    public ResponseEntity<Usuario> create(@RequestBody UsuarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UsuarioRequestDto dto) {
        boolean existe = service.validarUsuario(dto.email(), dto.senha());
        return ResponseEntity.ok(existe);
    }
}
