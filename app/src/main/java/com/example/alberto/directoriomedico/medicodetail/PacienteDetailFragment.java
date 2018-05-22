package com.example.alberto.directoriomedico.medicodetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alberto.directoriomedico.MainActivity;
import com.example.alberto.directoriomedico.PacientesFragment;
import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.addeditmedico.AddEditPacienteActivity;
import com.example.alberto.directoriomedico.datos.MedicosDbHelper;
import com.example.alberto.directoriomedico.datos.Pacientes;



public class PacienteDetailFragment extends Fragment {
    private static final String ARG_PACIENTE_ID = "pacienteId";
    private String mPacienteId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mDireccion;


    private static String mMedicoIdTest;

    private MedicosDbHelper mMedicosDBHelper;

    public PacienteDetailFragment(){

    }

    public static PacienteDetailFragment newInstance(String pacienteId, String medicoId) {
        PacienteDetailFragment fragment = new PacienteDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PACIENTE_ID,pacienteId);
        mMedicoIdTest = medicoId;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mPacienteId = getArguments().getString(ARG_PACIENTE_ID);

        }

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_paciente_detail, container, false);

        mCollapsingView = (CollapsingToolbarLayout)getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView)getActivity().findViewById(R.id.iv_avatar);
        mPhoneNumber = (TextView)root.findViewById(R.id.tv_telefono);
        mDireccion = (TextView)root.findViewById(R.id.tv_direccion);

        mMedicosDBHelper = new MedicosDbHelper(getActivity());
        loadPaciente();
        return root;
    }

    private class GetPacienteByIdTask extends AsyncTask<Void,Void,Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDBHelper.getPacienteById(mPacienteId);
        }
        @Override
        protected void onPostExecute(Cursor c){
            if(c!= null && c.moveToLast()){
                showPaciente(new Pacientes(c));
            }else{
                showLoadError();
            }
        }
    }

    private void loadPaciente(){
        new GetPacienteByIdTask().execute();
    }

    private void showLoadError(){
        Toast.makeText(getActivity(),"Error al cargar información del Paciente",Toast.LENGTH_LONG).show();
    }

    private void showPaciente(Pacientes pacientes){
        mCollapsingView.setTitle(pacientes.getName());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/"+pacientes.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(pacientes.getPhoneNumber());
        mDireccion.setText(pacientes.getDireccion());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PacientesFragment.REQUEST_UPDATE_DELETE_PACIENTE){
            if(resultCode == Activity.RESULT_OK){
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_edit_paciente:
                showEditScreen();
                break;
            case R.id.action_delete_paciente:
                new DeletePacienteTask().execute();
                break;
        }
        return  super.onOptionsItemSelected(item);
    }


    private void showDeleteError(){
        Toast.makeText(getActivity(),"Error al eliminar Paciente", Toast.LENGTH_SHORT).show();
    }

    private void showPacienteScreen(boolean requery){
        if(!requery){
            showDeleteError();
        }
        getActivity().setResult(requery? Activity.RESULT_OK:Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private class DeletePacienteTask extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            return mMedicosDBHelper.deletePaciente(mPacienteId);
        }
        @Override
        protected void onPostExecute(Integer integer){
            showPacienteScreen(integer>0);
        }
    }

    private void showEditScreen(){
        Intent intent = new Intent(getActivity(),AddEditPacienteActivity.class);
        intent.putExtra(MainActivity.EXTRA_PACIENTE_ID,mPacienteId);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,mMedicoIdTest);
        startActivityForResult(intent, PacientesFragment.REQUEST_UPDATE_DELETE_PACIENTE);
    }

}
