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
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.entity.mime.Header;

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

//    private final String BASE_URL = "http://worldtimeapi.org/api/ip/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_logado);

        btPonto = (Button) findViewById(R.id.btPonto);
        btHistorico = (Button) findViewById(R.id.btHistorico);
        final Switch swAcidental = (Switch) findViewById(R.id.swAcidental);
        String data = getIntent().getExtras().getString("matricula", "MATRICULA");

        TextView textView = findViewById(R.id.tvNMatricula);
        textView.setText(data);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

        //cria BD
        criarConexao();

            }

        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);



        swAcidental.setChecked(false);
        btPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!swAcidental.isChecked()){
//                    letsDoSomeNetworking(BASE_URL);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy - HH:mm:ss", Locale.getDefault());
                    String tempoAtual = sdf.format(new Date());

                    dados = tempoAtual + " Latitude: " + latitude + " Longitude: " + longitude;

                    lista.add(dados);

                    Toast.makeText(getApplicationContext(), getString(R.string.check_in_done) + dados, Toast.LENGTH_LONG).show();

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

            Toast.makeText(this, R.string.db_conected,Toast.LENGTH_LONG).show();

        }
        catch (SQLException e) {

            Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();

        }

    }
}

