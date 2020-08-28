package br.com.pulse.model.form;

import br.com.pulse.model.Produto;
import lombok.Data;

import java.util.List;

@Data
public class Response {
    private String mensagem;
    private List<Produto> produtos_validos, produtos_invalidos;
}
