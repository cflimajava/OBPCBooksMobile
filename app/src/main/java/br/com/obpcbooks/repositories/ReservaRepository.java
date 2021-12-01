package br.com.obpcbooks.repositories;

import java.util.List;

import br.com.obpcbooks.dto.ReservaDTO;
import br.com.obpcbooks.enums.ReservaStatus;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.retrofit.ConfiguracaoRetrofit;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.retrofit.callbacks.CallbackComRetorno;
import br.com.obpcbooks.retrofit.services.ReservaService;
import retrofit2.Call;

public class ReservaRepository {

    private final ReservaService reservaService;

    public ReservaRepository() {
        ConfiguracaoRetrofit configuracaoRetrofit = new ConfiguracaoRetrofit();
        this.reservaService = configuracaoRetrofit.getReservaService();
    }


    public void buscarTodasReservasByUserId(User userLogado, CallbackAction callback){
        Call<List<ReservaDTO>> call = reservaService.buscarReservasPorUsuario(userLogado.getId(), userLogado.getToken(), userLogado.getId());
        call.enqueue(new CallbackComRetorno<>(new CallbackAction<List<ReservaDTO>>() {
            @Override
            public void quandoSucesso(List<ReservaDTO> todasReservas) {
                for (ReservaDTO reserva : todasReservas) {
                    reserva.popularListaDeLivrosID();
                }
                callback.quandoSucesso(todasReservas);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    public void finalizarReserva(User userLogado, ReservaDTO reservaDTO ,CallbackAction callback){
        Call<ReservaDTO> call = reservaService.finalizarReserva(userLogado.getId(), userLogado.getToken(), reservaDTO);
        call.enqueue(new CallbackComRetorno<>(new CallbackAction<ReservaDTO>() {
            @Override
            public void quandoSucesso(ReservaDTO reservaFeita) {
                callback.quandoSucesso(reservaFeita);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }

        }));
    }

    public void atualizarReserva(User userLogado, ReservaDTO reservaDTO, CallbackAction callback){
        Call<ReservaDTO> call = reservaService.updateReserva(userLogado.getId(), userLogado.getToken(), reservaDTO);
        call.enqueue(new CallbackComRetorno<>(new CallbackAction<ReservaDTO>() {
            @Override
            public void quandoSucesso(ReservaDTO reservaCancelada) {
                callback.quandoSucesso(reservaCancelada);
            }

            @Override
            public void quandoFalha(String erro) {
                    callback.quandoFalha(erro);
            }
        }));
    }
}
