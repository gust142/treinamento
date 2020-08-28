package br.com.pulse.controller;





import br.com.pulse.model.Produto;
import br.com.pulse.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;
    @GetMapping("/{id}")
    public ResponseEntity listarProdutos(@PathVariable("id") Integer id) {
        try {
            List<Produto> produtos = produtoService.findProdutosByFilial(id);
            return new ResponseEntity(produtos,HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());

            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity addProduto( @Valid @RequestBody Produto produto) {

        try {

            produtoService.addProduto(produto);

        }  catch (Exception e) {
            if (log.isErrorEnabled()) {

                log.error(e.getMessage());

            }

            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Produto Adicionado com sucesso",HttpStatus.OK);
    }

}
