package frgp.utn.edu.appmobile_flota;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.HashMap;
import java.util.Map;
import frgp.utn.edu.appmobile_flota.remote.ApiService;
import frgp.utn.edu.appmobile_flota.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportarIncidenciaActivity extends AppCompatActivity {

    private Spinner spinnerMotivo;
    private TextInputEditText etDescripcion;
    private long pedidoId; // Cambiado a long para coincidir con la BD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_incidencia);

        // Recibimos el ID como Long. El -1 es por si no llega nada.
        pedidoId = getIntent().getLongExtra("PEDIDO_ID", -1);

        spinnerMotivo = findViewById(R.id.spinnerMotivo);
        etDescripcion = findViewById(R.id.etDescripcion);
        Button btnEnviar = findViewById(R.id.btnEnviarReporte);
        Button btnCancelar = findViewById(R.id.btnCancelarReporte);

        String[] opciones = {"Cliente ausente", "Dirección incorrecta", "Pedido dañado", "Zona peligrosa", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMotivo.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> enviarReporte());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void enviarReporte() {
        String descripcionStr = etDescripcion.getText().toString().trim();
        String motivoStr = spinnerMotivo.getSelectedItem().toString();

        if (descripcionStr.isEmpty()) {
            etDescripcion.setError("Debe detallar el motivo");
            return;
        }

        Map<String, Object> datos = new HashMap<>();
        datos.put("pedidoId", pedidoId); // Asegúrate que sea Long
        datos.put("motivo", motivoStr);
        datos.put("descripcionAdicional", descripcionStr);

        // Asegúrate que la URL coincida con la de tu Controller (/pedidos)
        ApiService api = RetrofitClient.getClient("http://10.0.2.2:8080/").create(ApiService.class);

        api.reportarIncidencia(datos).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toast.makeText(ReportarIncidenciaActivity.this, "✅ Incidencia reportada", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ReportarIncidenciaActivity.this, "❌ Error al procesar reporte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(ReportarIncidenciaActivity.this, "⚠️ Sin conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}