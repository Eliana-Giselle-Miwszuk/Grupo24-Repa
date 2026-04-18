package frgp.utn.edu.appmobile_flota;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import frgp.utn.edu.appmobile_flota.model.LoginRequest;
import frgp.utn.edu.appmobile_flota.model.LoginResponse;
import frgp.utn.edu.appmobile_flota.remote.ApiService;
import frgp.utn.edu.appmobile_flota.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Cambiamos el nombre para que coincida con el XML nuevo
    private TextInputEditText etEmail, etPassword;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciamos los IDs exactos del XML adaptado
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor, completa email y contraseña", Toast.LENGTH_SHORT).show();
            } else {
                hacerLogin(email, pass);
            }
        });
    }

    private void hacerLogin(String email, String password) {
        // IP 10.0.2.2 es para el emulador de Android apuntando al localhost de tu PC
        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8080/").create(ApiService.class);

        // Creamos el DTO que espera Spring Boot
        LoginRequest request = new LoginRequest(email, password);

        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();
                    String rol = res.getRol();

                    // Manejo de roles según los ENUMS de tu Backend (ADMINISTRADOR / REPARTIDOR)
                    if ("REPARTIDOR".equalsIgnoreCase(rol)) {
                        Toast.makeText(MainActivity.this, "Bienvenido Repartidor: " + res.getNombre(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, PedidosActivity.class);
                        // Pasamos datos a la siguiente pantalla
                        intent.putExtra("REPARTIDOR_ID", res.getId());
                        intent.putExtra("REPARTIDOR_NOMBRE", res.getNombre());
                        startActivity(intent);
                        finish(); // Cerramos el login para que no pueda volver atrás
                    }
                    else if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {
                        // Por ahora lo mandamos a una pantalla de Admin o avisamos
                        Toast.makeText(MainActivity.this, "Bienvenido Administrador", Toast.LENGTH_SHORT).show();
                        // Intent intentAdmin = new Intent(MainActivity.this, AdminActivity.class);
                        // startActivity(intentAdmin);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Rol desconocido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El servidor respondió pero con error (ej. 401 Unauthorized)
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Error de red o servidor caído
                Log.e("API_ERROR", "Fallo: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error de conexión con el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }
}