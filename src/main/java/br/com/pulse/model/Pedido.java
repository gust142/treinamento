package br.com.pulse.model;

import java.util.List;

import lombok.Data;



@Data
public class Pedido {
    private Integer id_filial;
    private String tipo_movimento;
    private List<Produto> produtos;
}
