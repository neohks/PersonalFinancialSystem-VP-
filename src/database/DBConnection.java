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
		 
        String url = "jdbc:mysql://localhost:3306/financedb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        
        
        //String url = "jdbc:mysql://localhost:3306/financedb";
        
        try {
            Class.forName ("com.mysql.cj.jdbc.Driver");                          
            System.out.println("*****Driver is ready!");
        } catch (Exception e) {
            System.out.println("*****Failed to load JDBC/ODBC driver.");
            return;               
        }

        try {                                                                    
        	conn = DriverManager.getConnection(url, username, password );
            conn.setAutoCommit(false);
            statement  = conn.createStatement();  
            
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

}
