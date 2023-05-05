package com.example.myrecyclerviewexample;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.myrecyclerviewexample.base.BaseActivity;
import com.example.myrecyclerviewexample.base.CallInterface;
import com.example.myrecyclerviewexample.model.Model;
import com.example.myrecyclerviewexample.model.MysqlDB;
import com.example.myrecyclerviewexample.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Usuario u = Model.getInstance().getUsuarios().get(viewHolder.getAdapterPosition());
                showProgress();
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        Model.getInstance().delecteUser(u);
                    }

                    @Override
                    public void doInUI() {
                        hideProgress();
                        myRecyclerViewAdapter.notifyItemRemoved(position);
                        Toast.makeText(viewHolder.itemView.getContext(), "Usuario "+u.getNombre()+" ha sido eliminado", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);




        @SuppressLint("NotifyDataSetChanged") ActivityResultLauncher<Intent> resultLauncherAddUser = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), rsult->{
            if (rsult.getResultCode()==RESULT_OK){
                Intent data = rsult.getData();
                Usuario usuario = (Usuario) data.getExtras().getSerializable("usuario");
                myRecyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Nuevo: " + usuario.getNombre() , Toast.LENGTH_LONG).show();
            }else if (rsult.getResultCode()==RESULT_CANCELED){
                Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_LONG).show();
            }
        });

        anyadirUsuario.setOnClickListener(v-> {
            Intent i = new Intent(this, Formulario.class);
            i.putExtra("modo",Formulario.Modo.ANYADIR);
            resultLauncherAddUser.launch(i);
        });

        showProgress();
        executeCall(this);
    }


    @SuppressLint("NotifyDataSetChanged") public ActivityResultLauncher<Intent> resultLauncherUpdateUser = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), rsult->{
        if (rsult.getResultCode()==RESULT_OK){
            Intent data = rsult.getData();
            Usuario usuario = (Usuario) data.getExtras().getSerializable("usuario");
            myRecyclerViewAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Actualizado: " + usuario.getNombre() , Toast.LENGTH_LONG).show();
        }else if (rsult.getResultCode()==RESULT_CANCELED){
            Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_LONG).show();
        }
    });

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, Formulario.class);
        i.putExtra("modo", Formulario.Modo.ACTUALIZAR);
        i.putExtra("usuario",Model.getInstance().getUsuarios().get(recyclerView.getChildAdapterPosition(view)));
        resultLauncherUpdateUser.launch(i);
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