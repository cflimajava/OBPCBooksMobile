package br.com.obpcbooks.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.LivroDTO;
import br.com.obpcbooks.model.Cesta;
import br.com.obpcbooks.model.DadosDeSessao;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.repositories.LivroRepository;
import br.com.obpcbooks.retrofit.callbacks.CallbackAction;
import br.com.obpcbooks.ui.activities.MainActivity;
import br.com.obpcbooks.ui.adapters.ListaLivrosAdapter;

public class PesquisaLivrosFragment extends Fragment {

    private Context context;
    private LivroRepository livroRepository;
    private DadosDeSessao sessao;
    private User userLogado;


    private TextView campoMessagemFedback;
    private RecyclerView campoListaLivros;
    private EditText campoTituloBusca;
    private MaterialButton botaoBuscaLivros;

    public PesquisaLivrosFragment(){
        super(R.layout.fragment_pesquisa);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.livroRepository = new LivroRepository();
        this.context = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pesquisa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        inicializaCampos();
        getActivity().setTitle("Pesquisa de Livros");
        sessao = (DadosDeSessao) requireArguments().getSerializable("sessao");
        userLogado = sessao.getUsuarioLogado();
    }


    private void inicializaCampos() {
        campoListaLivros = getView().findViewById(R.id.view_lista_livros);
        campoTituloBusca = getView().findViewById(R.id.editText_filtro_titulo);
        campoMessagemFedback = getView().findViewById(R.id.search_menssagem_fedback);
        botaoBuscaLivros = getView().findViewById(R.id.btn_filtrar);

        botaoBuscaLivros.setOnClickListener(v -> {
            buscarLivroPorTitulo();
        });
    }

    public void buscarLivroPorTitulo(){
        String tituloBusca = campoTituloBusca.getText().toString();

        if(tituloBusca.isEmpty()){
            getTodosLivros(userLogado);
        }else{
            livroRepository.buscarLivroPorTitulo(userLogado, tituloBusca, criarCallbackParaBuscaDeLivro());
        }
    }



    private void getTodosLivros(User user){
        livroRepository.buscaTodosOsLivros(user, criarCallbackParaBuscaDeLivro());
    }

    private CallbackAction<List<LivroDTO>> criarCallbackParaBuscaDeLivro(){
        return new CallbackAction<List<LivroDTO>>() {
            @Override
            public void quandoSucesso(List<LivroDTO> resultado) {
                if(resultado == null || resultado.size() == 0){
                    mostrarMenssagemFedback("Nenhum exemplar disponível com titulo informado.");
                }else{
                    esconderMenssagemFedback();
                    inicializaListaAdapter(resultado);
                }
            }

            @Override
            public void quandoFalha(String erro) {
                Toast.makeText(context, "FALHA AO BUSCAR LIVROS "+ erro, Toast.LENGTH_LONG).show();
            }
        };
    }

    private void mostrarMenssagemFedback(String menssagem){
        campoListaLivros.setVisibility(View.INVISIBLE);
        campoMessagemFedback.setVisibility(View.VISIBLE);
        campoMessagemFedback.setText(menssagem);

    }

    private void esconderMenssagemFedback(){
        campoListaLivros.setVisibility(View.VISIBLE);
        campoMessagemFedback.setVisibility(View.INVISIBLE);
    }

    public void addLivroNaCesta(LivroDTO livroEscolhido){
        Cesta.addLivro(livroEscolhido);
    }

    private void openFragment(Fragment fragment, LivroDTO livro){
        Bundle bundle = new Bundle();
        bundle.putSerializable("sessao", sessao);

        if(livro != null){
            bundle.putSerializable("livroSelecionado", livro);
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.main_fragment, fragment.getClass(), bundle)
                .commit();
    }

    private void abrirLivroDetalhes(LivroDTO livro){
        openFragment(new LivroDetalhesFragment(), livro);
        Toast.makeText(getContext(), "chamou click no item: "+livro.getTitle(), Toast.LENGTH_LONG).show();
    }


    private void inicializaListaAdapter(List<LivroDTO> livrosEncontrados){
        ListaLivrosAdapter adapter = new ListaLivrosAdapter(livrosEncontrados, context, 0,
            (posicao, livro) ->{
                abrirLivroDetalhes(livro);
            },
            (posicao, livro) -> {
                addLivroNaCesta(livro);
            }
        );
        campoListaLivros.setAdapter(adapter);
    }
}
