package com.example.myrecyclerviewexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecyclerviewexample.API.CallMethods;
import com.example.myrecyclerviewexample.base.BaseActivity;
import com.example.myrecyclerviewexample.base.CallInterface;
import com.example.myrecyclerviewexample.base.ImageDownloader;
import com.example.myrecyclerviewexample.model.Imagen;
import com.example.myrecyclerviewexample.model.Model;
import com.example.myrecyclerviewexample.model.MyPreferenceManager;
import com.example.myrecyclerviewexample.model.Oficio;
import com.example.myrecyclerviewexample.model.Usuario;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<Usuario> list;
    private final LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private Imagen imagen;

    public MyRecyclerViewAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = new ArrayList<>();
    }

    private void executeCall(CallInterface callInterface){
        executor.execute(() -> {
            callInterface.doInBackground();
            handler.post(() -> {
                callInterface.doInUI();
            });
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.simple_element,parent,false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        List<Oficio> oficios = Model.getInstance().getOficios();

        Usuario u = list.get(position);
        holder.title.setText(u.getApellidos().concat(", ").concat(u.getNombre()));
        holder.subtitle.setText(
                oficios.stream()
                        .filter(o->o.getIdOficio()==u.getIdOficio())
                        .findFirst()
                        .get()
                        .getDescripcion()
        );

        executeCall(new CallInterface() {
            @Override
            public void doInBackground() {
                if (MyPreferenceManager.getInstance(null).getImageOption()) {
                    imagen = Model.getInstance().getImagen(u.getIdOficio());
                }

            }

            @Override
            public void doInUI() {
                if (MyPreferenceManager.getInstance(null).getImageOption()) {
                    byte[] bytes = imagen.getImage().getBytes(StandardCharsets.ISO_8859_1);
                    holder.image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                }else {
                    ImageDownloader.downloadImage(MyPreferenceManager.getInstance(null).getPathImagenes() + Model.getInstance().getOficio(u.getIdOficio()).getUrlimagen(), holder.image);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setUsuarios(List<Usuario> usuarioList) {
        this.list = usuarioList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView subtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
        }

    }
}
