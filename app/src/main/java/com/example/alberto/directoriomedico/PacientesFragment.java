package com.example.alberto.directoriomedico;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alberto.directoriomedico.addeditmedico.AddEditMedicoActivity;
import com.example.alberto.directoriomedico.addeditmedico.AddEditPacienteActivity;
import com.example.alberto.directoriomedico.datos.MedicosContract;
import com.example.alberto.directoriomedico.datos.MedicosDbHelper;
import com.example.alberto.directoriomedico.datos.PacientesCursorAdapter;
import com.example.alberto.directoriomedico.medicodetail.PacienteDetailActivity;



public class PacientesFragment extends Fragment {

    private static final String ARG_MEDICO_ID = "medicoId";

    ListView mPacientesList;
    FloatingActionButton fabAddPaciente;
    PacientesCursorAdapter mPacientesAdapter;
    MedicosDbHelper mMedicosDBHelper;

    private static String mMedicoIdTest;

   // public static final int REQUEST_UPDATE_DELETE_MEDICO = 2;
    public static final int REQUEST_UPDATE_DELETE_PACIENTE = 2;



    public PacientesFragment() {
        // Required empty public constructor
    }

    public static PacientesFragment newInstance(String medicoId) {
        PacientesFragment fragment = new PacientesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID,medicoId);
        fragment.setArguments(args);
        mMedicoIdTest = medicoId;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment__pacientes,container,false);

        mPacientesList = (ListView)root.findViewById(R.id.pacientes_list);
        mPacientesAdapter =  new PacientesCursorAdapter(getActivity(),null,0);
        fabAddPaciente = (FloatingActionButton)getActivity().findViewById(R.id.fab_add_pacient);
        fabAddPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPacienteScreen();
            }
        });

        mPacientesList.setAdapter(mPacientesAdapter);

        mMedicosDBHelper = new MedicosDbHelper(getActivity());

        mPacientesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor)mPacientesAdapter.getItem(position);
                String currentPacienteId = currentItem.getString(currentItem.getColumnIndex(MedicosContract.PacienteEntry.ID));
                String currentMedicoId = currentItem.getString(currentItem.getColumnIndex(MedicosContract.PacienteEntry.ID_MEDICO));
                showDetailPacienteScreen(currentPacienteId,currentMedicoId);
            }
        });

        loadPacientes();
        return root;
    }

    public void showDetailPacienteScreen(String pacienteId,String medicoId){
        Intent intent = new Intent(getActivity(), PacienteDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_PACIENTE_ID,pacienteId);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,medicoId);
        startActivityForResult(intent,REQUEST_UPDATE_DELETE_PACIENTE);
    }



    private void showAddPacienteScreen(){
        Intent intent = new Intent(getActivity(),AddEditPacienteActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,mMedicoIdTest);
        startActivityForResult(intent,AddEditPacienteActivity.REQUEST_ADD_PACIENTE);
    }

    private void loadPacientes(){
        new PacientesLoadTask().execute();
    }

    private class PacientesLoadTask extends AsyncTask<Void,Void,Cursor> {


        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDBHelper.getAllPacientesByIdMedico(mMedicoIdTest);
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.getCount()>0){
                mPacientesAdapter.swapCursor(c);
            }else if (c!= null && c.getCount()==0){
                mPacientesAdapter.swapCursor(c);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            switch(requestCode){
                case AddEditMedicoActivity.REQUEST_ADD_MEDICO:
                    showSuccessfullSavedMessage();
                    loadPacientes();
                    break;
                case REQUEST_UPDATE_DELETE_PACIENTE:
                    loadPacientes();
                    break;

            }
        }
    }

    private void showSuccessfullSavedMessage(){
        Toast.makeText(getActivity(),"Paciente guardado correctamente",Toast.LENGTH_SHORT).show();
    }
}
