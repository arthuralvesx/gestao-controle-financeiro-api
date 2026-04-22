package com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.GestaoControleFinanceiroApi.GestaoControleFinanceiroApi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
