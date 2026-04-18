package frgp.utn.edu.appmobile_flota;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import frgp.utn.edu.appmobile_flota.remote.ApiService;
import frgp.utn.edu.appmobile_flota.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmarEntregaActivity extends AppCompatActivity {

    private TextInputEditText etCodigoVerificacion;
    private Button btnFinalizarEntrega;
    private long pedidoId;
    private ImageView ivEvidencia;
    private Bitmap fotoBitmap;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fotoBitmap = (Bitmap) result.getData().getExtras().get("data");
                    ivEvidencia.setImageBitmap(fotoBitmap);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_entrega);

        pedidoId = getIntent().getLongExtra("PEDIDO_ID", -1);
        ivEvidencia = findViewById(R.id.ivEvidencia);
        etCodigoVerificacion = findViewById(R.id.etCodigoVerificacion);
        btnFinalizarEntrega = findViewById(R.id.btnFinalizarEntrega);

        // --- AGREGADO: Referencia al botón cancelar ---
        Button btnCancelarEntrega = findViewById(R.id.btnCancelarEntrega);

        findViewById(R.id.cardFoto).setOnClickListener(v -> {
            cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        });

        btnFinalizarEntrega.setOnClickListener(v -> {
            String codigo = etCodigoVerificacion.getText().toString().trim();
            if (fotoBitmap == null || codigo.isEmpty()) {
                Toast.makeText(this, "Foto y código obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }
            enviarEntrega(codigo);
        });

        // --- AGREGADO: Lógica para volver atrás ---
        btnCancelarEntrega.setOnClickListener(v -> {
            finish(); // Cierra esta actividad y regresa a la anterior (la lista)
        });
    }

    private void enviarEntrega(String codigo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        fotoBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String fotoBase64 = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);

        Map<String, Object> datos = new HashMap<>();
        datos.put("pedidoId", pedidoId);
        datos.put("codigo", codigo);
        datos.put("fotoBase64", fotoBase64);

        ApiService api = RetrofitClient.getClient("http://10.0.2.2:8080/").create(ApiService.class);
        api.validarEntrega(datos).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    Toast.makeText(ConfirmarEntregaActivity.this, "Entrega exitosa", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ConfirmarEntregaActivity.this, "Código incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ConfirmarEntregaActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}