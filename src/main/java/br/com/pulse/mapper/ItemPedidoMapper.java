package br.com.pulse.mapper;
import br.com.pulse.model.ItemPedidoEstoque;
import br.com.pulse.model.Produto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ItemPedidoMapper {



    void addItemPedido(@Param("lista") List<Produto> pedido,@Param("pedido") Integer idPedido);

    Integer addPedido(@Param("estoque") Integer idEstoque, @Param("tipoMovimento")String tipoMovimento);



}
