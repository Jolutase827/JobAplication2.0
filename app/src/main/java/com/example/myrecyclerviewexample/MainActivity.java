package com.example.myrecyclerviewexample;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myrecyclerviewexample.base.BaseActivity;
import com.example.myrecyclerviewexample.base.CallInterface;
import com.example.myrecyclerviewexample.model.Model;
import com.example.myrecyclerviewexample.model.MysqlDB;
import com.example.myrecyclerviewexample.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, CallInterface {
    private FloatingActionButton anyadirUsuario;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anyadirUsuario = findViewById(R.id.anyadirUsuario);
        recyclerView = findViewById(R.id.recycler);

        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this);
        myRecyclerViewAdapter.setOnClickListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);






        LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLinearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);




        @SuppressLint("NotifyDataSetChanged") ActivityResultLauncher<Intent> resultLauncherAddUser = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), rsult->{
            if (rsult.getResultCode()==RESULT_OK){
                Intent data = rsult.getData();
                Usuario usuario = (Usuario) data.getExtras().getSerializable("usuario");
                Model.getInstance().addUser(usuario);
                myRecyclerViewAdapter.setUsuarios(Model.getInstance().getUsuarios());
                myRecyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Nuevo: " + usuario.getNombre() , Toast.LENGTH_LONG).show();
            }else if (rsult.getResultCode()==RESULT_CANCELED){
                Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_LONG).show();
            }
        });

        anyadirUsuario.setOnClickListener(v-> {
            Intent i = new Intent(this, Formulario.class);
            resultLauncherAddUser.launch(i);
        });

        showProgress();
        executeCall(this);
    }

    @Override
    public void onClick(View view) {
        Usuario u = Model.getInstance().getUsuarios().get(recyclerView.getChildAdapterPosition(view));
        Toast.makeText(this,"Clic en " + u.getOficio(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doInBackground() {

        Model.getInstance().getUsuarios();
        Model.getInstance().getOficios();

    }

    @Override
    public void doInUI() {
        hideProgress();
        List<Usuario> usuarioList = Model.getInstance().getUsuarios();
        myRecyclerViewAdapter.setUsuarios(usuarioList);
    }
}