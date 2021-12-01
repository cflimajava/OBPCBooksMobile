package br.com.obpcbooks.retrofit;

import org.jetbrains.annotations.NotNull;

import br.com.obpcbooks.retrofit.services.ComentarioService;
import br.com.obpcbooks.retrofit.services.LivroService;
import br.com.obpcbooks.retrofit.services.ReservaService;
import br.com.obpcbooks.retrofit.services.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfiguracaoRetrofit {

    private static final String URL_BASE = "http://192.168.0.106:8587/";

    private final UserService userService;
    private final LivroService livroService;
    private final ReservaService reservaService;
    private final ComentarioService comentarioService;

    public ConfiguracaoRetrofit(){
        OkHttpClient client = configuraClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        livroService = retrofit.create(LivroService.class);
        reservaService = retrofit.create(ReservaService.class);
        comentarioService = retrofit.create(ComentarioService.class);
    }


    @NotNull
    private OkHttpClient configuraClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public UserService getUserService(){
        return this.userService;
    }

    public LivroService getLivroService(){
        return this.livroService;
    }

    public ReservaService getReservaService(){
        return this.reservaService;
    }

    public ComentarioService getComentarioService(){return this.comentarioService;}

}
