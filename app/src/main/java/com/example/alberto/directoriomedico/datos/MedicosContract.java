package com.example.alberto.directoriomedico.datos;

import android.provider.BaseColumns;

public class MedicosContract {
    public static abstract class MedicoEntry implements BaseColumns {
        public static final String TABLE_NAME = "medicos";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SPECIALTY = "specialty";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO = "bio";
    }


    public static abstract class PacienteEntry implements BaseColumns {
        public static final String TABLE_NAME = "pacientes";

        public static final String ID = "id";
        public static final String ID_MEDICO = "idMedico";
        public static final String NAME = "name";
        public static final String DIRECCION = "direccion";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String AVATAR_URI = "avatarUri";
    }
}
