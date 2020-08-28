package br.com.pulse.service;

import br.com.pulse.model.*;
import br.com.pulse.model.form.Response;
import org.apache.ibatis.javassist.NotFoundException;


import java.util.List;

public interface IPedidoEstoqueService {

    Response inserirPedido(Pedido pedido) throws Exception;
    Response gerenciarEstoque(Estoque estoque, Pedido pedido) throws Exception ;
    Response salvarPedido(Integer estoque, String tipoMovimento, List<Produto> itemPedidoValido, List<Produto> itemPedidoInvalido) throws NotFoundException;
    Integer adicionarQuantidade(ItemPedidoEstoque itemPedidoEstoque, Produto itemPedido);
    Integer removerQuantidade(ItemPedidoEstoque itemPedidoEstoque, Produto itemPedido);

}
