package com.example.alberto.directoriomedico;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.alberto.directoriomedico.medicodetail.MedicoDetailFragment;

/**
 * Created by Tommy on 20/05/2018.
 */

public class PacientesActivity extends AppCompatActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Lista de Pacientes");

        String id = getIntent().getStringExtra(MainActivity.EXTRA_MEDICO_ID);

        PacientesFragment fragment = (PacientesFragment)getSupportFragmentManager().findFragmentById(R.id.pacientes_container);


        if(fragment == null){
            fragment = PacientesFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.pacientes_container,fragment)
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_pacient);
        fab.setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_account_plus,null));
    }
}
