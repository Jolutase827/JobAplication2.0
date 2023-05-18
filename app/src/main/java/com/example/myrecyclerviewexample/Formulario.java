package com.example.myrecyclerviewexample;

import android.graphics.BitmapFactory;
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
import com.example.myrecyclerviewexample.model.Imagen;
import com.example.myrecyclerviewexample.model.Model;
import com.example.myrecyclerviewexample.model.Oficio;
import com.example.myrecyclerviewexample.model.Usuario;

import java.nio.charset.StandardCharsets;

public class Formulario extends BaseActivity {


    public static enum Modo {
        ACTUALIZAR,ANYADIR
    }

    private ImageButton image;
    private Usuario u;
    private EditText nombre,apellido;
    private Spinner spinner;
    private Button aceptar, cancelar,actualizar;
    private Imagen imagen;

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
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        imagen = Model.getInstance().getImagen(oficio.getIdOficio());
                    }

                    @Override
                    public void doInUI() {
                        byte[] bytes = imagen.getImage().getBytes(StandardCharsets.ISO_8859_1);
                        image.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                    }
                });
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
                        u = new Usuario(null, nombre.getText().toString(), apellido.getText().toString(), adapterOficio.getItem(spinner.getSelectedItemPosition()).getIdOficio());
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

        cancelar.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        });

    }
}