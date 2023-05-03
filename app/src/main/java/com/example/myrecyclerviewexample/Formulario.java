package com.example.myrecyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myrecyclerviewexample.base.BaseActivity;
import com.example.myrecyclerviewexample.base.CallInterface;
import com.example.myrecyclerviewexample.model.Model;
import com.example.myrecyclerviewexample.model.Oficio;
import com.example.myrecyclerviewexample.model.Usuario;

public class Formulario extends AppCompatActivity {


    private EditText nombre,apellido;
    private Spinner spinner;
    private Button aceptar, cancelar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        spinner = findViewById(R.id.spinner);
        aceptar = findViewById(R.id.buttonAceptar);
        cancelar = findViewById(R.id.botonCancelar);

        ArrayAdapter<Oficio> adapterOficio = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Model.getInstance().getOficios());

        spinner.setAdapter(adapterOficio);

        aceptar.setOnClickListener(v->{
            Intent intent = new Intent();
            Usuario usuario = new Usuario(0,nombre.getText().toString(),apellido.getText().toString(), adapterOficio.getItem(spinner.getSelectedItemPosition()).getIdOficio());
            intent.putExtra("usuario",usuario);
            setResult(RESULT_OK,intent);
            finish();
        });



        cancelar.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        });

    }
}