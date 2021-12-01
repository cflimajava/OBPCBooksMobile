package br.com.obpcbooks.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.ComentarioDTO;
import br.com.obpcbooks.dto.LivroDTO;
import br.com.obpcbooks.dto.UserDTO;
import br.com.obpcbooks.model.DadosDeSessao;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.repositories.LivroRepository;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.retrofit.callbacks.CallbackActionSemRetorno;
import br.com.obpcbooks.ui.adapters.ListaComentarioAdapter;
import br.com.obpcbooks.ui.adapters.ListaLivrosAdapter;
import br.com.obpcbooks.utils.Utils;


public class LivroDetalhesFragment extends Fragment {

    private Context context;
    private DadosDeSessao sessao;
    private User usuarioLogado;
    private LivroDTO livroSelecionado;
    private LivroRepository livroRepository;
    private List<ComentarioDTO> listaComentarios;
    private ComentarioDTO comentario;

    private TextView campoTitulo;
    private TextView campoAutor;
    private TextView campoAnoLancamento;
    private TextView campoEditora;
    private TextView campoResumo;
    private TextView campoQtdCopiasDisponiveis;
    private EditText campoComentario;
    private RecyclerView campoListaComentarios;
    private Button btnSalvarComentario;

    public LivroDetalhesFragment() {
        inicializarCampos();
    }

    private void inicializarCampos(){}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Detalhes");
        sessao = (DadosDeSessao) requireArguments().getSerializable("sessao");
        usuarioLogado = sessao.getUsuarioLogado();
        livroRepository = new LivroRepository();
        listaComentarios = new ArrayList<ComentarioDTO>();
        context = getContext();
        livroSelecionado = (LivroDTO) requireArguments().getSerializable("livroSelecionado");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializaCampos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_livro_detalhes, container, false);
    }

    private void inicializaCampos(){
        campoTitulo = getView().findViewById(R.id.detalhes_titulo);
        campoTitulo.setText(livroSelecionado.getTitle());

        campoAutor = getView().findViewById(R.id.detalhes_autor);
        campoAutor.setText(livroSelecionado.getAuthor());

        campoAnoLancamento = getView().findViewById(R.id.detalhes_ano_lancamento);
        campoAnoLancamento.setText(livroSelecionado.getYear().toString());

        campoEditora = getView().findViewById(R.id.detalhes_editora);
        campoEditora.setText(livroSelecionado.getPublisher());

        campoQtdCopiasDisponiveis = getView().findViewById(R.id.detalhes_qtd_copias_disponiveis);
        campoQtdCopiasDisponiveis.setText(livroSelecionado.getAvailables().toString());

        campoComentario = getView().findViewById(R.id.editText_comentario);

        campoListaComentarios = getView().findViewById(R.id.rv_lista_comentarios);

        btnSalvarComentario = getView().findViewById(R.id.btn_add_comentario);



        btnSalvarComentario.setOnClickListener(v -> {
            salvarComentario();
        });

        getComentarios();
    }

    private void getComentarios(){
        if(livroSelecionado != null){
         livroRepository.buscarComentarioPorLivroId(usuarioLogado, livroSelecionado.getId(), new CallbackAction<List<ComentarioDTO>>() {
             @Override
             public void quandoSucesso(List<ComentarioDTO> resultado) {
                 listaComentarios = resultado;
                 inicializaListaAdapter(listaComentarios);
             }

             @Override
             public void quandoFalha(String erro) {
                 Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
             }
         });
        }

        inicializaListaAdapter(listaComentarios);
    }

    private void criarComentario(){
        if(this.comentario != null && this.comentario.getId() != null){
            String txtComentario = campoComentario.getText().toString();
            this.comentario.setText(txtComentario);
        }else{
            this.comentario = new ComentarioDTO();
            comentario.setBookId(livroSelecionado.getId());
            comentario.setUser(new UserDTO(usuarioLogado));
            String txtComentario = campoComentario.getText().toString();
            comentario.setText(txtComentario);
        }
    }

    private void salvarComentario(){
        criarComentario();

        livroRepository.adicioanarComentario(usuarioLogado, this.comentario, new CallbackAction<ComentarioDTO>() {
            @Override
            public void quandoSucesso(ComentarioDTO comentarioSalvo) {
                limparComentario();
                getComentarios();
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void limparComentario() {
        campoComentario.setText("");
        comentario = null;
    }

    private void inicializaListaAdapter(List<ComentarioDTO> listaComentarios){
        ListaComentarioAdapter adapter = new ListaComentarioAdapter(listaComentarios, usuarioLogado, context,
            (posicao, comentario) -> {
                    deletarComentario(comentario.getId());
            },
            (posicao, comentario) -> {
                editarComentario(comentario);
            }
        );
        campoListaComentarios.setAdapter(adapter);
    }

    private void editarComentario(ComentarioDTO comentarioSelecionado){
        this.comentario = comentarioSelecionado;
        this.comentario.setDateCreation(Utils.formatarData(comentarioSelecionado.getDateCreation()));
        campoComentario.setText(comentarioSelecionado.getText());
    }

    private void deletarComentario(String comentarioId){
        livroRepository.deletaComentario(usuarioLogado, comentarioId, new CallbackActionSemRetorno() {
            @Override
            public void quandoSucessoSemRetorno() {
                Toast.makeText(getContext(), "Comentario excluido com sucesso", Toast.LENGTH_LONG).show();
                getComentarios();
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
            }
        });
    }
}