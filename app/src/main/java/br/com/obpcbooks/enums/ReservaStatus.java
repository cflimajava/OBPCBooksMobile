package br.com.obpcbooks.enums;

public enum ReservaStatus {

    PENDENTE("PENDENTE"),
    ATIVO("ATIVO"),
    ATRASADO("DEVOLUCAO PENDENTE"),
    FINALIZADO("FINALIZADO"),
    CANCELADO("CANCELADO");

    private String descricao;

    ReservaStatus(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
