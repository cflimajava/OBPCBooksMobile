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
import br.com.obpcbooks.dto.ComentarioDTO;
import br.com.obpcbooks.model.User;
import br.com.obpcbooks.utils.Utils;

public class ListaComentarioAdapter extends
        RecyclerView.Adapter<ListaComentarioAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListenerToDelete;
    private final OnItemClickListener onItemClickListenerToUpdate;
    private final List<ComentarioDTO> listaComentarios;
    private Context context;
    private User usuarioLogado;

    public ListaComentarioAdapter(List<ComentarioDTO> listaComentarios, User usuarioLogado, Context context, OnItemClickListener onItemClickListenerToDelete,
                                  OnItemClickListener onItemClickListenerToUpdate){
        this.onItemClickListenerToDelete = onItemClickListenerToDelete;
        this.onItemClickListenerToUpdate = onItemClickListenerToUpdate;
        this.listaComentarios = listaComentarios;
        this.context = context;
        this.usuarioLogado = usuarioLogado;

    }


    @NonNull
    @Override
    public ListaComentarioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_comentario, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaComentarioAdapter.ViewHolder holder, int position) {
        ComentarioDTO comentario = listaComentarios.get(position);
        holder.vincula(comentario);
    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView campoUsuario;
        private TextView campoDataCriacao;
        private TextView campoTexto;
        private Button botaoExcluir;
        private Button botaoEditar;
        private ComentarioDTO comentario;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            campoDataCriacao = itemView.findViewById(R.id.txt_comentario_data_criacao);
            campoUsuario = itemView.findViewById(R.id.txt_comentario_user);
            campoTexto = itemView.findViewById(R.id.txt_comentario);
            botaoExcluir = itemView.findViewById(R.id.btn_excluir_comentario);
            botaoExcluir.setOnClickListener(v -> onItemClickListenerToDelete.onItemClick(getAdapterPosition(), comentario));

            botaoEditar = itemView.findViewById(R.id.btn_editar_comentario);
            botaoEditar.setOnClickListener(v -> onItemClickListenerToUpdate.onItemClick(getAdapterPosition(), comentario));

        }


        void vincula(ComentarioDTO comentarioItem) {
            this.comentario = comentarioItem;
            campoDataCriacao.setText(Utils.formatarData(comentarioItem.getDateCreation()));
            campoUsuario.setText(comentarioItem.getUser().getUsername());
            campoTexto.setText(comentarioItem.getText());

            if(comentarioItem.getUser().getId().equals(usuarioLogado.getId())){
                botaoEditar.setEnabled(true);
                botaoEditar.setVisibility(View.VISIBLE);
                botaoExcluir.setEnabled(true);
                botaoExcluir.setVisibility(View.VISIBLE);
            }else{
                botaoEditar.setEnabled(false);
                botaoEditar.setVisibility(View.INVISIBLE);
                botaoExcluir.setEnabled(false);
                botaoExcluir.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int posicao, ComentarioDTO comentario);
    }

}
