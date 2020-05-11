/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DBConnection {
	
	private static Connection conn;
	private static Statement statement; 
	private static ResultSet rs; 
	
	public DBConnection () {}
	
	public DBConnection (String username, String password) {
            //Connection string
            String url = "jdbc:derby://localhost:1527/personalFinanceDB";

            try {
                Class.forName ("org.apache.derby.jdbc.EmbeddedDriver");
                
                System.out.println("*****Driver is ready!");
            } catch (Exception e) {
                System.out.println("*****Failed to load JDBC/ODBC driver.");
                return;               
            }

            try {
                conn = DriverManager.getConnection(url, username, password );
                conn.setAutoCommit(false);
                statement  = conn.createStatement();  
                System.out.println(statement);
                   
                System.out.println(conn);
                
            } catch (SQLException exception ) {
                System.out.println ("\n*** SQLException caught ***\n");

                while (exception != null){                                                                     
                    System.out.println ("SQLState:   " + exception.getSQLState()  );
                    System.out.println ("Message:    " + exception.getMessage()   );
                    System.out.println ("Error code: " + exception.getErrorCode() );
                    JOptionPane.showMessageDialog(new JFrame(), exception.getMessage() + "\n Please check your connection or contact to server admin!");
                    exception = exception.getNextException ();
                    System.out.println ("");
                }
            } catch (java.lang.Exception exception) {                                  
                exception.printStackTrace();                                      
            }
        }
        //Get connection
        static Connection getConnection() {
            return conn;
        }
        //Get Statement
        static Statement getStmt() {
            return statement;
        }
        //Get Result set
        static ResultSet getRS() {
            return rs;
	}
	//Close connection if close the program
	public static void close() {
            try {

                //Need to commit in order to close connection
                //Refer: https://stackoverflow.com/questions/36666686/cannot-close-a-connection-while-a-transaction-is-still-alive-exception-on-connec
                conn.commit();
                
                if (rs != null) {
                    rs.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        
        /*public void IUquery(String username, String pw, String query){
           //thinking to make all the queries to functions to be more neat 
        }*/
}
