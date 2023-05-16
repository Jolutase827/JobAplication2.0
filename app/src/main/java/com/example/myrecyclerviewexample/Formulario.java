package com.example.myrecyclerviewexample;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myrecyclerviewexample.base.BaseActivity;
import com.example.myrecyclerviewexample.base.CallInterface;
import com.example.myrecyclerviewexample.model.Model;
import com.example.myrecyclerviewexample.model.Oficio;
import com.example.myrecyclerviewexample.model.Usuario;

public class Formulario extends BaseActivity {


    public static enum Modo {
        ACTUALIZAR,ANYADIR
    }

    private ImageButton image;
    private Usuario u;
    private EditText nombre,apellido;
    private Spinner spinner;
    private Button aceptar, cancelar,actualizar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        spinner = findViewById(R.id.spinner);
        Modo m = (Modo) getIntent().getExtras().getSerializable("modo");
        aceptar = findViewById(R.id.buttonAceptar);
        cancelar = findViewById(R.id.botonCancelar);
        actualizar = findViewById(R.id.actualizar);
        image = findViewById(R.id.imagen);


        if (m.equals(Modo.ACTUALIZAR)){
            actualizar.setVisibility(View.VISIBLE);
            aceptar.setVisibility(View.GONE);
            u = (Usuario) getIntent().getExtras().getSerializable("usuario");
        }else {
            actualizar.setVisibility(View.GONE);
            aceptar.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<Oficio> adapterOficio = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Model.getInstance().getOficios());

        spinner.setAdapter(adapterOficio);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Oficio oficio = (Oficio) adapterOficio.getItem(i);
                switch (oficio.getIdOficio()) {
                    case 1:
                        image.setImageResource(R.mipmap.ic_1_foreground);
                        break;
                    case 2:
                        image.setImageResource(R.mipmap.ic_2_foreground);
                        break;
                    case 3:
                        image.setImageResource(R.mipmap.ic_3_foreground);
                        break;
                    case 4:
                        image.setImageResource(R.mipmap.ic_4_foreground);
                        break;
                    case 5:
                        image.setImageResource(R.mipmap.ic_5_foreground);
                        break;
                    case 6:
                        image.setImageResource(R.mipmap.ic_6_foreground);
                        break;
                    case 7:
                        image.setImageResource(R.mipmap.ic_7_foreground);
                        break;
                    case 8:
                        image.setImageResource(R.mipmap.ic_8_foreground);
                        break;
                    case 9:
                        image.setImageResource(R.mipmap.ic_9_foreground);
                        break;
                    case 10:
                        image.setImageResource(R.mipmap.ic_10_foreground);
                        break;
                    case 11:
                        image.setImageResource(R.mipmap.ic_11_foreground);
                        break;
                    case 12:
                        image.setImageResource(R.mipmap.ic_12_foreground);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        aceptar.setOnClickListener(v->{
            if (nombre.getText().length()>0||apellido.getText().length()>0) {
                showProgress();
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        u = new Usuario(0, nombre.getText().toString(), apellido.getText().toString(), adapterOficio.getItem(spinner.getSelectedItemPosition()).getIdOficio());
                        Model.getInstance().insertUser(u);
                    }

                    @Override
                    public void doInUI() {
                        hideProgress();
                        Intent intent = new Intent();
                        Usuario usuario = new Usuario(u.getIdUsuario(), nombre.getText().toString(), apellido.getText().toString(), adapterOficio.getItem(spinner.getSelectedItemPosition()).getIdOficio());
                        intent.putExtra("usuario", usuario);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });

            }else if (nombre.getText().length()==0){
                Toast.makeText(this, "Introduce nombre de usuario", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Introduce el apellido", Toast.LENGTH_LONG).show();
            }
        });


        actualizar.setOnClickListener(v->{
            if (nombre.getText().length()>0||apellido.getText().length()>0) {
                showProgress();
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        Usuario usuario = new Usuario(u.getIdUsuario(), nombre.getText().toString(), apellido.getText().toString(), adapterOficio.getItem(spinner.getSelectedItemPosition()).getIdOficio());
                        Model.getInstance().updateUser(usuario);
                    }

                    @Override
                    public void doInUI() {
                        hideProgress();
                        Intent intent = new Intent();
                        Usuario usuario = new Usuario(0, nombre.getText().toString(), apellido.getText().toString(), adapterOficio.getItem(spinner.getSelectedItemPosition()).getIdOficio());
                        intent.putExtra("usuario", usuario);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });

            }else if (nombre.getText().length()==0){
                Toast.makeText(this, "Introduce nombre de usuario", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Introduce el apellido", Toast.LENGTH_LONG).show();
            }
        });

        cancelar.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        });

    }
}