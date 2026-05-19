package com.dam.authapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dam.authapp.fragments.InicioFragment
import com.dam.authapp.fragments.PerfilFragment
import com.dam.authapp.fragments.ProductosFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Configurar barra de título
        supportActionBar?.title = getString(R.string.nav_inicio)

        bottomNav = findViewById(R.id.bottom_nav)

        // RF-10: Cargar fragmento inicial por defecto
        if (savedInstanceState == null) {
            loadFragment(InicioFragment())
        }

        // RF-10: Conmutación de fragmentos según selección
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    supportActionBar?.title = getString(R.string.nav_inicio)
                    loadFragment(InicioFragment())
                    true
                }
                R.id.nav_productos -> {
                    supportActionBar?.title = getString(R.string.nav_productos)
                    loadFragment(ProductosFragment())
                    true
                }
                R.id.nav_perfil -> {
                    supportActionBar?.title = getString(R.string.nav_perfil)
                    loadFragment(PerfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Reemplaza el fragmento actual en el contenedor dinámico.
     * @param fragment El fragmento a incrustar
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
