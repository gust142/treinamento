package br.com.pulse.service;


import br.com.pulse.mapper.FilialMapper;
import br.com.pulse.model.Filial;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FilialServiceTest {

    private IFilialService filialService;
    private FilialMapper filialMapper;

    @Before
    public void setUp()  {
        filialMapper = mock(FilialMapper.class);
        filialService = new FilialService(filialMapper);

    }

    @Test
    public void deveRetornarFilial(){
        Filial filial;
        filial = filialService.findFilialById(1);
        when(filialService.findFilialById(1)).thenReturn(filial);
        verify(filialMapper, times(1)).findFilialById(1);

    }

    @Test
    public void deveRetornarIdEstoque(){

        when(filialService.findEstoqueIdByFilial(1)).thenReturn(10);
        Integer estoqueId = filialService.findEstoqueIdByFilial(1);
        assertEquals(10, (int) estoqueId);
        verify(filialMapper, times(1)).findEstoqueIdByFilial(1);
    }

    @Test
    public void addEstoque(){
        try {
            filialService.addEstoque(1);
            verify(filialMapper, times(1)).addEstoque(1);
            assert(true);
        }catch (Exception e){
            Assertions.fail("");
        }
    }
}
