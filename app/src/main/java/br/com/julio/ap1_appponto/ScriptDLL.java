package br.com.julio.ap1_appponto;

public class ScriptDLL {
    public static String getCreateTableClient(){
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE frequencia (");
        sql.append("        ID          INTEGER    PRIMARY KEY");
        sql.append("        NOT NULL,");
        sql.append("        dataHora TEXT (75)");
        sql.append("        NOT NULL,");
        sql.append("        local     TEXT (75)");
        sql.append("        NOT NULL,");
        sql.append("        matricula     TEXT (15)");
        sql.append("        NOT NULL");
        sql.append(");");

        return sql.toString();
    }

    public static String getFrequencia(){
        StringBuilder sql = new StringBuilder();
        sql.append("Select matricula, dataHora, local");
        sql.append(" from frequencia");
        return sql.toString();
    }

    public static String getChecklist(){
        StringBuilder sql = new StringBuilder();
        sql.append("Select matricula, dataHora, local");
        sql.append(" from frequencia");
        sql.append(" where ID = ?");
        return sql.toString();
    }
}
