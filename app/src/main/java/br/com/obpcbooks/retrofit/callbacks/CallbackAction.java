package br.com.obpcbooks.retrofit.callbacks;

public interface CallbackAction<T> {
    void quandoSucesso(T resultado);
    void quandoFalha(String erro);
}
