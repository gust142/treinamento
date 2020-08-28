package br.com.pulse.mapper;

import br.com.pulse.model.Estoque;
import br.com.pulse.model.Filial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface FilialMapper {
    Filial findFilialById(@Param("filial") Integer filial);

    Integer findEstoqueIdByFilial(@Param("filial") Integer filial);

    void addEstoque(@Param("filial") Integer filial);

}
