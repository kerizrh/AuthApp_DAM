package com.dam.authapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnGuardar;
    private Button btnRegresar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Configurar barra de título
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.registro_title));
        }

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AuthAppPrefs", MODE_PRIVATE);

        // Vincular vistas
        etUsuario = findViewById(R.id.etRegUsuario);
        etEmail = findViewById(R.id.etRegEmail);
        etPassword = findViewById(R.id.etRegPassword);
        etConfirmPassword = findViewById(R.id.etRegConfirmPassword);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnRegresar = findViewById(R.id.btnRegresar);

        // / Validaciones y guardado
        btnGuardar.setOnClickListener(v -> {
            if (validarFormulario()) {
                guardarDatos();
            }
        });

        // Cancelación de registro
        btnRegresar.setOnClickListener(v -> finish());
    }

    /**
     * : Validaciones de datos en registro.
     * Valida todos los campos del formulario según las reglas de negocio.
     * 
     * @return true si todas las validaciones pasan
     */
    private boolean validarFormulario() {
        String usuario = etUsuario.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validación: Nombre de usuario ≥ 3 caracteres
        if (usuario.length() < 3) {
            Toast.makeText(this, getString(R.string.error_usuario_corto), Toast.LENGTH_SHORT).show();
            etUsuario.requestFocus();
            return false;
        }

        // Validación: Password ≥ 5 caracteres
        if (password.length() < 5) {
            Toast.makeText(this, getString(R.string.error_password_corto), Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }

        // Validación: Password alfanumérico (solo letras y números)
        if (!password.matches("^[a-zA-Z0-9]+$")) {
            Toast.makeText(this, getString(R.string.error_password_alfanumerico), Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }

        // Validación: Confirmar Password coincide con Password
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, getString(R.string.error_password_no_coincide), Toast.LENGTH_SHORT).show();
            etConfirmPassword.requestFocus();
            return false;
        }

        // Validación: Email con formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.error_email_invalido), Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Persistencia de datos y limpieza.
     * Guarda las credenciales en SharedPreferences, muestra confirmación y limpia
     * campos.
     */
    private void guardarDatos() {
        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Guardar en SharedPreferences (clave/valor)
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", usuario);
        editor.putString("password", password);
        editor.putString("email", email);
        editor.apply();

        // Notificación de éxito
        Toast.makeText(this, getString(R.string.registro_exitoso), Toast.LENGTH_LONG).show();

        // Limpiar todos los campos
        etUsuario.getText().clear();
        etEmail.getText().clear();
        etPassword.getText().clear();
        etConfirmPassword.getText().clear();
    }
}
