package com.example.lembretemedicamentos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String laboratorio;
    private String dosagem;
    private LocalDate dataCompra;
    private Integer numeroCaixas;
    private BigDecimal valorCaixa;
    private String duracaoTratamento;

    // NOVO: Relacionamento com o usuário
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore // Evita que os dados do usuário sejam enviados com o medicamento
    private Usuario usuario;
}
