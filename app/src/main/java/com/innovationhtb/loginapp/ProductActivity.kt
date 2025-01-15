package com.innovationhtb.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innovationhtb.loginapp.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    private var binding: ActivityProductBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        // Configuración de la tabla y botones
        setupCreateButton()
        setupLogoutButton()

        // Ejemplo de producto en la tabla
        val products: MutableList<Product> = ArrayList<Product>()
        products.add(Product("Producto Ejemplo", "12345", "PROD001", "Electrónica, Móviles"))
        setupProductTable(products)
    }

    private fun setupCreateButton() {
        binding.createProductButton.setOnClickListener { v ->
            // Aquí iría la lógica para crear un nuevo producto
            Toast.makeText(this, "Crear Producto Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener { v ->
            // Redirigir a la pantalla de inicio de sesión
            val intent =
                Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finalizar la actividad para evitar que el usuario regrese a ella con el botón atrás
        }
    }

    private fun setupProductTable(products: List<Product>) {
        // Configuración del RecyclerView con la tabla
        val recyclerView: RecyclerView = binding.productTableRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter: ProductAdapter = ProductAdapter(products) { product ->
            // Lógica para eliminar un producto
            Toast.makeText(
                this,
                "Eliminar Producto: " + product.getName(),
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.adapter = adapter
    }
}