package br.com.pulse.model;

import lombok.Data;

import java.util.List;

@Data
public class Estoque {
    private Integer id_estoque;
    private Integer id_filial;
    private List<ItemPedidoEstoque> item_estoque;
}
