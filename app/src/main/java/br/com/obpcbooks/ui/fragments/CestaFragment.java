package br.com.obpcbooks.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.LivroDTO;
import br.com.obpcbooks.dto.ReservaDTO;
import br.com.obpcbooks.model.Cesta;
import br.com.obpcbooks.model.DadosDeSessao;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.repositories.LivroRepository;
import br.com.obpcbooks.repositories.ReservaRepository;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.ui.adapters.ListaLivrosAdapter;

public class CestaFragment extends Fragment {

    private ReservaRepository reservaRepository;

    private User userLogado;
    private RecyclerView campoListaLivros;
    private Button btn_finalizar_solicitacao;
    private DadosDeSessao sessao;

    public CestaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.reservaRepository = new ReservaRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cesta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        inicializaCampos();
        getActivity().setTitle("Cesta de Livros");
        this.sessao = (DadosDeSessao) requireArguments().getSerializable("sessao");
        this.userLogado = sessao.getUsuarioLogado();
    }

    public void finalizarSolicitacao(){
        reservaRepository.finalizarReserva(userLogado,
                new ReservaDTO(userLogado, Cesta.getListaLivro()),
                new CallbackAction<ReservaDTO>() {
            @Override
            public void quandoSucesso(ReservaDTO reservaFeita) {
                if(reservaFeita.getId() != null){
                    final TextView campoReservadoId = getView().findViewById(R.id.txt_msg_reservado_id);
                    final TextView campoMessagemReservadoComSucesso = getView().findViewById(R.id.txt_msg_reservado_com_sucesso);

                    campoReservadoId.setText(reservaFeita.getId());
                    campoMessagemReservadoComSucesso.setText(getMenssagemSucesso());

                    getView().findViewById(R.id.include_lista_cesta_livros).setVisibility(View.INVISIBLE);
                    getView().findViewById(R.id.include_msg_successo).setVisibility(View.VISIBLE);
                    esvaziarCesta();

                    Toast.makeText(getContext(), "Reserva realizada com sucesso.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
            }
        });
    }


    private String getMenssagemSucesso(){
        StringBuilder sb = new StringBuilder();
            sb.append("\nReserva realizada com sucesso.\n");
            sb.append("Livro(s) deverão ser retirado(s) no prazo maximo de 72h.\n");
            sb.append("Caso contrário reserva será cancelada automáticamente.\n");
            sb.append("Abaixo número da RESERVA.\n");
        return sb.toString();
    }

    private void inicializaCampos() {
        campoListaLivros = getView().findViewById(R.id.fragment_cesta_livros_selecionados);
        btn_finalizar_solicitacao = getView().findViewById(R.id.btn_finalizar_solicitacao);
        btn_finalizar_solicitacao.setOnClickListener(v -> {
            finalizarSolicitacao();
        });
        inicializaListaAdapter(Cesta.getListaLivro());
        gerenciarApareciaBotao();
    }

    private void gerenciarApareciaBotao(){
        btn_finalizar_solicitacao.setEnabled(!Cesta.getListaLivro().isEmpty());
    }


    private void removeLivroDaCesta(LivroDTO livro){
        Cesta.removeLivro(livro.getId());
        inicializaListaAdapter(Cesta.getListaLivro());
        gerenciarApareciaBotao();
    }

    private void esvaziarCesta(){
        Cesta.esvaziarCesta();
        inicializaListaAdapter(Cesta.getListaLivro());
        gerenciarApareciaBotao();
    }


    private void inicializaListaAdapter(List<LivroDTO> livrosEncontrados){
        ListaLivrosAdapter adapter = new ListaLivrosAdapter(livrosEncontrados, getContext(),1, null,  (posicao, livro) -> {
            removeLivroDaCesta(livro);
        });
        campoListaLivros.setAdapter(adapter);
    }

}