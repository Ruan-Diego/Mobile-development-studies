package com.example.aluguejaapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluguejaapp.R;
import com.example.aluguejaapp.TelaInicial;
import com.example.aluguejaapp.imovel.DetailImoveis;
import com.example.aluguejaapp.model.Imoveis;
import com.example.aluguejaapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class PerfilUser extends AppCompatActivity {
    private FirebaseAuth mAuth;


    DatabaseReference databaseReference;
    private TextView nomePerfil, emailPerfil, cpfPerfil, cidadePerfil, ufPerfil;
    private String email;
    private Map<String, String> userMap;
    private final String TAG = this.getClass().getName().toUpperCase();
    private static final String USUARIO = "Usuario";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = rootRef.child(USUARIO);
        Log.v("USERID", userReference.getKey());

        nomePerfil = findViewById(R.id.txtNomePerfil);
        emailPerfil = findViewById(R.id.txtEmailPerfil);
        cpfPerfil = findViewById(R.id.txtCpfPerfil);
        cidadePerfil = findViewById(R.id.txtCidadePerfil);
        ufPerfil = findViewById(R.id.txtUfPerfil);

        userReference.addValueEventListener(new ValueEventListener() {
            String fNome, fEmail, fCpf, fCidade, fUf;
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()){
                    if(keyId.child("email").getValue().equals(email)) {
                        fNome = keyId.child("nome").getValue(String.class);
                        fEmail = keyId.child("email").getValue(String.class);
                        fCpf = keyId.child("cpf").getValue(String.class);
                        fCidade = keyId.child("cidade").getValue(String.class);
                        fUf = keyId.child("uf").getValue(String.class);
                        break;
                    }
                }
                nomePerfil.setText(fNome);
                emailPerfil.setText(fEmail);
                cpfPerfil.setText(fCpf);
                cidadePerfil.setText(fCidade);
                ufPerfil.setText(fUf);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }
}