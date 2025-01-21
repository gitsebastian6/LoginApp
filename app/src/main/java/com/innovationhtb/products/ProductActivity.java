package com.innovationhtb.products;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovationhtb.loginapp.ApiService;
import com.innovationhtb.loginapp.R;
import com.innovationhtb.loginapp.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Referencias a los elementos del layout
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        // Referencia al botón Agregar
        Button buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddProduct.setOnClickListener(v -> addProduct());

        // Llamar a la API para obtener productos existentes
        fetchProductsFromBackend();
    }

    private void fetchProductsFromBackend() {
        ApiService productService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ProductModel>> call = productService.getAllProducts();

        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductModel> products = response.body();
                    productAdapter = new ProductAdapter(products);
                    recyclerViewProducts.setAdapter(productAdapter);
                } else {
                    Toast.makeText(ProductActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addProduct() {
        // Obtener los datos desde los EditTexts
        EditText editTextProductName = findViewById(R.id.editTextProductName);
        EditText editTextProductDescription = findViewById(R.id.editTextProductDescription);
        EditText editTextProductPrice = findViewById(R.id.editTextProductPrice);
        EditText editTextProductCompany = findViewById(R.id.editTextProductCompany);
        EditText editTextProductCode = findViewById(R.id.editTextProductCode);

        String name = editTextProductName.getText().toString().trim();
        String description = editTextProductDescription.getText().toString().trim();
        String priceString = editTextProductPrice.getText().toString().trim();
        String company = editTextProductCompany.getText().toString().trim();
        String code = editTextProductCode.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (name.isEmpty() || description.isEmpty() || priceString.isEmpty() || company.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir el precio de String a double
        double price = Double.parseDouble(priceString);

        // Crear una lista de precios (solo un precio en este ejemplo)
        ProductModel.Price productPrice = new ProductModel.Price(price);
        List<ProductModel.Price> priceList = new ArrayList<>();
        priceList.add(productPrice);

        // Crear el objeto ProductModel con los datos ingresados
        ProductModel newProduct = new ProductModel("0", code, name, description, 10, priceList);

        // Llamar a la API para agregar el producto
        addProductToBackend(newProduct);
    }

    private void addProductToBackend(ProductModel product) {
        ApiService productService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = productService.createProduct(product);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductActivity.this, "Producto creado exitosamente", Toast.LENGTH_SHORT).show();
                    fetchProductsFromBackend();  // Actualizar la lista de productos después de agregar uno nuevo
                } else {
                    Toast.makeText(ProductActivity.this, "Error al crear el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
