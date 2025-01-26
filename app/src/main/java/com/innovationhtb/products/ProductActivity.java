package com.innovationhtb.products;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovationhtb.loginapp.ApiService;
import com.innovationhtb.loginapp.R;
import com.innovationhtb.loginapp.RetrofitClient;
import com.innovationhtb.products.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProductActivity  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button btnAddProduct = findViewById(R.id.buttonAddProduct);

        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, CreateProductActivity.class);
            startActivity(intent);
        });

        fetchProducts();
    }
    private void openCreateProductActivity() {
        Intent intent = new Intent(this, CreateProductActivity.class);
        createProductLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            fetchProducts();
        }
    }
    public final ActivityResultLauncher<Intent> createProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    fetchProducts();
                }
            });

    private void fetchProducts() {
        ApiService apiService = RetrofitClient.getApiService();
        retrofit2.Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Product>> call, retrofit2.Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();

                    runOnUiThread(() -> {
                        productAdapter = new ProductAdapter(ProductActivity.this, productList);
                        recyclerView.setAdapter(productAdapter);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(ProductActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Product>> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(ProductActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                    Log.e("ProductError", t.getMessage(), t);
                });
            }



        });
    }

}