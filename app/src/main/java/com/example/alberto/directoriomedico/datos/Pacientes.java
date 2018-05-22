package com.example.alberto.directoriomedico.datos;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;



public class Pacientes {
    private String id;
    private String idMedico;
    private String name;
    private String direccion;
    private String phoneNumber;
    private String avatarUri;

    public Pacientes(String idMedico, String name, String direccion, String phoneNumber, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.idMedico = idMedico;
        this.name = name;
        this.direccion = direccion;
        this.phoneNumber = phoneNumber;
        this.avatarUri = avatarUri;
    }

    public Pacientes(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex
                (MedicosContract.PacienteEntry.ID));
        idMedico = cursor.getString(cursor.getColumnIndex
                (MedicosContract.PacienteEntry.ID_MEDICO));
        name = cursor.getString(cursor.getColumnIndex
                (MedicosContract.PacienteEntry.NAME));
        direccion = cursor.getString(cursor.getColumnIndex
                (MedicosContract.PacienteEntry.DIRECCION));
        phoneNumber = cursor.getString(cursor.getColumnIndex
                (MedicosContract.PacienteEntry.PHONE_NUMBER));
        avatarUri = cursor.getString(cursor.getColumnIndex
                (MedicosContract.PacienteEntry.AVATAR_URI));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(MedicosContract.PacienteEntry.ID,id);
        values.put(MedicosContract.PacienteEntry.ID_MEDICO,idMedico);
        values.put(MedicosContract.PacienteEntry.NAME,name);
        values.put(MedicosContract.PacienteEntry.DIRECCION,direccion);
        values.put(MedicosContract.PacienteEntry.PHONE_NUMBER,phoneNumber);
        values.put(MedicosContract.PacienteEntry.AVATAR_URI,avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public String getName() {
        return name;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
}
