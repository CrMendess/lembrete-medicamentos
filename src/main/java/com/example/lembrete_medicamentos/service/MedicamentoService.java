package com.example.lembretemedicamentos.service;

import com.example.lembretemedicamentos.model.Medicamento;
import com.example.lembretemedicamentos.model.Usuario;
import com.example.lembretemedicamentos.repository.MedicamentoRepository;
import com.example.lembretemedicamentos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Medicamento> findAllByUser(Long usuarioId, String nome, String laboratorio, String tipoTratamento) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Medicamento> todos = medicamentoRepository.findByUsuario(usuario);
        Stream<Medicamento> stream = todos.stream();

        if (nome != null && !nome.isBlank()) {
            stream = stream.filter(m -> m.getNome().toLowerCase().contains(nome.toLowerCase()));
        }
        if (laboratorio != null && !laboratorio.isBlank()) {
            stream = stream.filter(m -> m.getLaboratorio() != null && m.getLaboratorio().toLowerCase().contains(laboratorio.toLowerCase()));
        }
        if (tipoTratamento != null && !tipoTratamento.isBlank()) {
            stream = stream.filter(m -> {
                String duracao = m.getDuracaoTratamento();
                if (duracao == null) return false;
                if (tipoTratamento.equals("Duração Definida")) {
                    return duracao.contains("dias");
                }
                return tipoTratamento.equals(duracao);
            });
        }
        return stream.collect(Collectors.toList());
    }

    public Medicamento save(Medicamento medicamento, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        medicamento.setUsuario(usuario);
        return medicamentoRepository.save(medicamento);
    }

    public Optional<Medicamento> update(Long id, Long usuarioId, Medicamento medicamentoDetails) {
        return medicamentoRepository.findById(id)
                .filter(medicamento -> medicamento.getUsuario().getId().equals(usuarioId)) // Valida a posse
                .map(medicamento -> {
                    medicamento.setNome(medicamentoDetails.getNome());
                    medicamento.setLaboratorio(medicamentoDetails.getLaboratorio());
                    medicamento.setDosagem(medicamentoDetails.getDosagem());
                    medicamento.setDataCompra(medicamentoDetails.getDataCompra());
                    medicamento.setNumeroCaixas(medicamentoDetails.getNumeroCaixas());
                    medicamento.setValorCaixa(medicamentoDetails.getValorCaixa());
                    medicamento.setDuracaoTratamento(medicamentoDetails.getDuracaoTratamento());
                    return medicamentoRepository.save(medicamento);
                });
    }

    public boolean deleteById(Long id, Long usuarioId) {
        return medicamentoRepository.findById(id)
                .filter(medicamento -> medicamento.getUsuario().getId().equals(usuarioId)) // Valida a posse
                .map(medicamento -> {
                    medicamentoRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
