package com.seuprojeto.marketplace.domain.model;

public enum CategoriaProduto {
    CAPINHA(3),
    CARREGADOR(5),
    FONE(3),
    PELICULA(2),
    SUPORTE(2),
    OUTROS(0);

    private final int percentualDesconto;

    CategoriaProduto(int percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public int getPercentualDesconto() {
        return percentualDesconto;
    }
}