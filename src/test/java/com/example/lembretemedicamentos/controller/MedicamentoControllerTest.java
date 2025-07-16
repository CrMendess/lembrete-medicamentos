package com.example.lembretemedicamentos.controller;

import com.example.lembretemedicamentos.model.Medicamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste de integração para o MedicamentoController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Garante que o teste não altere o banco de dados permanentemente
public class MedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deveCriarMedicamentoComSucesso() throws Exception {
        // Cria um objeto de medicamento para o teste com os novos campos
        Medicamento novoMedicamento = new Medicamento();
        novoMedicamento.setNome("Dipirona");
        novoMedicamento.setLaboratorio("Medley");
        novoMedicamento.setDosagem("500mg");
        novoMedicamento.setDataCompra(LocalDate.now());
        novoMedicamento.setNumeroCaixas(2);
        novoMedicamento.setValorCaixa(new BigDecimal("15.50"));
        novoMedicamento.setDuracaoTratamento("10 dias");

        // Executa a requisição POST e verifica a resposta
        mockMvc.perform(post("/api/medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoMedicamento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Dipirona"))
                .andExpect(jsonPath("$.numeroCaixas").value(2));
    }
}
