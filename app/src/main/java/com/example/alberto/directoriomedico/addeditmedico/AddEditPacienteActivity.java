package com.example.alberto.directoriomedico.addeditmedico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.alberto.directoriomedico.MainActivity;
import com.example.alberto.directoriomedico.R;


public class AddEditPacienteActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_PACIENTE = 1;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_paciente);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String pacienteId = getIntent().getStringExtra(MainActivity.EXTRA_PACIENTE_ID);

        //id del medico
        String medicoId = getIntent().getStringExtra(MainActivity.EXTRA_MEDICO_ID);

        setTitle(pacienteId == null? "Añadir Paciente" :"Editar Paciente");

        AddEditPacienteFragment fragment = (AddEditPacienteFragment)getSupportFragmentManager().findFragmentById(R.id.add_edit_paciente_container);

        if(fragment == null){
            fragment = AddEditPacienteFragment.newInstance(pacienteId,medicoId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_paciente_container,fragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
