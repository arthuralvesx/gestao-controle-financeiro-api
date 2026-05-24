package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.dto.AuthResponseDto;
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
        validarDados(dto);
        var email = dto.email().trim().toLowerCase();
        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ja cadastrado.");
        }

        var usuario = new Usuario(
            email,
            dto.senha()
        );
        return repository.save(usuario);
    }

    public AuthResponseDto validarUsuario(String email, String senha) {
        if (email == null || senha == null) {
            return new AuthResponseDto(false, null, null, "Informe email e senha.");
        }

        return repository.findByEmailAndSenha(email.trim().toLowerCase(), senha)
            .map(usuario -> new AuthResponseDto(true, usuario.getId(), usuario.getEmail(), "Login realizado com sucesso."))
            .orElseGet(() -> new AuthResponseDto(false, null, null, "Email ou senha invalidos."));
    }

    private void validarDados(UsuarioRequestDto dto) {
        if (dto.email() == null || dto.email().isBlank() || dto.senha() == null || dto.senha().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um email valido e uma senha com pelo menos 6 caracteres.");
        }
    }

}
