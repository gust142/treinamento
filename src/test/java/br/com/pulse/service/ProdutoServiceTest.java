package br.com.pulse.service;

import br.com.pulse.mapper.ProdutoMapper;
import br.com.pulse.model.Produto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {
    private IProdutoService produtoService;
    private ProdutoMapper produtoMapper;


    @Before
    public void setUp() {

        produtoMapper = mock(ProdutoMapper.class);
        produtoService = new ProdutoService(produtoMapper);
    }

    @Test
    public void deveRetornarListaDeProdutos(){
        List<Produto> lista = new ArrayList<>();
        Produto produto = new Produto();
        produto.setDescricao("produto1");
        produto.setId_produto(1);
        produto.setValor(10.0);

        Produto produto2 = new Produto();
        produto.setDescricao("produto1");
        produto.setId_produto(2);
        produto.setValor(10.0);
        lista.add(produto);
        lista.add(produto2);

        when(produtoService.findProdutosByFilial(1)).thenReturn(lista);
        List<Produto> listaProduto = produtoService.findProdutosByFilial(1);
        verify(produtoMapper,times(1)).findProdutosByFilial(1);
        assertFalse(listaProduto.isEmpty());
    }

    @Test
    public void deveAdicionarProduto(){
        Produto produto = new Produto();
        produto.setDescricao("produto1");
        produto.setValor(10.0);

        try {
            produtoService.addProduto(produto);
            assert(true);
        }catch (Exception e){
            Assertions.fail("");
        }

    }

}
