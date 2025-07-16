package com.example.lembretemedicamentos.repository;

import com.example.lembretemedicamentos.model.Medicamento;
import com.example.lembretemedicamentos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    // Novo método para buscar medicamentos por usuário
    List<Medicamento> findByUsuario(Usuario usuario);
}
