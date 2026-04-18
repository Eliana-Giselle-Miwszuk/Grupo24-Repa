package frgp.utn.edu.appmobile_flota.remote;

import java.util.Map;
import java.util.List;
import frgp.utn.edu.appmobile_flota.model.LoginRequest;
import frgp.utn.edu.appmobile_flota.model.LoginResponse;
import frgp.utn.edu.appmobile_flota.model.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("usuario/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @GET("pedidos/repartidor/{id}")
    Call<List<Pedido>> getPedidosByRepartidor(@Path("id") Long id);

    // Cambia esto en tu ApiService.java
    @POST("pedidos/validar-entrega")
    Call<Boolean> validarEntrega(@Body Map<String, Object> datos);
    @POST("pedidos/reportar-incidencia")
    Call<Boolean> reportarIncidencia(@Body Map<String, Object> datos);
}