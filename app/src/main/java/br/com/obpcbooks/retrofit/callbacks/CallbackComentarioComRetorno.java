package br.com.obpcbooks.retrofit.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackComentarioComRetorno<T> implements Callback<T> {

    private final CallbackAction<T> callback;

    public CallbackComentarioComRetorno(CallbackAction<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            T resultado = response.body();
            if(resultado != null){
                callback.quandoSucesso(resultado);
            }
        }else {
                callback.quandoFalha("ERRO AO CARREGAR COMENTARIOS");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.quandoFalha("Falha ao chamar API para COMENTARIO: " + t.getMessage());
    }

}
