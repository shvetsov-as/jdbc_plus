/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.Enumeration;

/**
 *
 * @author User
 */
public class Connector {
    private static final String URL = "jdbc:hsqldb:file:dbFolder/data";
    private static final String LOGIN = "test";
    private static final String PWD = "test";
    private static Connection con;
    
    public void checkDriver() {

        Enumeration <Driver> e = DriverManager.getDrivers();
        while(e.hasMoreElements()){
            Driver d = e.nextElement();
            System.out.println(d.getClass().getCanonicalName());
        }
        try {
            Driver d = DriverManager.getDriver(URL);//polychaem ssilky tipa Driver (proverka chto Driver estb)
        if(d != null){
            System.out.println("#1 Takoy driver estb");
        }
        } catch (SQLException ex) {
            System.out.println("Error v checkDriver() " + ex.getMessage());
        } 
    }//check end
    
//Ystanovka soedineniya
    public void setConnection (){
      try {
          con = DriverManager.getConnection(URL, LOGIN, PWD);
          System.out.println("#2 setConnection(IS OK)");
      } catch (SQLException ex) {
          System.out.println("Error v setConnection() " + ex.getMessage());
      }
    }//setConnection end

//Zakrytie soedineniya
    public void closeConnection(){
      try {
          if(con != null && !con.isClosed()){ //esli podkluchenie ne null i ne zakrito to...
              con.close();//...zakrivaem podkluchenie
              System.out.println("#6 closeConnection(CLOSE IS OK)");
          }
      } catch (SQLException ex) {
           System.out.println("Error v closeConnection() " + ex.getMessage());
      }
    }//closeConnection end

    public Connection getCon() {
        return con;
    }
    
}
