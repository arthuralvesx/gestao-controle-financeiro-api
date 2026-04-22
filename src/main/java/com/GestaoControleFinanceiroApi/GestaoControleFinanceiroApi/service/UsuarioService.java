package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.UsuarioRequestDto;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Usuario;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Usuario criar(UsuarioRequestDto dto) {
        var usuario = new Usuario(
            dto.email(),
            dto.senha()
        );
        return repository.save(usuario);
    }

    public boolean validarUsuario(String email, String senha) {
        return repository.findByEmailAndSenha(email, senha).isPresent();
    }

}
