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

    private static int executeUpdate(String insert_into_userinfouseridusernamepasswor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
                uid = rs.getString("userid");
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
//        String strID= Integer.toString(userid);
//        String newNum = String.format("%04d", strID);
//        String newUserID = "U" + newNum;
        
//        System.out.println(newUserID);
        
        System.out.println("register");
        String strID= Integer.toString(userid);
        int lengthID = strID.length();
        int requiredZeros = 4-lengthID;
        
        String zeropads="";
        for(int i=0 ; i<requiredZeros ; i++){
            zeropads += "0";
        }
        
        strID = zeropads + strID;
        
        boolean isadmin = false;
        
        try{
            //stmt.executeQuery(query);
//            String query = "INSERT INTO userinfo(`userid`, `username`, `password`, `isAdmin`) VALUES (?,?,?,?)";
//            prepstatement = conn.prepareStatement(query);
//            prepstatement.setString(1, strID);
//            prepstatement.setString(2, uname);
//            prepstatement.setString(3, pw);
//            prepstatement.setBoolean(4,false);
            
//            int rowAffected = stmt.executeUpdate("insert into userinfo(userid,username,password,isadmin) values ('U0002', 'aaa', 'aaa', 'false')");
//            System.out.println("*****Insert One Result Table Success!");
//            System.out.println(String.format("Row affected %d", rowAffected));

            String insertSQL = "INSERT INTO userinfo (userID, username, password, isadmin) values ('U" + strID + "', '" + uname + "', '" + pw + "', " + isadmin + ")";
            System.out.println(insertSQL);
            stmt.executeQuery(insertSQL);
//            System.out.println(row);
            conn.commit();
            //prepstatement.close();
            
//            INSERT INTO userinfo (userID, username, password, isadmin) values ('U0002','aaa','sss',false)

            
            
        }catch (Exception e){
            
        }
        
    }
    
    
}
