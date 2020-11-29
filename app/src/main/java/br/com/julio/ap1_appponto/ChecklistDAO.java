package br.com.julio.ap1_appponto;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class ChecklistDAO {

    SQLiteDatabase conexao;

    public ChecklistDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;

    }

    public void insert(Checklist chk){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Matricula", chk.getMatricula());
        contentValues.put("Latitude",chk.getLatitude());
        contentValues.put("Longitude", chk.getLongitude());
        contentValues.put("Data e hora", chk.getTimeStamp());
        conexao.insertOrThrow("CHecklist",null, contentValues);

    }
    public void remove(int id){

    }
    public void alter(Checklist chk){

    }
    public List<Checklist> listChecklists(){

        return null;

    }
    public Checklist getChecklist(int id){

        return null;
    }

}
