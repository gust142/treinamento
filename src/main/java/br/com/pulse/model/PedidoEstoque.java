package br.com.pulse.model;

import lombok.Data;

import java.util.List;


@Data
public class PedidoEstoque {
    private Integer id_estoque;
    private List<ItemPedidoEstoque> item_pedido;
    private String tipo_movimento;

}
