package br.com.pulse.service;



import br.com.pulse.mapper.ProdutoMapper;
import br.com.pulse.model.Produto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService implements IProdutoService {
    private final ProdutoMapper mapper;

    @Override
    public List<Produto> findProdutosByFilial(Integer filial) {
        return mapper.findProdutosByFilial(filial);
    }

    @Override
    public void addProduto(Produto produto) throws Exception {
           if(!produto.getDescricao().isEmpty()){
               mapper.addProduto(produto);
           }else{
               throw new Exception("Erro: insira um nome para o produto");
           }
    }
}
