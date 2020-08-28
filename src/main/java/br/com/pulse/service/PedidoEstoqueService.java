package br.com.pulse.service;


import br.com.pulse.mapper.ItemEstoqueMapper;
import br.com.pulse.mapper.ItemPedidoMapper;
import br.com.pulse.model.*;
import br.com.pulse.model.form.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoEstoqueService implements IPedidoEstoqueService {

    private final FilialService filialService;
    private final ItemPedidoMapper itemPedidoMapper;
    private final ItemEstoqueMapper itemEstoqueMapper;

    @Override
    public Response inserirPedido(Pedido pedido) throws NotFoundException {

        Filial filial = filialService.findFilialById(pedido.getId_filial());
        if (filial != null){ //se possui filial
            Estoque estoque = new Estoque();
            estoque.setId_estoque(filialService.findEstoqueIdByFilial(pedido.getId_filial()));
            if(estoque.getId_estoque() == null){// se não possuir estoque criado
                filialService.addEstoque(pedido.getId_filial());
                estoque.setId_estoque(filialService.findEstoqueIdByFilial(pedido.getId_filial()));
            }
            return gerenciarEstoque(estoque,pedido);
        }else{throw new NotFoundException("A Filial em questão não existe");}
    }

    @Override
    public Response gerenciarEstoque(Estoque estoque,Pedido pedido) throws NotFoundException {

        estoque.setItem_estoque(itemEstoqueMapper.findItemByEstoque(estoque.getId_estoque())); // metodo que irá buscar os itens filtrados pelo id_estoque

        List<Produto> itemPedidoValido = new ArrayList<>();
        List<Produto> itemPedidoInvalido = new ArrayList<>();
        pedido.getProdutos().forEach(itemPedido->{
            List<ItemPedidoEstoque> itemPedidoEstoque = estoque.getItem_estoque().stream()
                    .filter(i-> i.getId_produto().equals(itemPedido.getId_produto()))
                    .collect(Collectors.toList()); // cria uma lista que filtra os produtos existentes que são iguais ao ids dos produtos passados por parâmetro

            if(itemPedidoEstoque.size() == 1){// se existe registro de produto, atualiza o registro
                if (pedido.getTipo_movimento().equals("entrada")){
                    itemPedidoValido.add(itemPedido);
                    adicionarQuantidade(itemPedidoEstoque.get(0),itemPedido);
                }else if(pedido.getTipo_movimento().equals("saida")){
                    int total = itemPedidoEstoque.get(0).getQuantidade_total() - itemPedido.getQuantidade();
                    if(total >= 0){// se a saída for maior ou igual a 0 , considera como item do pedido válido
                        itemPedidoValido.add(itemPedido);
                        removerQuantidade(itemPedidoEstoque.get(0),itemPedido);
                    }else{//senão, inválido
                        itemPedidoInvalido.add(itemPedido);
                    }
                }
            }else{// senão, adiciona o registro de produto se for entrada
                if (pedido.getTipo_movimento().equals("entrada")){
                    itemPedidoValido.add(itemPedido);
                    ItemPedidoEstoque item = new ItemPedidoEstoque();
                    item.setId_estoque(estoque.getId_estoque());
                    item.setId_produto(itemPedido.getId_produto());
                    item.setQuantidade_total(itemPedido.getQuantidade());
                    itemEstoqueMapper.addItemEstoque(item);

                }
            }
        });
        return salvarPedido(estoque.getId_estoque(),pedido.getTipo_movimento(),itemPedidoValido,itemPedidoInvalido);
    }

    @Override
    public Response salvarPedido(Integer estoque, String tipoMovimento, List<Produto> itemPedidoValido,List<Produto> itemPedidoInvalido) throws NotFoundException {
        if(!itemPedidoValido.isEmpty()){// se há algum item pedido válido para criar o pedido
            int idPedido = itemPedidoMapper.addPedido(estoque,tipoMovimento);
            itemPedidoMapper.addItemPedido(itemPedidoValido,idPedido);
            Response response = new Response();
            response.setMensagem("Pedido realizado com sucesso");
            response.setProdutos_validos(itemPedidoValido);
            if (itemPedidoInvalido.isEmpty()) {
                response.setMensagem("Pedido realizado com sucesso");
            } else {
                response.setMensagem("Pedido realizado contendo "+ itemPedidoInvalido.size()+" item(ns) inválido(s), verifique a disponibilidade do(s) item(ns) em questão");
            }
            response.setProdutos_invalidos(itemPedidoInvalido);
            return response;
        }else{
            throw new NotFoundException("Não foi possível concluir o pedido, verifique a disponibilidade dos itens no estoque e tente novamente");
        }
    }

    @Override
    public Integer adicionarQuantidade(ItemPedidoEstoque itemPedidoEstoque, Produto itemPedido) {
        itemPedidoEstoque.setQuantidade_total(itemPedidoEstoque.getQuantidade_total() + itemPedido.getQuantidade());
        itemEstoqueMapper.updateItemEstoque(itemPedidoEstoque);
        return itemPedidoEstoque.getQuantidade_total();
    }

    @Override
    public Integer removerQuantidade(ItemPedidoEstoque itemPedidoEstoque, Produto itemPedido) {
        itemPedidoEstoque.setQuantidade_total(itemPedidoEstoque.getQuantidade_total() - itemPedido.getQuantidade());
        itemEstoqueMapper.updateItemEstoque(itemPedidoEstoque);
        return itemPedidoEstoque.getQuantidade_total();
    }




}
