package br.com.obpcbooks.retrofit.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class CallbackComRetorno<T> implements Callback<T> {

    private final CallbackAction<T> callback;

    public CallbackComRetorno(CallbackAction<T> callback) {
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
            callback.quandoFalha(response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.quandoFalha("Falha ao chamar API: " + t.getMessage());
    }

}
