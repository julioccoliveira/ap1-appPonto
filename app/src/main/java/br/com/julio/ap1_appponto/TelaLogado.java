package br.com.julio.ap1_appponto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class TelaLogado extends AppCompatActivity {

    private Button btPonto, btHistorico;
    private Switch swAcidental;
    double latitude, longitude = 0;
    String dados;
    private ArrayList<String> lista = new ArrayList();
    private LocationManager locationManager;
    private LocationListener locationListener;

    private SQLiteDatabase conexao;
    private DatabaseHelper databaseHelper;
    private FrequenciaDAO frequenciaDAO;
    private String tempoAtual;

    private final String BASE_URL = "http://worldtimeapi.org/api/timezone/America/Fortaleza";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_logado);

        btPonto = (Button) findViewById(R.id.btPonto);
        btHistorico = (Button) findViewById(R.id.btHistorico);
        final Switch swAcidental = (Switch) findViewById(R.id.swAcidental);
        final String data = getIntent().getExtras().getString("matricula", "MATRICULA");

        TextView textView = findViewById(R.id.tvNMatricula);
        textView.setText(data);

        //NOTA: Não colocar NADA além disso nesse bloco
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                letsDoSomeNetworking(BASE_URL);

            }

        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

        //cria BD
        criarConexao();
        //Criando cheacklistDAO
        final FrequenciaDAO frequenciaDAO = new FrequenciaDAO(conexao);

        swAcidental.setChecked(false);
        btPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!swAcidental.isChecked()){

//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault());
//                    String tempoAtual = sdf.format(new Date());

                    dados = tempoAtual + " Lat: " + latitude + " Lon: " + longitude;

                    lista.add(dados);

                    Toast.makeText(getApplicationContext(), getString(R.string.check_in_done) + dados, Toast.LENGTH_LONG).show();

                    Frequencia frequencia = new Frequencia();
                    frequencia.setMatricula(data);
                    frequencia.setLongitude(String.valueOf(longitude));
                    frequencia.setLatitude(String.valueOf(latitude));
                    frequencia.setTimeStamp(tempoAtual);

                    frequenciaDAO.insert(frequencia);

                    return;
                } else {
                    Toast.makeText(getApplicationContext(), R.string.check_in_lock, Toast.LENGTH_LONG).show();
                }
            }
        });

        swAcidental.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (swAcidental.isChecked()){
                    swAcidental.setChecked(false);
                }
                return false;
            }
        });
        
                btHistorico.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        abrirViewDados(view);
                    }
                });

    }
    private void abrirViewDados(View v) {

        Intent i = new Intent(this, ListViewDados.class);
        i.putExtra("historico", lista);
        startActivity(i);
    }

    //Esse método deve ir para outra classe
    private void criarConexao(){

        try {

            databaseHelper = new DatabaseHelper(this);
            conexao = databaseHelper.getWritableDatabase();

//            Toast.makeText(this, R.string.db_conected,Toast.LENGTH_LONG).show();

        }
        catch (SQLException e) {

            Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();

        }

    }
    private void letsDoSomeNetworking(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try{

                    Log.d("TEMPO", "AAAA: " +response.getString("datetime"));
                    tempoAtual = response.getString("datetime");
                    String format[] = tempoAtual.split("T|\\.");
                    tempoAtual = format[0] + " " + format[1];

               } catch (JSONException e){

                   e.printStackTrace();

               }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("ERRO", e.toString());
            }
        });
    }


}

