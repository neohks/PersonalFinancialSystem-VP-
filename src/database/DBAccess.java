/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author NKS
 */
public class DBAccess {
    
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement prepstatement;
    
    public DBAccess(){
        conn = DBConnection.getConnection();
        stmt = DBConnection.getStmt();
        rs = DBConnection.getRS();
    }
    
    public static void checkAvailableUsername(String uname, String pw) throws Exception {
        ArrayList<String> usernameLists = new ArrayList<String>();
        String uid = "U0001";
        boolean isAvailable = true;
        try{
            rs = stmt.executeQuery("select * from app.userinfo");
            
            while(rs.next()){
                String name = rs.getString("username");
                uid = rs.getString("uid");
                usernameLists.add(name);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        for(int i=0; i<usernameLists.size();i++){
            if(uname.equals(usernameLists.get(0))){
                isAvailable=false;
                System.out.println("Username is used");
                break;
            }
        }
        if(isAvailable){
            registerUser(uid,uname,pw);
        }
        
    }
    
    public static void registerUser(String uid, String uname, String pw) throws Exception{
        
        int userid = Integer.parseInt(uid.substring(1,5));
        userid++;
        String strID= Integer.toString(userid);
        String newNum = String.format("%04d", strID);
        String newUserID = "U" + newNum;
        
        System.out.println(newUserID);
        
        
//        String strID= Integer.toString(userid);
//        int lengthID = strID.length();
//        int requiredZeros = 4-lengthID;
//        
//        String zeropads="";
//        for(int i=0 ; i<requiredZeros ; i++){
//            zeropads += "0";
//        }
//        
//        strID = zeropads + strID;
        
        
        String query = "INSERT INTO userinfo(`u`, `Module_ID`, `Mark`, `Grade`, `Points`, `Credit_earned`, `Remarks`, `Semester`) VALUES "
                + "(?,?,?,?,?,?,?,?)";
			
        try{
            stmt.executeQuery(query);
        }catch (Exception e){
            
        }
        
    }
    
    
}
