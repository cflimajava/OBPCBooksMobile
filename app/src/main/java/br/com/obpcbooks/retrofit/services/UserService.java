package br.com.obpcbooks.retrofit.services;

import br.com.obpcbooks.dto.UserDTO;
import br.com.obpcbooks.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @GET("/obpc/user/login")
    Call<User> login(@Header("username") String username, @Header("password") String senha);

    @POST("/obpc/user/create")
    Call<User> registrar(@Body UserDTO userDTO);
}
