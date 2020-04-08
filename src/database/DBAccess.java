/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.*;
import java.sql.Timestamp;

/**
 *
 * @author NKS
 */
public class DBAccess {
    
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement prepstatement;
    private static int rowAffected = 0;
    public static String currentUser;
    
    private static int executeUpdate(String insert_into_userinfouseridusernamepasswor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public DBAccess(){
        conn = DBConnection.getConnection();
        stmt = DBConnection.getStmt();
        rs = DBConnection.getRS();
        
    }
    
    public static void checkAvailableUsername(String uname, String pw, String email) throws Exception {
        ArrayList<String> usernameLists = new ArrayList<String>();
        String uid = "U0000";
        boolean isAvailable = true;
        try{
            rs = stmt.executeQuery("select * from root.userinfo");
            
            while(rs.next()){
                String name = rs.getString("username");
                uid = rs.getString("userid");
                usernameLists.add(name);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        for(int i=0; i<usernameLists.size();i++){
            if(uname.equals(usernameLists.get(i))){
                isAvailable=false;
                System.out.println("Username is used");
                break;
            }
        }
        if(isAvailable){
            registerUser(uid,uname,pw,email);
        }
        
    }
    
    public static void registerUser(String uid, String uname, String pw, String email) throws Exception{
        boolean isadmin = false;
        int userid = Integer.parseInt(uid.substring(1,5));

        userid++;
        String strID= Integer.toString(userid);
        int lengthID = strID.length();
        int requiredZeros = 4-lengthID;
        
        String zeropads="";
        for(int i=0 ; i<requiredZeros ; i++){
            zeropads += "0";
        }
        
        strID = zeropads + strID;
        strID = "U" + strID;
        
        try{
            String query = "INSERT INTO root.userinfo(USERID, USERNAME, PASSWORD, EMAIL, ISADMIN) VALUES (?,?,?,?,?)";
            prepstatement = conn.prepareStatement(query);
            prepstatement.setString(1, strID);
            prepstatement.setString(2, uname);
            prepstatement.setString(3, pw);
            prepstatement.setString(4, email);
            prepstatement.setBoolean(5, isadmin);
            
            rowAffected = prepstatement.executeUpdate();
            System.out.println("Row inserted: " + rowAffected);
            
            conn.commit();
            prepstatement.close();
            
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static boolean login(String uname, String pw){
        String username, password;
        boolean loginSuccess = false;
        
        try{
            rs = stmt.executeQuery("select username, password from root.userinfo");
            
            while(rs.next()){
                username = rs.getString("username");
                password = rs.getString("password");
                
                if(username.equals(uname) && password.equals(pw)){
                    System.out.println("logged in");
                    currentUser = uname;
                    return true;
                }
            }
            if(!loginSuccess){
                System.out.println("invalid login");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    
    public static String getUserID(String username){
        String userid="U0001";
        try{
            rs = stmt.executeQuery("SELECT USERID FROM ROOT.USERINFO WHERE USERNAME='" + username + "'");
            while(rs.next()){
            
                userid = rs.getString("userid");
                System.out.println(userid);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return userid;
    }
    

    public static void insertBudget(String source, double budget, String date){
        String categoryID = "C0001";
        try{
            String query = "INSERT INTO ROOT.USER_CATEGORY (USERID, CATID, PURPOSE, COSTINCOME, DATE) VALUES (?,?,?,?,?)";
            prepstatement = conn.prepareStatement(query);
            prepstatement.setString(1, getUserID(DBAccess.currentUser));
            prepstatement.setString(2, categoryID);
            prepstatement.setString(3, source);
            prepstatement.setDouble(4, budget);
            prepstatement.setString(5, date);
            
            rowAffected = prepstatement.executeUpdate();
            System.out.println("Row inserted: " + rowAffected);
            
            conn.commit();
            prepstatement.close();
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    
    public static void insertExpenditure(String purpose, double cost, String date, String category){
        //make it negative indicating expenditure/expenses
        cost *= -1;
        try{
            String query = "INSERT INTO ROOT.USER_CATEGORY (USERID, CATID, PURPOSE, COSTINCOME, DATE) VALUES (?,?,?,?,?)";
            prepstatement = conn.prepareStatement(query);
            prepstatement.setString(1, getUserID(DBAccess.currentUser));
            prepstatement.setString(2, category);
            prepstatement.setString(3, purpose);
            prepstatement.setDouble(4, cost);
            prepstatement.setString(5, date);
            
            rowAffected = prepstatement.executeUpdate();
            System.out.println("Row inserted: " + rowAffected);
            
            conn.commit();
            prepstatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    };
    
    
    public static double getBalance(){
        String userid= getUserID(DBAccess.currentUser);
        double balance = 0.00;
        try{
            rs = stmt.executeQuery("SELECT COSTINCOME FROM ROOT.USER_CATEGORY WHERE USERID='" + userid + "'");
            while(rs.next()){
                balance += rs.getDouble("costincome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return balance;
    }
    
}
