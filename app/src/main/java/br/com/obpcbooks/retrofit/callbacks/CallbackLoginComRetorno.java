package br.com.obpcbooks.retrofit.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackLoginComRetorno<T> implements Callback<T> {

    private final CallbackAction<T> callback;

    public CallbackLoginComRetorno(CallbackAction<T> callback) {
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
            if(response.code() == 403){
                callback.quandoFalha("USUARIO OU SENHA INVÁLIDOS");
            }else if(response.code() == 412){
                callback.quandoFalha("USUARIO NÃO ATIVO");
            }else{
                callback.quandoFalha("ERROR AO FAZER LOGIN");
            }

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.quandoFalha("Falha ao chamar API: " + t.getMessage());
    }

}
