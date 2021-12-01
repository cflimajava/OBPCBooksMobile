package br.com.obpcbooks.enums;

public enum Roles {

    ROLE_ADM("ROLE_ADM"),
    ROLE_BASIC("ROLE_BASIC");

    private String descricao;

    Roles(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

}
