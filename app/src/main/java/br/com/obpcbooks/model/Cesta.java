package br.com.obpcbooks.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.obpcbooks.dto.LivroDTO;

public class Cesta {

    private static Cesta instance;
    private static Map<String, LivroDTO> livros;

    public static Cesta getCesta(){
        if(instance == null){
            instance = new Cesta();
            livros = new HashMap<>();
        }

        return instance;
    }

    public static List<LivroDTO> getListaLivro(){
        List<LivroDTO> listaLivros = new ArrayList<>();
        for (Map.Entry<String, LivroDTO> entry : livros.entrySet()) {
            String s = entry.getKey();
            listaLivros.add(entry.getValue());
        }
        return  listaLivros;
    }

    public static void addLivro(LivroDTO livro){
        livros.put(livro.getId(), livro);
    }

    public static LivroDTO getLivro(String livroId){
        return livros.get(livroId);
    }

    public static void removeLivro(String livroId){
        livros.remove(livroId);
    }

    public static void esvaziarCesta(){
        livros.clear();
    }
}
