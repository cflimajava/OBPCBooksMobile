package br.com.obpcbooks.retrofit.services;

import java.util.List;

import br.com.obpcbooks.dto.LivroDTO;
import br.com.obpcbooks.dto.ReservaDTO;
import br.com.obpcbooks.dto.UserDTO;
import br.com.obpcbooks.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReservaService {

    @POST("/obpc/booking")
    Call<ReservaDTO> finalizarReserva(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token,@Body ReservaDTO reservaDTO);

    @GET("/obpc/booking/user/{userId}")
    Call<List<ReservaDTO>> buscarReservasPorUsuario(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token, @Path("userId") String userId);

    @PUT("/obpc/booking")
    Call<ReservaDTO> updateReserva(@Header("HEADERS_REQUESTER") String userID, @Header("HEADERS_TOKEN") String token,@Body ReservaDTO reservaDTO);

}
