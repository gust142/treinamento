package br.com.pulse.controller;

import br.com.pulse.model.Pedido;
import br.com.pulse.model.form.Response;
import br.com.pulse.service.PedidoEstoqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoEstoqueController {
    private final PedidoEstoqueService pedidoEstoqueService;
    @PostMapping("")
    public ResponseEntity efetuarPedido( @Valid  @RequestBody Pedido pedido) {
        Response response;
        try {

            response = pedidoEstoqueService.inserirPedido(pedido);

        }  catch (Exception e) {
            if (log.isErrorEnabled()) {

                log.error(e.getMessage());

            }

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(response,HttpStatus.OK);
    }

}
