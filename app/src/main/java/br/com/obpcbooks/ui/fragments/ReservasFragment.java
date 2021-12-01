package br.com.obpcbooks.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.ReservaDTO;
import br.com.obpcbooks.enums.ReservaStatus;
import br.com.obpcbooks.model.DadosDeSessao;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.repositories.ReservaRepository;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.ui.adapters.ListaReservaAdapter;
import br.com.obpcbooks.utils.Utils;

public class ReservasFragment extends Fragment {

    private ReservaRepository reservaRepository;
    private DadosDeSessao sessao;
    private User userLogado;
    private RecyclerView campoListaReservas;
    private Switch campoSwitchPendentes;
    private Switch campoSwitchCancelados;
    private Switch campoSwitchAtivos;
    private Switch campoSwitchFinalizados;
    private Switch campoSwitchAtrasados;


    private List<ReservaDTO> todasReservasList;
    private Map<String, ReservaStatus> listaItensFiltro = new HashMap<>();


    public ReservasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reservaRepository = new ReservaRepository();
        userLogado = DadosDeSessao.getSessao().getUsuarioLogado();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reservas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Reservas");
        inicializaCampos();
        this.sessao = (DadosDeSessao) requireArguments().getSerializable("sessao");
        this.userLogado = sessao.getUsuarioLogado();
    }

    private void inicializaCampos(){
        campoListaReservas = getView().findViewById(R.id.rv_lista_reservas);
        campoSwitchPendentes = getView().findViewById(R.id.switch_tipo_reserva_pendentes);
        campoSwitchCancelados = getView().findViewById(R.id.switch_tipo_reserva_cancelados);
        campoSwitchAtivos = getView().findViewById(R.id.switch_tipo_reserva_ativos);
        campoSwitchFinalizados = getView().findViewById(R.id.switch_tipo_reserva_finalizados);
        campoSwitchAtrasados = getView().findViewById(R.id.switch_tipo_reserva_atrasados);
        setComportamentoFiltroTipoReservas();
        buscarTodasReservasByUserId();
    }

    private void setComportamentoFiltroTipoReservas(){
        listaItensFiltro.put(ReservaStatus.PENDENTE.getDescricao(), ReservaStatus.PENDENTE);

        campoSwitchCancelados.setOnClickListener(v -> {
            if(campoSwitchCancelados.isChecked()){
                listaItensFiltro.put(ReservaStatus.CANCELADO.getDescricao(), ReservaStatus.CANCELADO);
            }else{
                listaItensFiltro.remove(ReservaStatus.CANCELADO.getDescricao());
            }
            filtraLista();
        });

        campoSwitchPendentes.setOnClickListener(v -> {
            if(campoSwitchPendentes.isChecked()){
                listaItensFiltro.put(ReservaStatus.PENDENTE.getDescricao(), ReservaStatus.PENDENTE);
            }else{
                listaItensFiltro.remove(ReservaStatus.PENDENTE.getDescricao());
            }
            filtraLista();
        });

        campoSwitchAtivos.setOnClickListener(v -> {
            if(campoSwitchAtivos.isChecked()){
                listaItensFiltro.put(ReservaStatus.ATIVO.getDescricao(), ReservaStatus.ATIVO);
            }else{
                listaItensFiltro.remove(ReservaStatus.ATIVO.getDescricao());
            }
            filtraLista();
        });

        campoSwitchFinalizados.setOnClickListener(v -> {
            if(campoSwitchFinalizados.isChecked()){
                listaItensFiltro.put(ReservaStatus.FINALIZADO.getDescricao(), ReservaStatus.FINALIZADO);
            }else{
                listaItensFiltro.remove(ReservaStatus.FINALIZADO.getDescricao());
            }
            filtraLista();
        });

        campoSwitchAtrasados.setOnClickListener(v -> {
            if(campoSwitchAtrasados.isChecked()){
                listaItensFiltro.put(ReservaStatus.ATRASADO.getDescricao(), ReservaStatus.ATRASADO);
            }else{
                listaItensFiltro.remove(ReservaStatus.ATRASADO.getDescricao());
            }
            filtraLista();
        });

    }

    private void filtraLista(){
        List<ReservaDTO> listaFiltrada = new ArrayList<>();

        for (ReservaDTO reservaDTO : todasReservasList) {
            if (listaItensFiltro.containsKey(reservaDTO.getStatus())) {
                listaFiltrada.add(reservaDTO);
            }
        }
        inicializaListaAdapter(listaFiltrada);
    }



    private void buscarTodasReservasByUserId(){
        reservaRepository.buscarTodasReservasByUserId(this.userLogado, new CallbackAction<List<ReservaDTO>>() {
            @Override
            public void quandoSucesso(List<ReservaDTO> reservas) {
                todasReservasList = reservas;
                filtraLista();
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void inicializaListaAdapter(List<ReservaDTO> listaReservas){
        ListaReservaAdapter adapter = new ListaReservaAdapter(listaReservas, getContext(), (posicao, reserva) -> {
            mostarConfirmacaoCancelamento(reserva);
        });
        campoListaReservas.setAdapter(adapter);
    }

    private void mostarConfirmacaoCancelamento(ReservaDTO reserva){
        new AlertDialog.Builder(getContext())
                .setTitle("Cancelamento de Reserva")
                .setMessage("Deseja mesmo cancelar Reserva com ID: "+reserva.getId()+" ?")
                .setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelarReserva(reserva);
                                Toast.makeText(getContext(), "Reserva cancelda", Toast.LENGTH_LONG).show();

                            }
                        })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void cancelarReserva(ReservaDTO reserva) {
        reserva.setDateCreation(Utils.formatarData(reserva.getDateCreation()));
        reserva.setStatus(ReservaStatus.CANCELADO.getDescricao());
        reservaRepository.atualizarReserva(this.userLogado, reserva, new CallbackAction<ReservaDTO>() {
            @Override
            public void quandoSucesso(ReservaDTO reservaCancelada) {
                if(reservaCancelada.getStatus().equals(ReservaStatus.CANCELADO.getDescricao())){
                    Toast.makeText(getContext(), "Reserva cancelda com sucesso", Toast.LENGTH_LONG).show();
                }
                buscarTodasReservasByUserId();
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
            }
        });
    }
































}