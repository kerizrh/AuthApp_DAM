package com.dam.authapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnIngresar: Button
    private lateinit var btnSalir: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar barra de título
        supportActionBar?.title = getString(R.string.login_title)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AuthAppPrefs", MODE_PRIVATE)

        // Vincular vistas
        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnSalir = findViewById(R.id.btnSalir)

        // RF-02: Validación de credenciales
        btnIngresar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            val savedUsuario = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (usuario == savedUsuario && password == savedPassword) {
                // Credenciales correctas → navegar a HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Credenciales incorrectas → mostrar error
                Toast.makeText(
                    this,
                    getString(R.string.error_credenciales),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // RF-03: Cierre de la aplicación (botón)
        btnSalir.setOnClickListener {
            finishAffinity()
        }
    }

    // Inflar menú superior
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Manejar selección del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // RF-04: Navegación a Registro
            R.id.action_registrar -> {
                val intent = Intent(this, RegistrarActivity::class.java)
                startActivity(intent)
                true
            }
            // RF-03: Cierre de la aplicación (menú)
            R.id.action_salir -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
