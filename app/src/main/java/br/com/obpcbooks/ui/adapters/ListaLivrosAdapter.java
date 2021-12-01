package br.com.obpcbooks.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.LivroDTO;

public class ListaLivrosAdapter extends
        RecyclerView.Adapter<ListaLivrosAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private final OnItemClickListener onItemButtonClickListener;
    private final List<LivroDTO> livros;
    private Context context;
    public static int typeList = 0;  // 0- Lista tela de pesquisa 1- Lista tela da Cesta de livros

    public ListaLivrosAdapter(List<LivroDTO> livros, Context context, int typeList, OnItemClickListener onItemClickListener, OnItemClickListener onItemButtonClickListener){
        this.onItemClickListener = onItemClickListener;
        this.onItemButtonClickListener = onItemButtonClickListener;
        this.livros = livros;
        this.context = context;
        this.typeList = typeList;
    }


    @NonNull
    @Override
    public ListaLivrosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_lista_livro, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaLivrosAdapter.ViewHolder holder, int position) {
        LivroDTO livro = livros.get(position);
        holder.vincula(livro);
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tituloView;
        private TextView autorView;
        private Button addButton;
        private Button removeButton;
        private LivroDTO livro;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloView = itemView.findViewById(R.id.livro_titulo);
            autorView = itemView.findViewById(R.id.livro_autor);
            addButton = itemView.findViewById(R.id.btn_add_livo_cesta);
            removeButton = itemView.findViewById(R.id.btn_remove_livo_cesta);

            if(ListaLivrosAdapter.typeList == 0){
                configuraItemClique(itemView);
                addButton.setVisibility(View.VISIBLE);
                removeButton.setVisibility(View.INVISIBLE);
                configuraItemBotaoClique(addButton);
            }else if(ListaLivrosAdapter.typeList == 1){
                addButton.setVisibility(View.INVISIBLE);
                removeButton.setVisibility(View.VISIBLE);
                configuraItemBotaoClique(removeButton);
            }


        }

        private void configuraItemClique(@NonNull View itemView) {
            itemView.setOnClickListener(v -> onItemClickListener
                    .onItemClick(getAdapterPosition(), livro));
        }

        private void configuraItemBotaoClique(Button button) {
            button.setOnClickListener(v -> onItemButtonClickListener.onItemClick(getAdapterPosition(), livro));
        }

        void vincula(LivroDTO livroItem) {
            this.livro = livroItem;
            tituloView.setText(livroItem.getTitle());
            autorView.setText(livroItem.getAuthor());
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int posicao, LivroDTO livro);
    }

}
