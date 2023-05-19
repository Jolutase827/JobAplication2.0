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
import android.view.Menu;
import android.view.MenuItem;
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

                    @SuppressLint("ShowToast")
                    @Override
                    public void doInUI() {
                        hideProgress();
                        myRecyclerViewAdapter.notifyItemRemoved(position);
                    }
                });
                Snackbar.make(recyclerView, "Deleted " + u.getNombre(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showProgress();
                                executeCall(new CallInterface() {
                                    @Override
                                    public void doInBackground() {
                                        Model.getInstance().undoDelete(u);
                                    }

                                    @Override
                                    public void doInUI() {
                                        hideProgress();
                                        myRecyclerViewAdapter.notifyItemInserted(position);
                                    }
                                });

                            }
                        })
                        .show();



            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);


       ActivityResultLauncher<Intent> resultLauncherAddUser = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), rsult->{
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



    public ActivityResultLauncher<Intent> resultLauncherUpdateUser = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), rsult->{
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit: finish();
            case R.id.settings:
                Intent i = new Intent(this, PreferenceActivity.class);
                startActivity(i);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}