package br.com.obpcbooks.retrofit.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackComentarioSemRetorno<T> implements Callback<T> {

    private final CallbackActionSemRetorno callback;

    public CallbackComentarioSemRetorno(CallbackActionSemRetorno callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            callback.quandoSucessoSemRetorno();
        }else {
            callback.quandoFalha("ERRO AO DELETAR COMENTARIO");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.quandoFalha("Falha ao chamar API de COMENTARIO: " + t.getMessage());
    }

}
