package br.com.obpcbooks.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.obpcbooks.dto.LivroDTO;

public class DadosDeSessao implements Serializable {

    private static DadosDeSessao instance;
    private static User usuarioLogado;
    private static Map<String, Object> itens;

    public static DadosDeSessao getSessao(){
        if(instance == null){
            instance = new DadosDeSessao();
            itens = new HashMap<>();
        }
        return instance;
    }

    public void setItem(String chave, Object valor){
        itens.put(chave, valor);
    }

    public Object getItem(String chave){
        return itens.get(chave);
    }

    public DadosDeSessao comUsuarioLogado(User user){
        usuarioLogado = user;
        return this;
    }

    public User getUsuarioLogado(){
        return usuarioLogado;
    }

}
