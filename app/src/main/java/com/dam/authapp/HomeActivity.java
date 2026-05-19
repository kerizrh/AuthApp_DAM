package com.dam.authapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dam.authapp.fragments.InicioFragment;
import com.dam.authapp.fragments.PerfilFragment;
import com.dam.authapp.fragments.ProductosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Configurar barra de título
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.nav_inicio));
        }

        bottomNav = findViewById(R.id.bottom_nav);

        // Cargar fragmento inicial por defecto
        if (savedInstanceState == null) {
            loadFragment(new InicioFragment());
        }

        // Conmutación de fragmentos según selección
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_inicio) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.nav_inicio));
                }
                loadFragment(new InicioFragment());
                return true;
            } else if (id == R.id.nav_productos) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.nav_productos));
                }
                loadFragment(new ProductosFragment());
                return true;
            } else if (id == R.id.nav_perfil) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.nav_perfil));
                }
                loadFragment(new PerfilFragment());
                return true;
            }

            return false;
        });
    }

    /**
     * Reemplaza el fragmento actual en el contenedor dinámico.
     * 
     * @param fragment El fragmento a incrustar
     */
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
