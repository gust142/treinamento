package br.com.pulse.service;

import br.com.pulse.model.Produto;

import java.util.List;

public interface IProdutoService {

    List<Produto> findProdutosByFilial(Integer filial);

    void addProduto(Produto produto) throws Exception;

}
