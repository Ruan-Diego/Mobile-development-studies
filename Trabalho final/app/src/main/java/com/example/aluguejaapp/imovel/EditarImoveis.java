package com.example.aluguejaapp.imovel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluguejaapp.R;
import com.example.aluguejaapp.transactions.Constants;
import com.example.aluguejaapp.model.Imoveis;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarImoveis extends AppCompatActivity{
    DatabaseReference mDatabase;
    Firebase meuFirebase;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    EditText edtRua;
    EditText edtNumero;
    EditText edtBairro;
    EditText edtCidade;
    EditText edtUf;
    EditText edtMensalidade;
    EditText edtQuartos;
    EditText edtBanheiros;
    EditText edtContato;
    Button btnConfirma;
    boolean edit;
    Imoveis imovel = new Imoveis();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_imoveis);



        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Firebase.setAndroidContext(this);

        btnConfirma = findViewById(R.id.btnConfirm);
        edtRua = findViewById(R.id.editTextRua);
        edtNumero = findViewById(R.id.editTextNumero);
        edtBairro = findViewById(R.id.editTextBairro);
        edtCidade = findViewById(R.id.editTextCidade);
        edtUf = findViewById(R.id.editTextUf);
        edtMensalidade = findViewById(R.id.editTextMensalidade);
        edtQuartos = findViewById(R.id.editTextQuartos);
        edtBanheiros = findViewById(R.id.editTextBanheiros);
        edtContato = findViewById(R.id.editTextContato);

        meuFirebase = new Firebase("https://alugueja-app-d1519-default-rtdb.firebaseio.com/");

        edit = false;

        if(getIntent().getExtras() != null){

            id = (String)getIntent().getExtras().get("id");
            String rua = (String)getIntent().getExtras().get("rua");
            String numero = (String)getIntent().getExtras().get("numero");
            String bairro = (String)getIntent().getExtras().get("bairro");
            String cidade = (String)getIntent().getExtras().get("cidade");
            String uf = (String)getIntent().getExtras().get("uf");
            String mensalidade = (String)getIntent().getExtras().get("mensalidade");
            String quartos = (String)getIntent().getExtras().get("quartos");
            String banheiros = (String)getIntent().getExtras().get("banheiros");
            String contato = (String)getIntent().getExtras().get("contato");

            edtRua.setText(rua);
            edtNumero.setText(numero);
            edtBairro.setText(bairro);
            edtCidade.setText(cidade);
            edtUf.setText(uf);
            edtMensalidade.setText(mensalidade);
            edtQuartos.setText(quartos);
            edtBanheiros.setText(banheiros);
            edtContato.setText(contato);

            edit = true;
        }
        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imovel.setId(id);
                imovel.setRua(edtRua.getText().toString());
                imovel.setNumero(edtNumero.getText().toString());
                imovel.setBairro(edtBairro.getText().toString());
                imovel.setCidade(edtCidade.getText().toString());
                imovel.setUf(edtUf.getText().toString());
                imovel.setMensalidade(edtMensalidade.getText().toString());
                imovel.setQuartos(edtQuartos.getText().toString());
                imovel.setBanheiros(edtBanheiros.getText().toString());
                imovel.setContato(edtContato.getText().toString());
                imovel.setEmail(mAuth.getCurrentUser().getEmail());
                imovel.setInteresse(0);

                if (TextUtils.isEmpty(imovel.getRua()) || TextUtils.isEmpty(imovel.getNumero()) || TextUtils.isEmpty(imovel.getBairro()) ||
                        TextUtils.isEmpty(imovel.getCidade()) || TextUtils.isEmpty(imovel.getUf()) || TextUtils.isEmpty(imovel.getMensalidade()) ||
                        TextUtils.isEmpty(imovel.getQuartos()) || TextUtils.isEmpty(imovel.getBanheiros())) {
                    Toast.makeText(EditarImoveis.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Firebase no01 = meuFirebase.child("Imoveis").child(id);
                    no01.setValue(imovel);
                    voltar();
                }
            }
        });
    }

    public void voltar(){
        Intent intent = new Intent(this, MeusImoveis.class);
        startActivity(intent);
    }


    public void cancelarBtn(View view){
        setResult(Constants.RESULT_CANCEL_IMOVEL);
        finish();
    }
}
