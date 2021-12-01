package br.com.obpcbooks.retrofit.services;

import java.util.List;

import br.com.obpcbooks.dto.ComentarioDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static br.com.obpcbooks.utils.ConstantesOBPCapp.URL_BASE;

public interface ComentarioService {

    @POST(URL_BASE +"/add")
    Call<ComentarioDTO> salvarComentario(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token, @Body ComentarioDTO comentarioDTO);

    @GET(URL_BASE +"/{livroId}")
    Call<List<ComentarioDTO>> bucarComentarioPorLivroId(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token, @Path("livroId") String livroId);

    @DELETE(URL_BASE+"/{comentarioId}")
    Call<Void> deletarComentarioPeloId(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token, @Path("comentarioId") String comentarioId);

}
