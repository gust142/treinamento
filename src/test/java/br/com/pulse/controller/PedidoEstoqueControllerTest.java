package br.com.pulse.controller;

import br.com.pulse.mapper.FilialMapper;
import br.com.pulse.mapper.ItemEstoqueMapper;
import br.com.pulse.mapper.ItemPedidoMapper;
import br.com.pulse.model.Filial;
import br.com.pulse.model.Pedido;
import br.com.pulse.model.Produto;
import br.com.pulse.service.FilialService;
import br.com.pulse.service.IPedidoEstoqueService;
import br.com.pulse.service.PedidoEstoqueService;
import br.com.pulse.service.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PedidoEstoqueControllerTest {

    private MockMvc mockMvc;
    @Mock
    private IPedidoEstoqueService pedidoEstoqueService;
    private ObjectMapper objectMapper;
    private ItemPedidoMapper itemPedidoMapper;
    private ItemEstoqueMapper itemEstoqueMapper;
    private FilialService filialService;

    @Before
    public void setUp(){
        itemPedidoMapper = mock(ItemPedidoMapper.class);
        itemEstoqueMapper = mock(ItemEstoqueMapper.class);
        filialService = mock(FilialService.class);
        pedidoEstoqueService = new PedidoEstoqueService(
                filialService,
                itemPedidoMapper,
                itemEstoqueMapper
        );
        mockMvc = MockMvcBuilders.standaloneSetup(new PedidoEstoqueController((PedidoEstoqueService) pedidoEstoqueService))
                .build();
        objectMapper = new ObjectMapper();
    }


    @Test
    public void deveEfetuarPedido() throws Exception {
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

        String url = "/pedido";
        mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().is2xxSuccessful());

    }
    @Test
    public void naoDeveEfetuarPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId_filial(1);
        pedido.setTipo_movimento("entrada");
        Produto produto = new Produto();
        produto.setId_produto(1);
        produto.setQuantidade(10);
        List<Produto> lista = new ArrayList<>();
        lista.add(produto);
        pedido.setProdutos(lista);


        String url = "/pedido";
        mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().is4xxClientError());

    }



}
