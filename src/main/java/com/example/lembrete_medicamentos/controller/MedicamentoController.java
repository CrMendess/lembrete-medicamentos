package com.example.lembretemedicamentos.controller;

import com.example.lembretemedicamentos.model.Medicamento;
import com.example.lembretemedicamentos.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin(origins = "*")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    // A requisição agora precisa de um usuarioId para buscar os medicamentos
    @GetMapping
    public List<Medicamento> getAllMedicamentos(
            @RequestParam Long usuarioId,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String laboratorio,
            @RequestParam(required = false) String tipoTratamento) {
        return medicamentoService.findAllByUser(usuarioId, nome, laboratorio, tipoTratamento);
    }

    // A requisição para criar também precisa do usuarioId
    @PostMapping
    public ResponseEntity<Medicamento> createMedicamento(@RequestParam Long usuarioId, @RequestBody Medicamento medicamento) {
        Medicamento savedMedicamento = medicamentoService.save(medicamento, usuarioId);
        return new ResponseEntity<>(savedMedicamento, HttpStatus.CREATED);
    }

    // A requisição para atualizar precisa do usuarioId para validar a posse
    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> updateMedicamento(@PathVariable Long id, @RequestParam Long usuarioId, @RequestBody Medicamento medicamentoDetails) {
        return medicamentoService.update(id, usuarioId, medicamentoDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // A requisição para deletar precisa do usuarioId para validar a posse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Long id, @RequestParam Long usuarioId) {
        if (medicamentoService.deleteById(id, usuarioId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
