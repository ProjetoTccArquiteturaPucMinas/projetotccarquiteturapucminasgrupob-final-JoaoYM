package com.seuprojeto.marketplace.application.usecase;

import com.seuprojeto.marketplace.application.dto.SelecaoCarrinho;
import com.seuprojeto.marketplace.domain.model.Produto;
import com.seuprojeto.marketplace.domain.model.ResumoCarrinho;
import com.seuprojeto.marketplace.domain.repository.ProdutoRepositorio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class CalcularCarrinhoUseCase {

    private final ProdutoRepositorio produtoRepositorio;

    public CalcularCarrinhoUseCase(ProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }

    public ResumoCarrinho executar(List<SelecaoCarrinho> selecoes) {
        BigDecimal subtotal = BigDecimal.ZERO;
        int quantidadeTotalItens = 0;
        int descontoPorCategoriaGeral = 0;

        // Iterar sobre a lista de seleções
        for (SelecaoCarrinho selecao : selecoes) {
            // Assumindo que SelecaoCarrinho tem os métodos getIdProduto() e getQuantidade()
            Optional<Produto> produtoOpt = produtoRepositorio.findById(selecao.getIdProduto());
            
            if (produtoOpt.isPresent()) {
                Produto produto = produtoOpt.get();
                int quantidade = selecao.getQuantidade();
                
                if (quantidade > 0) {
                    // 1. Acumular Subtotal e Quantidade
                    quantidadeTotalItens += quantidade;
                    BigDecimal valorItens = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
                    subtotal = subtotal.add(valorItens);

                    // 2. Acumular Desconto de Categoria (Por Item)
                    int descontoDaCategoria = produto.getCategoriaProduto().getPercentualDesconto();
                    descontoPorCategoriaGeral += (descontoDaCategoria * quantidade);
                }
            }
        }

        // 3. Regra: Desconto por quantidade total de itens
        int descontoPorQuantidade = 0;
        if (quantidadeTotalItens >= 4) {
            descontoPorQuantidade = 10;
        } else if (quantidadeTotalItens == 3) {
            descontoPorQuantidade = 7;
        } else if (quantidadeTotalItens == 2) {
            descontoPorQuantidade = 5;
        }

        // 4. Regra: Desconto Máximo Cumulativo (Limite de 25%)
        int percentualDescontoFinal = descontoPorQuantidade + descontoPorCategoriaGeral;
        if (percentualDescontoFinal > 25) {
            percentualDescontoFinal = 25;
        }

        // 5. Cálculos Finais de Valores
        BigDecimal percentualDecimal = BigDecimal.valueOf(percentualDescontoFinal).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal valorDesconto = subtotal.multiply(percentualDecimal).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFinal = subtotal.subtract(valorDesconto).setScale(2, RoundingMode.HALF_UP);

        // Retorna o Resumo (os getters no ResumoCarrinho devem ser getSubtotal, getDesconto e getTotal)
        return new ResumoCarrinho(subtotal, valorDesconto, totalFinal);
    }
}