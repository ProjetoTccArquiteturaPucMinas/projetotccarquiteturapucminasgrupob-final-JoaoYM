package com.seuprojeto.marketplace.domain.model;

import java.math.BigDecimal;

public class ResumoCarrinho {

    private BigDecimal subtotal;
    private BigDecimal desconto;
    private BigDecimal total;
    private int percentualDescontoTotal;

    public ResumoCarrinho(BigDecimal subtotal, BigDecimal desconto) {
        this.subtotal = subtotal;
        this.desconto = desconto;
        this.total = subtotal.subtract(desconto);
    }

    public ResumoCarrinho(BigDecimal subtotal, int percentualDescontoTotal, BigDecimal valorDesconto, BigDecimal totalFinal) {
        this.subtotal = subtotal;
        this.percentualDescontoTotal = percentualDescontoTotal;
        this.desconto = valorDesconto;
        this.total = totalFinal;
    }

    public ResumoCarrinho(BigDecimal subtotal2, BigDecimal valorDesconto, BigDecimal totalFinal) {
        //TODO Auto-generated constructor stub
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        atualizarTotal();
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        atualizarTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }

    private void atualizarTotal() {
        if (subtotal != null && desconto != null) {
            this.total = subtotal.subtract(desconto);
        }
    }

    public int getPercentualDescontoTotal() { return percentualDescontoTotal; }
}