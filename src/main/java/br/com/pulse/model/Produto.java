package br.com.pulse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;




@Data
public class Produto {
    private Integer id_produto;
    private String descricao;
    private Double valor;
    private Integer quantidade;
}
