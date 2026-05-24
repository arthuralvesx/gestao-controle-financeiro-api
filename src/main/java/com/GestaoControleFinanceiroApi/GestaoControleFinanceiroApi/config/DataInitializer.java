package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Usuario;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;

    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByEmail("admin@ffb.com")) {
            usuarioRepository.save(new Usuario("admin@ffb.com", "123456"));
        }
    }
}
