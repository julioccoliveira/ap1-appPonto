package br.com.julio.ap1_appponto;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewDados extends AppCompatActivity {

    private List<String> historico = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_dados);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> dados = bundle.getStringArrayList("historico");

        ListView historico = findViewById(R.id.listView);

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,dados);

        historico.setAdapter(adapter);


    }
}