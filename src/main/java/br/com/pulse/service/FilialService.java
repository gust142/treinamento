package br.com.pulse.service;


import br.com.pulse.mapper.FilialMapper;
import br.com.pulse.model.Filial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilialService implements IFilialService {
    private final FilialMapper mapper;

    @Override
    public Filial findFilialById(Integer filial) {
        return mapper.findFilialById(filial);
    }

    @Override
    public Integer findEstoqueIdByFilial(Integer filial) {
        return mapper.findEstoqueIdByFilial(filial);
    }

    @Override
    public void addEstoque(Integer filial) {
        mapper.addEstoque(filial);
    }
}
