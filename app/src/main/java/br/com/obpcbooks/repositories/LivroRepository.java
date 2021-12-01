package br.com.obpcbooks.repositories;

import java.util.List;

import br.com.obpcbooks.dto.ComentarioDTO;
import br.com.obpcbooks.dto.LivroDTO;
import br.com.obpcbooks.dto.ReservaDTO;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.retrofit.ConfiguracaoRetrofit;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.retrofit.callbacks.CallbackActionSemRetorno;
import br.com.obpcbooks.retrofit.callbacks.CallbackComRetorno;
import br.com.obpcbooks.retrofit.callbacks.CallbackComentarioComRetorno;
import br.com.obpcbooks.retrofit.callbacks.CallbackComentarioSemRetorno;
import br.com.obpcbooks.retrofit.callbacks.CallbackLivrosComRetorno;
import br.com.obpcbooks.retrofit.services.ComentarioService;
import br.com.obpcbooks.retrofit.services.LivroService;
import br.com.obpcbooks.retrofit.services.ReservaService;
import retrofit2.Call;

public class LivroRepository {

    private final LivroService livroService;

    private final ComentarioService comentarioService;


    public LivroRepository() {
        ConfiguracaoRetrofit configuracaoRetrofit = new ConfiguracaoRetrofit();

        this.livroService = configuracaoRetrofit.getLivroService();
        this.comentarioService = configuracaoRetrofit.getComentarioService();

    }

    public void buscarLivroPorTitulo(User user, String titulo, CallbackAction<List<LivroDTO>> callback){
        Call<List<LivroDTO>> call = livroService.bucarLivroPorTitulo(user.getId(), user.getToken(), titulo);

        call.enqueue(new CallbackLivrosComRetorno<List<LivroDTO>>(new CallbackAction<List<LivroDTO>>() {
            @Override
            public void quandoSucesso(List<LivroDTO> resultado) {
                callback.quandoSucesso(resultado);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }


    public void buscaTodosOsLivros(User user, CallbackAction<List<LivroDTO>> callback){
        Call<List<LivroDTO>> call = livroService.buscarTodosLivros(user.getId(), user.getToken());
        call.enqueue(new CallbackLivrosComRetorno<>(new CallbackAction<List<LivroDTO>>() {
            @Override
            public void quandoSucesso(List<LivroDTO> todosLivros) {
                callback.quandoSucesso(todosLivros);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }


    public void adicioanarComentario(User user, ComentarioDTO comentario ,CallbackAction<ComentarioDTO> callback){
        Call<ComentarioDTO> call = comentarioService.salvarComentario(user.getId(), user.getToken(), comentario);
        call.enqueue(new CallbackComentarioComRetorno<>(new CallbackAction<ComentarioDTO>() {
            @Override
            public void quandoSucesso(ComentarioDTO comentarioSalvo) {
                callback.quandoSucesso(comentarioSalvo);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    public void deletaComentario(User user, String comentarioId, CallbackActionSemRetorno callback){
        Call<Void> call = comentarioService.deletarComentarioPeloId(user.getId(), user.getToken(), comentarioId);
        call.enqueue(new CallbackComentarioSemRetorno<>(new CallbackActionSemRetorno() {
            @Override
            public void quandoSucessoSemRetorno() {
                callback.quandoSucessoSemRetorno();
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    public void buscarComentarioPorLivroId(User user, String livroId, CallbackAction<List<ComentarioDTO>> callback){
        Call<List<ComentarioDTO>> call = comentarioService.bucarComentarioPorLivroId(user.getId(), user.getToken(), livroId);
        call.enqueue(new CallbackComentarioComRetorno<>(new CallbackAction<List<ComentarioDTO>>() {
            @Override
            public void quandoSucesso(List<ComentarioDTO> listaComentarios) {
                callback.quandoSucesso(listaComentarios);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

}
