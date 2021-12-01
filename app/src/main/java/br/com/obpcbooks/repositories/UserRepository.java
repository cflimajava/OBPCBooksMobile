package br.com.obpcbooks.repositories;

import android.content.Context;

import br.com.obpcbooks.dto.UserDTO;
import br.com.obpcbooks.asynctask.BaseAsyncTask;
import br.com.obpcbooks.asynctask.LimparUsuarioLogadosTask;
import br.com.obpcbooks.database.ObpcDatabase;
import br.com.obpcbooks.database.daos.UserDAO;
import br.com.obpcbooks.retrofit.ConfiguracaoRetrofit;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.retrofit.callbacks.CallbackComRetorno;
import br.com.obpcbooks.retrofit.callbacks.CallbackLoginComRetorno;
import br.com.obpcbooks.retrofit.callbacks.CallbackRegistroUsuarioComRetorno;
import br.com.obpcbooks.retrofit.services.UserService;
import retrofit2.Call;

public class UserRepository {

    private final UserDAO userDAO;
    private final UserService service;

    public UserRepository(Context context) {
        ObpcDatabase db = ObpcDatabase.getInstance(context);
        this.userDAO = db.getUserDAO();
        this.service = new ConfiguracaoRetrofit().getUserService();
    }

    public void fazerRegistro(UserDTO userDTO, CallbackAction<User> callback){
        Call<User> call = service.registrar(userDTO);
        call.enqueue(new CallbackRegistroUsuarioComRetorno<>(new CallbackAction<User>() {
            @Override
            public void quandoSucesso(User usuarioRegistrado) {
                callback.quandoSucesso(usuarioRegistrado);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }


    public void doLogin(String username, String senha, CallbackAction<User> callback){
        Call<User> call = service.login(username, senha);
        call.enqueue(new CallbackLoginComRetorno<>(new CallbackAction<User>() {
            @Override
            public void quandoSucesso(User user) {
                salvaInterno(user, callback);
                callback.quandoSucesso(user);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    private void salvaInterno(User usuarioLogado, CallbackAction<User> callback){
       new BaseAsyncTask<User>(()->{
            userDAO.liparUsuarioLogados();
            long id = userDAO.salva(usuarioLogado);
            return userDAO.buscaUsuarioPeloId(id);
        }, callback::quandoSucesso).execute();
    }

    public void doLogout(){
        new LimparUsuarioLogadosTask(this.userDAO).execute();
    }

    public void getUsuarioLogado(CallbackAction<User> callback){
        new BaseAsyncTask<User>(()->{
            return userDAO.bucarUsuarioLogado();
        }, callback::quandoSucesso).execute();
    }

}
