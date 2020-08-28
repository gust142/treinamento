package br.com.pulse.mapper;

import br.com.pulse.model.Produto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ProdutoMapper {

    List<Produto> findProdutosByFilial(@Param("filial") Integer filial);

    void addProduto(@Param("produto")Produto produto);
}
