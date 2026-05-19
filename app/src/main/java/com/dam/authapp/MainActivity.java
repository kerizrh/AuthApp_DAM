package com.dam.authapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etPassword;
    private Button btnIngresar;
    private Button btnSalir;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar barra de título
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.login_title));
        }

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AuthAppPrefs", MODE_PRIVATE);

        // Vincular vistas
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnSalir = findViewById(R.id.btnSalir);

        // Validación de credenciales
        btnIngresar.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            String savedUsuario = sharedPreferences.getString("username", null);
            String savedPassword = sharedPreferences.getString("password", null);

            if (usuario.equals(savedUsuario) && password.equals(savedPassword)) {
                // Credenciales correctas → navegar a HomeActivity
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Credenciales incorrectas → mostrar error
                Toast.makeText(
                        MainActivity.this,
                        getString(R.string.error_credenciales),
                        Toast.LENGTH_LONG).show();
            }
        });

        // Cierre de la aplicación (botón)
        btnSalir.setOnClickListener(v -> finishAffinity());
    }

    // Inflar menú superior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Manejar selección del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Navegación a Registro
        if (id == R.id.action_registrar) {
            Intent intent = new Intent(this, RegistrarActivity.class);
            startActivity(intent);
            return true;
        }

        // Cierre de la aplicación (menú)
        if (id == R.id.action_salir) {
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
