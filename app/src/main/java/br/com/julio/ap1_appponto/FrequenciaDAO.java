package br.com.julio.ap1_appponto;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class FrequenciaDAO {

    SQLiteDatabase conexao;

    public FrequenciaDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;

    }

    public void insert(Frequencia frequencia){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Matricula", frequencia.getMatricula());
        contentValues.put("Latitude", frequencia.getLatitude());
        contentValues.put("Longitude", frequencia.getLongitude());
        contentValues.put("Data e hora", frequencia.getTimeStamp());
        conexao.insertOrThrow("frequencia",null, contentValues);

    }
    public void remove(int id){

    }
    public void alter(Frequencia freq){

    }
    public List<Frequencia> listFrequencia(){

        return null;

    }
    public Frequencia getFrequencia(int id){

        return null;
    }

}
