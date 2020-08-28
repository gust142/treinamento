package br.com.pulse.service;

import br.com.pulse.mapper.ItemEstoqueMapper;
import br.com.pulse.mapper.ItemPedidoMapper;
import br.com.pulse.model.*;
import br.com.pulse.model.form.Response;
import org.apache.ibatis.javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;


public class PedidoEstoqueServiceTest {

    private IPedidoEstoqueService pedidoEstoqueService;
    private FilialService filialService;
    private ItemPedidoMapper itemPedidoMapper;
    private ItemEstoqueMapper itemEstoqueMapper;

    @Before
    public void setUp()  {
        filialService = mock(FilialService.class);
        itemPedidoMapper = mock(ItemPedidoMapper.class);
        itemEstoqueMapper = mock(ItemEstoqueMapper.class);

        pedidoEstoqueService = new PedidoEstoqueService(filialService,itemPedidoMapper,itemEstoqueMapper);

    }


    @Test
    public void deveRetornarFilialNaoExiste() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId_filial(1);
        pedido.setTipo_movimento("entrada");
        pedido.setProdutos(new ArrayList<>());


        try {
            pedidoEstoqueService.inserirPedido(pedido);
            Assertions.fail("");
        }catch (NotFoundException e){
            assert(true);
        }

    }

    @Test
    public void deveRetornarPedidoNaoConcluido(){
        Integer estoque = 1;
        String tipoMovimento="entrada";
        List<Produto> listaValidos = new ArrayList<>();
        List<Produto> listaInvalidos = new ArrayList<>();


        try {
            pedidoEstoqueService.salvarPedido(estoque,tipoMovimento,listaValidos,listaInvalidos);
            Assertions.fail("");
        }catch (NotFoundException e ){

            assert(true);
        }

    }
    @Test
    public void deveRetornarPedidoConcluido(){
        Integer estoque = 1;
        String tipoMovimento="entrada";
        List<Produto> listaValidos = new ArrayList<>();
        List<Produto> listaInvalidos = new ArrayList<>();
        listaValidos.add(new Produto());

        try {
           Response response  = pedidoEstoqueService.salvarPedido(estoque,tipoMovimento,listaValidos,listaInvalidos);
            assert(response.getMensagem().equals("Pedido realizado com sucesso"));

        }catch (NotFoundException e ){
            Assertions.fail("");
        }

    }
    @Test
    public void deveRetornarPedidoConcluidoComItensInvalidos(){
        Integer estoque = 1;
        String tipoMovimento="entrada";
        List<Produto> listaValidos = new ArrayList<>();
        List<Produto> listaInvalidos = new ArrayList<>();
        listaValidos.add(new Produto());
        listaInvalidos.add(new Produto());

        try {
            Response response  = pedidoEstoqueService.salvarPedido(estoque,tipoMovimento,listaValidos,listaInvalidos);
            assert(response.getMensagem().equals("Pedido realizado contendo 1 item(ns) inválido(s), verifique a disponibilidade do(s) item(ns) em questão"));

        }catch (NotFoundException e ){
            Assertions.fail("");
        }

    }

    @Test
    public void deveAdicionarProdutoEstoque(){
        ItemPedidoEstoque itemPedidoEstoque = new ItemPedidoEstoque();
        itemPedidoEstoque.setId_estoque(1);
        itemPedidoEstoque.setId_produto(1);
        itemPedidoEstoque.setQuantidade_total(10);
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(10);
        int valorEstoqueAnterior = itemPedidoEstoque.getQuantidade_total();
        int soma = pedidoEstoqueService.adicionarQuantidade(itemPedidoEstoque,produto);

        if(soma == (valorEstoqueAnterior + produto.getQuantidade())){
            assert(true);
        }else{
            Assertions.fail("");
        }
    }

    @Test
    public void deveRemoverProdutoEstoque(){
        ItemPedidoEstoque itemPedidoEstoque = new ItemPedidoEstoque();
        itemPedidoEstoque.setId_estoque(1);
        itemPedidoEstoque.setId_produto(1);
        itemPedidoEstoque.setQuantidade_total(10);
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(10);
        int valorEstoqueAnterior = itemPedidoEstoque.getQuantidade_total();
        int diferenca = pedidoEstoqueService.removerQuantidade(itemPedidoEstoque,produto);

        if(diferenca == (valorEstoqueAnterior - produto.getQuantidade())){
            assert(true);
        }else{
            Assertions.fail("");
        }
    }

    @Test
    public void deveSalvarPedido(){
       Estoque estoque = new Estoque();
       Pedido pedido = new Pedido();
       estoque.setId_estoque(2);
       estoque.setId_filial(1);
       estoque.setItem_estoque(new ArrayList<>());


       pedido.setId_filial(1);
       pedido.setTipo_movimento("entrada");
        List<Produto> lista = new ArrayList<>();
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(10);
        lista.add(produto);
        pedido.setProdutos(lista);

        try {
            pedidoEstoqueService.gerenciarEstoque(estoque,pedido);
            assert(true);
        } catch (Exception e) {
            Assertions.fail("");
        }
    }

    @Test
    public void deveEfetuarPedidoEntrada() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId_filial(1);
        pedido.setTipo_movimento("entrada");
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(10);
        List<Produto> lista = new ArrayList<>();
        lista.add(produto);
        pedido.setProdutos(lista);
        Filial filial = new Filial();
        filial.setId_filial(1);
        filial.setNome_filial("filial 1");
        ItemPedidoEstoque itemPedidoEstoque = new ItemPedidoEstoque();
        itemPedidoEstoque.setId_produto(1);
        itemPedidoEstoque.setId_estoque(1);
        itemPedidoEstoque.setQuantidade_total(20);
        List<ItemPedidoEstoque> listaItem = new ArrayList<>();
        listaItem.add(itemPedidoEstoque);

        when(filialService.findFilialById(pedido.getId_filial())).thenReturn(filial);
        when(itemEstoqueMapper.findItemByEstoque(any())).thenReturn(listaItem);
        when(filialService.findEstoqueIdByFilial(pedido.getId_filial())).thenReturn(1);

        pedidoEstoqueService.inserirPedido(pedido);
        verify(itemPedidoMapper,times(1)).addPedido(1,pedido.getTipo_movimento());

    }
    @Test
    public void deveEfetuarPedidoSaida() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId_filial(1);
        pedido.setTipo_movimento("saida");
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(10);
        List<Produto> lista = new ArrayList<>();
        lista.add(produto);
        pedido.setProdutos(lista);
        Filial filial = new Filial();
        filial.setId_filial(1);
        filial.setNome_filial("filial 1");
        ItemPedidoEstoque itemPedidoEstoque = new ItemPedidoEstoque();
        itemPedidoEstoque.setId_produto(1);
        itemPedidoEstoque.setId_estoque(1);
        itemPedidoEstoque.setQuantidade_total(20);
        List<ItemPedidoEstoque> listaItem = new ArrayList<>();
        listaItem.add(itemPedidoEstoque);

        when(filialService.findFilialById(pedido.getId_filial())).thenReturn(filial);
        when(itemEstoqueMapper.findItemByEstoque(any())).thenReturn(listaItem);
        when(filialService.findEstoqueIdByFilial(pedido.getId_filial())).thenReturn(1);

        pedidoEstoqueService.inserirPedido(pedido);
        verify(itemPedidoMapper,times(1)).addPedido(1,pedido.getTipo_movimento());

    }

    @Test
    public void deveRetornarPedidoConcluidoCriandoEstoque() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId_filial(1);
        pedido.setTipo_movimento("entrada");
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(10);
        List<Produto> lista = new ArrayList<>();
        lista.add(produto);
        pedido.setProdutos(lista);
        Filial filial = new Filial();
        filial.setId_filial(1);
        filial.setNome_filial("filial 1");

        when(filialService.findFilialById(pedido.getId_filial())).thenReturn(filial);
        when(filialService.findEstoqueIdByFilial(pedido.getId_filial())).thenReturn(null);
        pedidoEstoqueService.inserirPedido(pedido);

        verify(filialService,times(1)).addEstoque(pedido.getId_filial());
    }

    @Test
    public void deveRetornarErroNoPedidoPorQuantidadeMaiorQueOEstoque(){
        Pedido pedido = new Pedido();
        pedido.setId_filial(1);
        pedido.setTipo_movimento("saida");
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(50);
        List<Produto> lista = new ArrayList<>();
        lista.add(produto);
        pedido.setProdutos(lista);
        Filial filial = new Filial();
        filial.setId_filial(1);
        filial.setNome_filial("filial 1");
        ItemPedidoEstoque itemPedidoEstoque = new ItemPedidoEstoque();
        itemPedidoEstoque.setId_produto(1);
        itemPedidoEstoque.setId_estoque(1);
        itemPedidoEstoque.setQuantidade_total(20);
        List<ItemPedidoEstoque> listaItem = new ArrayList<>();
        listaItem.add(itemPedidoEstoque);

        when(filialService.findFilialById(pedido.getId_filial())).thenReturn(filial);
        when(itemEstoqueMapper.findItemByEstoque(any())).thenReturn(listaItem);
        when(filialService.findEstoqueIdByFilial(pedido.getId_filial())).thenReturn(1);

        try{
            pedidoEstoqueService.inserirPedido(pedido);
            Assertions.fail("error");
        }catch (Exception e){

            assert(true);
        }
    }
}