package br.com.pulse.controller;

import br.com.pulse.mapper.ProdutoMapper;
import br.com.pulse.model.Produto;
import br.com.pulse.service.IProdutoService;
import br.com.pulse.service.ProdutoService;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.core.MediaType;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProdutoControllerTest {

    private MockMvc mockMvc;
    @Mock
    private IProdutoService produtoService;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        produtoService = new ProdutoService(mock(ProdutoMapper.class));
        mockMvc = MockMvcBuilders.standaloneSetup(new ProdutoController((ProdutoService) produtoService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deveListarProdutosPorFilial() throws Exception {
        String url = "/produtos/";
        mockMvc.perform(get(url+1).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void deveAdicionarProduto() throws Exception {
        Produto produto = new Produto();
        produto.setDescricao("Produto1");
        produto.setValor(10.0);
        String url = "/produtos";
        mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void naoDeveAdicionarProduto() throws Exception {
        Produto produto = new Produto();
        produto.setDescricao("");
        produto.setValor(10.0);
        String url = "/produtos";
        mockMvc.perform(post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().is4xxClientError());

    }



}
