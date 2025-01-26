package com.innovationhtb.products;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.innovationhtb.loginapp.ApiService;
import com.innovationhtb.loginapp.R;
import com.innovationhtb.loginapp.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProductActivity extends AppCompatActivity {


    private EditText etCode, etName, etCharacteristic, etQuantity, etPrice;
    private Button btnCreateProduct;

    private static final String COMPANY_ID = "95f9aa77-b87c-4356-a083-8af8ff7ca5b7";
    private static final String CATEGORY_ID = "be81fea5-1c9d-4c13-aa60-76a4dce8ecaf";
    private static final String CURRENCY_ID = "f7494fd7-6f6c-4256-adf2-e570bb7ae934";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        // Inicializar vistas
        etCode = findViewById(R.id.etCode);
        etName = findViewById(R.id.etName);
        etCharacteristic = findViewById(R.id.etCharacteristic);
        etQuantity = findViewById(R.id.etQuantity);
        etPrice = findViewById(R.id.etPrice);
        btnCreateProduct = findViewById(R.id.btnCreateProduct);

        // Acción al hacer clic en el botón
        btnCreateProduct.setOnClickListener(v -> createProduct());
    }

    private void createProduct() {
        String code = etCode.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String characteristic = etCharacteristic.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if (code.isEmpty() || name.isEmpty() || characteristic.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        ProductRequest.Price priceRequest = new ProductRequest.Price(price, CURRENCY_ID);
        List<ProductRequest.Price> pricesList = new ArrayList<>(); // Lista mutable
        pricesList.add(priceRequest);
        List<String> categories = List.of(CATEGORY_ID);

        ProductRequest productRequest = new ProductRequest(code, name, characteristic, quantity, COMPANY_ID, pricesList, categories);

        sendProductToServer(productRequest);

    }

    private void sendProductToServer(ProductRequest productRequest) {
        RetrofitClient.getApiService().createProduct(productRequest)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CreateProductActivity.this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);

                            Intent intent = new Intent(CreateProductActivity.this, ProductActivity.class);
                            startActivity(intent);  // Usamos startActivity normalmente, no necesitamos el launcher aquí
                            finish();

                        } else {
                            Toast.makeText(CreateProductActivity.this, "Error al crear producto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CreateProductActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("CreateProductError", t.getMessage(), t);
                    }
        });
    }
}