/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import static database.Main.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 *
 * @author User
 */
public class DataBase {

    private Statement st;
    private PreparedStatement ps_author;//dlia fillTable
    private PreparedStatement ps_document;
    private Connector connector = new Connector();
    

    public void startBase() {
        connector.checkDriver();
        connector.setConnection();
        connector.getCon();
    }

    public void stopBase() {
        connector.closeConnection();
    }

    public void setStatement() {

        try {

            st = connector.getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps_author = connector.getCon().prepareStatement(QUERY_A);//dlia fillTable
            ps_document = connector.getCon().prepareStatement(QUERY_D);
            System.out.println("#3 Statement object sozdan");
        } catch (SQLException ex) {
            System.out.println("Error v setStatement() " + ex.getMessage());
        }
    }// setStatement() end

    public void fillTable(int author_id, String author, String notes) {//Author
        try {
            ps_author.setInt(1, author_id);
            ps_author.setString(2, author);
            ps_author.setString(3, notes);
            ps_author.addBatch();

            int[] ai_auth = ps_author.executeBatch();//tk vozvrashaet massiv intov
            ps_author.clearBatch();//Ochistka batch
        } catch (SQLException ex) {
            System.out.println("Error v fillTable(ps.executeBatch()) " + ex.getMessage());
        }

    }// fillTable() end

    public void fillTable(int document_id, String title, String text, int doc_author) {//Document
        try {
            ps_document.setInt(1, document_id);
            ps_document.setString(2, title);
            ps_document.setString(3, text);
            ps_document.setInt(4, doc_author);
            ps_document.addBatch();

            int[] ai_doc = ps_document.executeBatch();//tk vozvrashaet massiv intov
            ps_document.clearBatch();//Ochistka batch
        } catch (SQLException ex) {
            System.out.println("Error v fillTable(ps.executeBatch()) " + ex.getMessage());
        }

    }// fillTable() end

    public void executeQuery(String[] query) {
        try {//TK peredaem [] zaprosov - ispolzyem FOR

            for (String q : query) { //Str q - konkretnaya stroka
                boolean b = st.execute(q);//execute(String sql) - vozvrashaet T ili F chitai nije

                if (b == true) {//esli T - mojem poluchitb ResultSet (v rs)
                    ResultSet rs = st.getResultSet();//executeQuery - esli znaem chto tip komandbI vernet ResultSet
                    System.out.println("## (v FOR) executeQuery() polychen ResultSet");
                    //obrabotka ResultSet
                    printResult(rs);

                } else {//esli F - mojem poluchitb int (v n)
                    int n = st.getUpdateCount();// kolvo izmenennnbIh zapisey //executeUpdate - esli znaem chto tip komandbI vernet int
                    System.out.println("## (v FOR) executeQuery() polychen Integer ili Void");
                    System.out.println("## (v FOR) Izmeneno (Obnovleno) zapisey: " + n);// obrabotka esli vernulsia int
                }
                System.out.println("## (v FOR) executeQuery() vbIpolnilo Statement");
            }

            System.out.println("#4 executeQuery() vbIpolnilo Statement");
        } catch (SQLException ex) {
            System.out.println("Error v executeQuery(String[] query) " + ex.getMessage());
        }
    }//executeQuery() end

    private void printResult(ResultSet rs) {// s realizaciey perebora vbIborki
        if (rs != null) {
            try {
                ResultSetMetaData md = rs.getMetaData();//yznaem kakaya y nas tablica
                int cols = md.getColumnCount();//yznaem skolbko kolonok
                for (int i = 1; i <= cols; ++i) {
                    System.out.print(md.getColumnName(i) + "\t\t");// poluchim strokovoe nazvanie kolonki
                }
                System.out.println("\n---------------------------");

                while (rs.next()) {//poka estb sledyushaya zapisb

                    //rs.next(); //rabotaem s tekyshey zapisbu rs.next, perevodim kyrsor na sledyushyu zapisb
// do ispolbzovaniya *ResultSetMetaData md* System.out.println("printResult(ResultSet rs) " + rs.getInt(1) + ". " + rs.getString("test") + ": " + rs.getString("notes"));
                    for (int i = 1; i <= cols; ++i) {
                        switch (md.getColumnType(cols)) {
                            case Types.INTEGER://esli tip bydet int to v sout mbI ispolzuem korrektnyi metod izvlecheniya znacheniya getInt
                                System.out.print(rs.getString(i) + "\t\t");//Anomaliya rs.getInt(i)!!!
                                break;

                            case Types.VARCHAR:
                                System.out.print(rs.getString(i) + "\t\t");
                                break;
                            case Types.CHAR:
                                System.out.print(rs.getString(i) + "\t\t");
                                break;
                            case Types.DATE:
                                System.out.print(rs.getDate(i) + "\t\t");
                                break;
                        }//switch end  
                    }//FOR end
                    System.out.println();
                }
            } catch (SQLException ex) {
                System.out.println("Error v printResult(ResultSet rs) " + ex.getMessage());
            }
        }
    }//printResult() end

}//class end
