package com.innovationhtb.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.innovationhtb.loginapp.R;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductModel> productList;

    public ProductAdapter(List<ProductModel> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getCharacteristic());
        if (!product.getPrices().isEmpty()) {
            holder.productPrice.setText("$" + product.getPrices().get(0).getValue());
        } else {
            holder.productPrice.setText("Price not available");
        }
        holder.productImage.setImageResource(R.drawable.placeholder_image); // Imagen fija o carga dinámica
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textViewProductName);
            productDescription = itemView.findViewById(R.id.textViewProductDescription);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productImage = itemView.findViewById(R.id.imageViewProduct);
        }
    }
}
