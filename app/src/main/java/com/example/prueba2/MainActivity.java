package com.example.prueba2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements FragmentFilter.fragmentLisener {

    List<Posts> listadatos = new ArrayList<Posts>();
    String[] stringdatos;

    private FragmentFilter FragmentFilter;
    private ListView lv;
    private CustomAdapter customAdapter;
    private ArrayList<Model> modelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentFilter = new FragmentFilter();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, FragmentFilter)
                .commit();

        lv = (ListView) findViewById(R.id.listview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
        new SwipeToDismissTouchListener<>(new ListViewAdapter(lv),
        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListViewAdapter view, int position) {
                customAdapter.remove(position);
            }
        });

        updateposts();

        lv.setOnTouchListener(touchListener);
        lv.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                TextView tagText = (TextView) view.findViewById(R.id.tv);
                String tag = tagText.getText().toString();

                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    final AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                    dialogo.setTitle("Detalle del Post");
                    dialogo.setMessage(tag);
                    dialogo.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialogo.create();
                    dialogo.show();

                    Toast.makeText(MainActivity.this, "Post Leido!", LENGTH_SHORT).show();
                    view.findViewById(R.id.tv).setBackgroundResource(R.color.colorPrimary);
                }
            }
        });
    }

    public void updateposts()
    {
        RetrofitInterface api = RetrofitInterface.retrofit.create(RetrofitInterface.class);
        Call<List<Posts>> call = api.getPostList();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>>call, Response<List<Posts>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        listadatos.addAll(response.body());
                        stringdatos = listaDeNombres();
                        modelArrayList = populateList(stringdatos);
                        customAdapter = new CustomAdapter(MainActivity.this,modelArrayList);
                        lv.setAdapter(customAdapter);
                    } else {
                        Log.i("RespuestaVacia", "No se encontraron datos");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Posts>>call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(MainActivity.this, "FALLO AL OBTENER DATOS", LENGTH_SHORT).show();
            }

        });
    }

    public String[] listaDeNombres() {
        int cant = listadatos.size();
        String[] lsRecetaNombre = new String[cant];
        int i = 0;
        if (!listadatos.isEmpty()) {
            for (Posts r : listadatos) {
                lsRecetaNombre[i] = String.valueOf("Titulo: "+r.getTitle()+"\nCuerpo: "+r.getBody());
                i++;
            }
        }

        return lsRecetaNombre;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.deleteall:
                lv.setAdapter(null);
                break;
            case R.id.updateposts:
                lv.setAdapter(customAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Model> populateList(String[] NameList){

        ArrayList<Model> list = new ArrayList<>();

        for(int i = 0; i < NameList.length; i++){
            Model model = new Model();
            model.setName(NameList[i]);
            //model.setImage_drawable(myImageList[i]);
            list.add(model);
        }

        return list;
    }

    @Override
    public void onInputASent(CharSequence input) {
        Toast.makeText(MainActivity.this, "input: "+input, LENGTH_SHORT).show();

    }
}