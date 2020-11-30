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

*MUDANÇAS EM RELAÇÃO AP1
*Implementação do banco de dados, que infelizmente nao foi possivel realizar o import do banco para a lista no menu Histórico. Se possível gostaria de saber como fazer isso na correcao, ja que nao achei resposta.
*O relógio agora é com base em uma API
*Internacionalizacao das Strings e mensagens de texto
*Botao de bater ponto inicia desabilitado durante 5 segundos, para dar tempo de ter alguma resposta da API antes de um ponto ser realizado.

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
            Toast.makeText(getApplicationContext(), R.string.id_missing, Toast.LENGTH_LONG).show();
            etMatricula.requestFocus();
            etMatricula.setBackgroundColor(Color.MAGENTA);

            return;
        } else {
            TextView textView = findViewById(R.id.etMatricula);
            String data = textView.getText().toString();
            Intent i = new Intent(MainActivity.this, TelaLogado.class);
            i.putExtra("matricula", data);
            startActivity(i);

        }
    }
}