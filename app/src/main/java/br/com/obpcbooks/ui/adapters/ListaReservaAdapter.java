package br.com.obpcbooks.ui.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.obpcbooks.R;
import br.com.obpcbooks.dto.ReservaDTO;
import br.com.obpcbooks.enums.ReservaStatus;
import br.com.obpcbooks.utils.Utils;

public class ListaReservaAdapter extends
        RecyclerView.Adapter<ListaReservaAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private final List<ReservaDTO> reservas;
    private Context context;

    public ListaReservaAdapter(List<ReservaDTO> reservas, Context context, OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
        this.reservas = reservas;
        this.context = context;
    }


    @NonNull
    @Override
    public ListaReservaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_lista_reserva, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaReservaAdapter.ViewHolder holder, int position) {
        ReservaDTO reserva = reservas.get(position);
        holder.vincula(reserva);
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView campoDataCriacao;
        private TextView campoReservaId;
        private TextView campoStatus;
        private Button botaoCancelar;
        private ReservaDTO reserva;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            campoDataCriacao = itemView.findViewById(R.id.txt_data_criacao);
            campoReservaId = itemView.findViewById(R.id.txt_reserva_id);
            campoStatus = itemView.findViewById(R.id.txt_status_reserva);
            botaoCancelar = itemView.findViewById(R.id.btn_cancelar_reserva);
            botaoCancelar.setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition(), reserva));

        }


        void vincula(ReservaDTO reservaItem) {
            this.reserva = reservaItem;
            campoDataCriacao.setText(Utils.formatarData(reservaItem.getDateCreation()));
            campoReservaId.setText(reservaItem.getId());
            campoStatus.setText(reservaItem.getStatus());
            if(reservaItem.getStatus().equals(ReservaStatus.PENDENTE.getDescricao()) ){
                botaoCancelar.setEnabled(true);
            }else if(reservaItem.getStatus().equals(ReservaStatus.CANCELADO.getDescricao())){
                campoStatus.setTextColor(Color.RED);
                botaoCancelar.setVisibility(View.INVISIBLE);
            }else{
                botaoCancelar.setEnabled(false);
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int posicao, ReservaDTO reserva);
    }

}
