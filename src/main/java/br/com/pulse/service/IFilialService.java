package br.com.pulse.service;

import br.com.pulse.model.Filial;

public interface IFilialService {

    public Filial findFilialById(Integer filial);
    public Integer findEstoqueIdByFilial(Integer filial);
    public void addEstoque(Integer filial);
}
