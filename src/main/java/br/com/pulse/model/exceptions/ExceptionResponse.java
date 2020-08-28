package br.com.pulse.model.exceptions;


import lombok.Data;

@Data
public class ExceptionResponse {
    private String mensagem;

    public ExceptionResponse(String message) {
        super();
        this.mensagem = message;
    }
    public String getMensagem() {
        return mensagem;
    }
}