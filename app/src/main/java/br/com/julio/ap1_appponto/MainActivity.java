package br.com.julio.ap1_appponto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
/*
O presente aplicativo tem como função marcar presença do funcionário com base no horário e na sua localização do GPS.
Possíveis atualizações seriam:
*Validação do funcionário com algum servidor,
*Validação do local onde foi feito o ponto em relação com a distância do local de trabalho,
*Controle de quantidade de pontos por dia (limite de 4, sendo chegada, almoço, volta e saída),
*Ponto salvo em um arquivo, possívelmente encriptado para evitar alterações
*Envio automático de ponto para algum email (RH?) ou servidor SQL
*Salvar a matrícula do funcionário no primeiro uso, para não ter que fazer o login toda vez
*Embelezamento do front.
 */
public class MainActivity extends AppCompatActivity {

    private EditText etMatricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMatricula = findViewById(R.id.etMatricula);

    }
    public void logar(View v){
        if (etMatricula.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Você precisa preencher o campo Matrícula.", Toast.LENGTH_LONG).show();
            etMatricula.requestFocus();
            etMatricula.setBackgroundColor(Color.MAGENTA);

            return;
        } else {
            TextView textView = findViewById(R.id.etMatricula);
            String data = textView.getText().toString();
            Intent i = new Intent(MainActivity.this, TelaLogado.class);
            i.putExtra("matricula", data);
            startActivity(i);

//            Intent intent = new Intent(this,TelaLogado.class);
//            startActivity(intent);

        }
    }
}