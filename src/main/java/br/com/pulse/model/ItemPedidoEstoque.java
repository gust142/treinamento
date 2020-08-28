package br.com.pulse.model;

import lombok.Data;

@Data
public class ItemPedidoEstoque {
    private Integer id_estoque;
    private Integer id_produto;
    private Integer quantidade_total;
}
