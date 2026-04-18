package frgp.utn.edu.appmobile_flota;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import frgp.utn.edu.appmobile_flota.model.Pedido;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> listaPedidos;
    private OnPedidoActionListener actionListener;

    public interface OnPedidoActionListener {
        void onEntregarClick(Pedido pedido);
        void onVerMapaClick(Pedido pedido);
        void onReportarClick(Pedido pedido);
    }

    public PedidoAdapter(List<Pedido> listaPedidos, OnPedidoActionListener listener) {
        this.listaPedidos = listaPedidos;
        this.actionListener = listener;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);

        if (pedido.getCliente() != null) {
            holder.tvNombreCliente.setText(pedido.getCliente().getNombre());
            holder.tvDireccion.setText(pedido.getCliente().getDireccion());
        }

        // USAMOS EL NOMBRE QUE DEFINISTE EN TU MODELO DE ANDROID
        holder.tvNumeroPedido.setText("#" + pedido.getNumero_pedido());

        // Manejo de estado
        String estadoStr = (pedido.getEstado() != null) ? pedido.getEstado().toUpperCase() : "";

        // Resetear visualización para evitar errores de reciclaje
        holder.itemView.setAlpha(1.0f);
        holder.btnEntregar.setEnabled(true);
        holder.btnReportar.setEnabled(true);
        holder.btnVerMapa.setEnabled(true);

        switch (estadoStr) {
            case "ENTREGADO":
                holder.viewEstadoColor.setBackgroundColor(Color.parseColor("#4CAF50")); // Verde
                holder.btnEntregar.setText("ENTREGADO");
                holder.itemView.setAlpha(0.5f);
                holder.btnEntregar.setEnabled(false);
                holder.btnReportar.setEnabled(false);
                holder.btnVerMapa.setEnabled(false);
                break;

            case "PENDIENTE":
            case "EN_CAMINO":
                holder.viewEstadoColor.setBackgroundColor(Color.parseColor("#FFC107")); // Amarillo
                holder.btnEntregar.setText("ENTREGAR");
                break;

            case "NO_ENTREGADO":
                holder.viewEstadoColor.setBackgroundColor(Color.parseColor("#F44336")); // Rojo
                holder.btnEntregar.setText("REINTENTAR");
                break;

            default:
                holder.viewEstadoColor.setBackgroundColor(Color.GRAY);
                holder.btnEntregar.setText("VER");
                break;
        }

        holder.btnEntregar.setOnClickListener(v -> actionListener.onEntregarClick(pedido));
        holder.btnVerMapa.setOnClickListener(v -> actionListener.onVerMapaClick(pedido));
        holder.btnReportar.setOnClickListener(v -> actionListener.onReportarClick(pedido));
    }

    @Override
    public int getItemCount() { return listaPedidos != null ? listaPedidos.size() : 0; }

    public void updateList(List<Pedido> nuevaLista) {
        this.listaPedidos = nuevaLista;
        notifyDataSetChanged();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCliente, tvNumeroPedido, tvDireccion;
        View viewEstadoColor;
        Button btnVerMapa, btnEntregar, btnReportar;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvNumeroPedido = itemView.findViewById(R.id.tvNumeroPedido);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            viewEstadoColor = itemView.findViewById(R.id.viewEstadoColor);
            btnVerMapa = itemView.findViewById(R.id.btnVerMapa);
            btnEntregar = itemView.findViewById(R.id.btnEntregar);
            btnReportar = itemView.findViewById(R.id.btnReportar);
        }
    }
}