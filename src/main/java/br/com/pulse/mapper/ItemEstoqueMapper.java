package br.com.pulse.mapper;

import br.com.pulse.model.ItemPedidoEstoque;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemEstoqueMapper {


    void addItemEstoque(@Param("item") ItemPedidoEstoque itemEstoque);

    void updateItemEstoque(@Param("item") ItemPedidoEstoque itemEstoque);

    List<ItemPedidoEstoque> findItemByEstoque(@Param("id_estoque") Integer estoque);
}
