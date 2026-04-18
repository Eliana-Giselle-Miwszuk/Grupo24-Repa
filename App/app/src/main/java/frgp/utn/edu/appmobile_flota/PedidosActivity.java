package frgp.utn.edu.appmobile_flota;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frgp.utn.edu.appmobile_flota.model.Pedido;
import frgp.utn.edu.appmobile_flota.remote.ApiService;
import frgp.utn.edu.appmobile_flota.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidosActivity extends AppCompatActivity implements PedidoAdapter.OnPedidoActionListener {

    private RecyclerView rvPedidos;
    private PedidoAdapter adapter;
    private List<Pedido> listaPedidos = new ArrayList<>();
    private Long repartidorId;
    private ImageButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_repartidor);

        // 1. Referencias de UI
        TextView tvTitulo = findViewById(R.id.tvTituloLista);
        rvPedidos = findViewById(R.id.rvPedidosRepartidor);
        btnLogout = findViewById(R.id.btnLogout);

        rvPedidos.setLayoutManager(new LinearLayoutManager(this));

        // 2. Inicializar el adapter con la interfaz de escucha (this)
        adapter = new PedidoAdapter(listaPedidos, this);
        rvPedidos.setAdapter(adapter);

        // 3. Recibir datos del repartidor desde el Login
        repartidorId = getIntent().getLongExtra("REPARTIDOR_ID", 0);
        String nombreRepartidor = getIntent().getStringExtra("REPARTIDOR_NOMBRE");

        if (nombreRepartidor != null) {
            tvTitulo.setText("Pedidos de " + nombreRepartidor);
        }

        // 4. Lógica de Cerrar Sesión
        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Estás seguro de que quieres salir?")
                    .setPositiveButton("Sí, salir", (dialog, which) -> {
                        Intent intent = new Intent(PedidosActivity.this, MainActivity.class);
                        // Limpia el historial de actividades para que no pueda volver atrás
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        cargarPedidos();
    }

    private void cargarPedidos() {
        // Asegúrate de que la IP sea correcta para el emulador (10.0.2.2) o tu PC
        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8080/").create(ApiService.class);

        Call<List<Pedido>> call = apiService.getPedidosByRepartidor(repartidorId);
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaPedidos = response.body();
                    adapter.updateList(listaPedidos);
                } else {
                    Toast.makeText(PedidosActivity.this, "Error al cargar pedidos del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(PedidosActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- MÉTODOS DE LA INTERFAZ OnPedidoActionListener ---

    @Override
    public void onEntregarClick(Pedido pedido) {
        Intent intent = new Intent(this, ConfirmarEntregaActivity.class);
        // ENVIAMOS EL ID (Long) para que el Backend pueda procesar la entrega
        intent.putExtra("PEDIDO_ID", pedido.getId());
        intent.putExtra("NUMERO_PEDIDO", pedido.getNumero_pedido());
        intent.putExtra("CLIENTE_NOMBRE", pedido.getCliente().getNombre());
        intent.putExtra("DIRECCION", pedido.getCliente().getDireccion());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onVerMapaClick(Pedido pedido) {
        Toast.makeText(this, "Abriendo ubicación de: " + pedido.getCliente().getDireccion(), Toast.LENGTH_SHORT).show();
        // Aquí podrías implementar el Intent de Google Maps
    }

    @Override
    public void onReportarClick(Pedido pedido) {
        Intent intent = new Intent(this, ReportarIncidenciaActivity.class);
        // CAMBIO CLAVE: Enviamos el ID numérico (Long) para el reporte
        intent.putExtra("PEDIDO_ID", pedido.getId());
        startActivityForResult(intent, 2);
    }

    // --- RECARGAR LISTA AL VOLVER DE UNA ACCIÓN ---

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Si la otra actividad terminó con RESULT_OK, refrescamos la lista
        if (resultCode == RESULT_OK) {
            cargarPedidos();
        }
    }
}