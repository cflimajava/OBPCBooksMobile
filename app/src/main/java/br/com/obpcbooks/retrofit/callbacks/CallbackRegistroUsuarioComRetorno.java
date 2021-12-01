package br.com.obpcbooks.retrofit.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackRegistroUsuarioComRetorno<T> implements Callback<T> {

    private final CallbackAction<T> callback;

    public CallbackRegistroUsuarioComRetorno(CallbackAction<T> callback) {
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
            if(response.code() == 422){
                callback.quandoFalha("EMAIL JÁ CADASTRADO");
            }else if(response.code() == 428 ){
                callback.quandoFalha("SENHA NÃO INFORMADA");
            }else{
                callback.quandoFalha("Erro ao registrar usuario. HTTP CODE: "+response.code());
            }

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.quandoFalha("Falha ao chamar API: " + t.getMessage());
    }

}
