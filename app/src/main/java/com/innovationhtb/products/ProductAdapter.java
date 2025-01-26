package com.innovationhtb.products;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.innovationhtb.loginapp.ApiService;
import com.innovationhtb.loginapp.R;
import com.innovationhtb.loginapp.RetrofitClient;
import com.innovationhtb.products.models.Price;
import com.innovationhtb.products.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    // Constructor
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getCharacteristic());
        //holder.productQuantity.setText(product.getQuantity());
        //holder.productCode.setText(product.getCode());
        String prices="";
        for (Price price:product.getPrices()
             ) {
            prices+=price.getCurrency().getDescription()+" "+price.getValue()+" ";

        }
        holder.productPrice.setText(prices);
        holder.productImage.setImageResource(R.drawable.cellicon);

        // Configurar el botón de eliminar
        holder.deleteButton.setOnClickListener(v -> {
            // Eliminar producto
            removeProduct(position);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Eliminar producto por posición
    private void removeProduct(int position) {
        Product productToDelete = productList.get(position);
        String productId = productToDelete.getId();  // Obtén el ID del producto

        ApiService apiService = RetrofitClient.getApiService();
        apiService.deleteProduct(productId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la eliminación fue exitosa en el backend, eliminar el producto de la lista
                    productList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice, productQuantity, productCode;
        ImageView productImage, deleteButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textViewProductName);
            productDescription = itemView.findViewById(R.id.textViewProductDescription);
            //productQuantity = itemView.findViewById(R.id.textViewProductQuantity);
           // productCode = itemView.findViewById(R.id.textViewProductCode);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productImage = itemView.findViewById(R.id.imageViewProduct);
            deleteButton = itemView.findViewById(R.id.deleteButton);  // Referencia al botón eliminar
        }
    }
}
