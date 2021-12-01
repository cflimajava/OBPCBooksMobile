package br.com.obpcbooks.retrofit.services;

import java.util.List;

import br.com.obpcbooks.dto.LivroDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LivroService {

    @GET("/obpc/book/findAll")
    Call<List<LivroDTO>> buscarTodosLivros(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token);

    @GET("/obpc/book/title/{titulo}")
    Call<List<LivroDTO>> bucarLivroPorTitulo(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token, @Path("titulo") String titulo);
}
