/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static boolean check;
    private static String[] document = new String[30];
    
    
    
    
    //Sozdanie Data base
    //public static final String QUERY_1 = "CREATE TABLE Authors (author_id INTEGER PRIMARY KEY NOT NULL, author VARCHAR(64) NOT NULL, notes VARCHAR(255));";
    //public static final String QUERY_2 = "CREATE TABLE Documents (document_id INTEGER PRIMARY KEY, title VARCHAR(64) NOT NULL, text VARCHAR(1024), date DATE NOT NULL, doc_author INTEGER,CONSTRAINT fkey FOREIGN KEY (doc_author) REFERENCES Authors(author_id));";

    //Zapolnenie DB
    static LocalDate date = LocalDate.now();
    
  
    public static final String QUERY_A = "INSERT INTO Authors VALUES(?, ?, ?)";//dlia fillTable parametriz zapros
    public static final String QUERY_D = "INSERT INTO Documents VALUES (?, ?, ?, " + "'" + date + "', " + "?);";

    //public static final String QUERY_1 = "SELECT title FROM Documents JOIN Authors ON Author_id = Doc_author WHERE author = 'Tom Hawkins'";
    //public static final String QUERY_1 = "UPDATE Authors SET notes = 'No data' WHERE notes = '';";// WHERE notes IS NULL;
    //public static final String QUERY_1 = "SELECT document_id, title, author FROM Documents JOIN Authors ON Author_id = Doc_author WHERE title LIKE '%report%';";
    //public static final String QUERY_1 = "SELECT * FROM Documents";
    public static final String QUERY_1 = "SELECT * FROM Authors";
    //public static final String QUERY_2 = "DELETE FROM Documents";
    
    public static void main(String[] args) {
        String[] queries = {QUERY_1}; //, QUERY_2

        //Zagryzka draivera
        //DataBase data = new DataBase();
        DbServer serv = new DbServer();
       //data.startBase();
       //data.setStatement();
       //data.executeQuery(queries);

        //data.fillTable(1, "Arnon Grey", "");
        //data.fillTable(2, "Tom Hawkins", "new author");
        //data.fillTable(3, "Jim Beam", "");
        //data.fillTable(1, "Project plan", "First content", 1);
        //data.fillTable(2, "First report", "Second content", 2);
        //data.fillTable(3, "Test result", "Third content", 2);
        //data.fillTable(4, "Second report", "Report content", 3);
       
        
        Author author2 = new Author(9, "Sloo", "hello");
        Document document1 = new Document (1, "title", "text", date, 1);//doc_id title text date author_id
        
        try {
            
            System.out.println(serv.addAuthor(author2));
            System.out.println(serv.addDocument(document1, author2));
        } catch (DocumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        //data.stopBase();

//1 STEP *** PROVERKA NALICHIYA DRAIVERA I VOZMOJNOSTI YASANOVKI SVIAZY S NYJNOY NAM DB**********
        //data.checkDriver();
//2 STEP *** YSTANOVKA SOEDINENIYA S DB***************************************************************
        //data.setConnection("test", "test");
//3 STEP *** SOZDATb ZAPROS (Statement st) k DB***************************************************
        //data.setStatement();
        //data.fillTable(10);
//4 STEP *** VbIPOLNITb ZAPROS k DB***************************************************
//5 STEP *** OBRABOTKA ZAPROS k DB*************************************************** v executeQuery
        //data.executeQuery(new String[] {/*QUERY_1, ydaliaem chtobbI fillTable mog zapolnitb DB*/ QUERY_2});//Suda peredaem nash zapros (Query, objavlena v classe)
//6 STEP *** ZAKRYTIE SOEDINENIYA S DB***************************************************************
        //data.closeConnection();
    }//main end

      
}
