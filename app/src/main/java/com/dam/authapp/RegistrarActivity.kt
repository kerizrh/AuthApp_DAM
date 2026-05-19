package com.dam.authapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrarActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnRegresar: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        // Configurar barra de título
        supportActionBar?.title = getString(R.string.registro_title)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AuthAppPrefs", MODE_PRIVATE)

        // Vincular vistas
        etUsuario = findViewById(R.id.etRegUsuario)
        etEmail = findViewById(R.id.etRegEmail)
        etPassword = findViewById(R.id.etRegPassword)
        etConfirmPassword = findViewById(R.id.etRegConfirmPassword)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnRegresar = findViewById(R.id.btnRegresar)

        // RF-06/RF-07: Validaciones y guardado
        btnGuardar.setOnClickListener {
            if (validarFormulario()) {
                guardarDatos()
            }
        }

        // RF-08: Cancelación de registro
        btnRegresar.setOnClickListener {
            finish()
        }
    }

    /**
     * RF-06: Validaciones de datos en registro.
     * Valida todos los campos del formulario según las reglas de negocio.
     * @return true si todas las validaciones pasan
     */
    private fun validarFormulario(): Boolean {
        val usuario = etUsuario.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        // Validación: Nombre de usuario ≥ 3 caracteres
        if (usuario.length < 3) {
            Toast.makeText(this, getString(R.string.error_usuario_corto), Toast.LENGTH_SHORT).show()
            etUsuario.requestFocus()
            return false
        }

        // Validación: Password ≥ 5 caracteres
        if (password.length < 5) {
            Toast.makeText(this, getString(R.string.error_password_corto), Toast.LENGTH_SHORT).show()
            etPassword.requestFocus()
            return false
        }

        // Validación: Password alfanumérico (solo letras y números)
        val alphanumericRegex = Regex("^[a-zA-Z0-9]+$")
        if (!alphanumericRegex.matches(password)) {
            Toast.makeText(this, getString(R.string.error_password_alfanumerico), Toast.LENGTH_SHORT).show()
            etPassword.requestFocus()
            return false
        }

        // Validación: Confirmar Password coincide con Password
        if (password != confirmPassword) {
            Toast.makeText(this, getString(R.string.error_password_no_coincide), Toast.LENGTH_SHORT).show()
            etConfirmPassword.requestFocus()
            return false
        }

        // Validación: Email con formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.error_email_invalido), Toast.LENGTH_SHORT).show()
            etEmail.requestFocus()
            return false
        }

        return true
    }

    /**
     * RF-07: Persistencia de datos y limpieza.
     * Guarda las credenciales en SharedPreferences, muestra confirmación y limpia campos.
     */
    private fun guardarDatos() {
        val usuario = etUsuario.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val email = etEmail.text.toString().trim()

        // Guardar en SharedPreferences (clave/valor)
        val editor = sharedPreferences.edit()
        editor.putString("username", usuario)
        editor.putString("password", password)
        editor.putString("email", email)
        editor.apply()

        // Notificación de éxito
        Toast.makeText(this, getString(R.string.registro_exitoso), Toast.LENGTH_LONG).show()

        // Limpiar todos los campos
        etUsuario.text.clear()
        etEmail.text.clear()
        etPassword.text.clear()
        etConfirmPassword.text.clear()
    }
}
