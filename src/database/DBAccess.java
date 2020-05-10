/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
    public static DefaultTableModel overviewTableModel;
    public static DefaultTableModel overviewUserTableModel;
    public static ArrayList<String> listUserCatID = new ArrayList<>();
    
    private static int executeUpdate(String insert_into_userinfouseridusernamepasswor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public DBAccess(){
        conn = DBConnection.getConnection();
        stmt = DBConnection.getStmt();
        rs = DBConnection.getRS();
        
    }
    
    //Get UserInfo
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
                JOptionPane.showMessageDialog(new JFrame(), "Username used by someone else! Please rename your username.");
//                System.out.println("Username is used");
                break;
            }
        }
        if(isAvailable){
            registerUser(uid,uname,pw,email);
            JOptionPane.showMessageDialog(new JFrame(), "Registered Succesfully! Please return to Login Page now.");
            
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
//                System.out.println("invalid login");
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Login! Please recheck your username and password.");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean checkAdmin(String uname) {
        
        boolean isAdmin = false;
        
        try{
            rs = stmt.executeQuery("SELECT isadmin FROM root.userinfo WHERE username='" + uname + "'");
            
            while(rs.next()){

                isAdmin = rs.getBoolean("isadmin");
                return isAdmin;
            }
            
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return isAdmin;

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
    
    public static void getSpecificUsername(String searchName){
        
        String userid, username, email, password;
        final Object[][] rowData = {};
        final Object[] columnNames = { "UserID", "Username", "Email", "Password" };
        overviewUserTableModel = new DefaultTableModel(rowData, columnNames);
        try{
            rs = stmt.executeQuery("SELECT * FROM USERINFO WHERE USERNAME LIKE '%"+ searchName +"%'");
            while(rs.next()){
                
                userid = rs.getString("USERID");
                username = rs.getString("USERNAME");
                email = rs.getString("EMAIL");
                password = rs.getString("PASSWORD");
                
                System.out.println(userid + " " + username + " " + email + " " + password);
                
                //Add to JTable
                overviewUserTableModel.addRow(new Object[] { userid, username, email, password });

            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    //Insert Methods
    public static void insertBudget(String source, double budget, String date){
        String categoryID = "C0001";
        try{
            String query = "INSERT INTO ROOT.USER_CATEGORY (USERCATID, USERID, CATID, PURPOSE, COSTINCOME, DATE) VALUES (?,?,?,?,?,?)";
            prepstatement = conn.prepareStatement(query);
            prepstatement.setString(1, getUserCatID());
            prepstatement.setString(2, getUserID(DBAccess.currentUser));
            prepstatement.setString(3, categoryID);
            prepstatement.setString(4, source);
            prepstatement.setDouble(5, budget);
            prepstatement.setString(6, date);
            
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
            String query = "INSERT INTO ROOT.USER_CATEGORY (USERCATID, USERID, CATID, PURPOSE, COSTINCOME, DATE) VALUES (?,?,?,?,?,?)";
            prepstatement = conn.prepareStatement(query);
            prepstatement.setString(1, getUserCatID());
            prepstatement.setString(2, getUserID(DBAccess.currentUser));
            prepstatement.setString(3, category);
            prepstatement.setString(4, purpose);
            prepstatement.setDouble(5, cost);
            prepstatement.setString(6, date);
            
            rowAffected = prepstatement.executeUpdate();
            System.out.println("Row inserted: " + rowAffected);
            
            conn.commit();
            prepstatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    };
    
    
    //Get Methods
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
    
    public static double getExpenditure(){
        String userid= getUserID(DBAccess.currentUser);
        double expenses = 0.00;
        try{
            rs = stmt.executeQuery("SELECT COSTINCOME FROM ROOT.USER_CATEGORY WHERE USERID='" + userid + "' AND COSTINCOME < 0");
            while(rs.next()){
                expenses -= rs.getDouble("costincome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return expenses;
    }
    
    public static double getExpensesCat(String category){
        String userid= getUserID(DBAccess.currentUser);
        double expenses = 0.00;
        try{
            rs = stmt.executeQuery("SELECT COSTINCOME FROM ROOT.USER_CATEGORY WHERE USERID='" + userid + "' AND CATID='" + category + "'");
            while(rs.next()){
                expenses -= rs.getDouble("costincome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return expenses;
    }
   
    public static void fetchOverviewTable(){
        
        listUserCatID.clear();
        String usercatid, purpose, catid, date;
        double costincome;
        final Object[][] rowData = {};
        final Object[] columnNames = { "Source/Purpose", "Category", "Income/Cost", "Date" };
        overviewTableModel = new DefaultTableModel(rowData, columnNames);
        try{
            rs = stmt.executeQuery("SELECT USERCATID, PURPOSE, CATID, COSTINCOME, DATE FROM ROOT.USER_CATEGORY WHERE USERID='" + getUserID(DBAccess.currentUser) + "'");
            while(rs.next()){
                
                usercatid = rs.getString("USERCATID");
                purpose = rs.getString("PURPOSE");
                catid = rs.getString("CATID");
                costincome = rs.getDouble("COSTINCOME"); 
                date = rs.getString("DATE");
                
                System.out.println(usercatid + " " + purpose + " " + catid + " " + costincome+ " " + date );
                
                //Add to ArrayList for Delete/Update purpose
                listUserCatID.add(usercatid);
                //Add to JTable
                overviewTableModel.addRow(new Object[] { purpose, catid, costincome, date });

            }
            
//            System.out.println( "CATEGORY ROW COUNT : !!!!!!!! " + listUserCatID.size());
            
            //MainFrame.tableBudget.setModel(overviewTableModel);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void fetchUserOverviewTable(){
        
        String userid, username, email, password;
        final Object[][] rowData = {};
        final Object[] columnNames = { "UserID", "Username", "Email", "Password" };
        overviewUserTableModel = new DefaultTableModel(rowData, columnNames);
        try{
            rs = stmt.executeQuery("SELECT USERID, USERNAME, EMAIL, Password FROM ROOT.USERINFO WHERE ISADMIN = false");
            while(rs.next()){
                
                userid = rs.getString("USERID");
                username = rs.getString("USERNAME");
                email = rs.getString("EMAIL");
                password = rs.getString("PASSWORD");
                
                System.out.println(userid + " " + username + " " + email + " " + password);
                
                //Add to JTable
                overviewUserTableModel.addRow(new Object[] { userid, username, email, password });

            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static String getUserPW(){
        String pw = "";
        try{
            String query = "SELECT PASSWORD FROM ROOT.USERINFO WHERE USERNAME='" + DBAccess.currentUser + "'";
            rs = stmt.executeQuery(query);
            while(rs.next())
                pw = rs.getString("PASSWORD");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        return pw;
    }
    
    public static void changePW(String newpw, String oldpw){
//    String userid = getUserID(DBAccess.currentUser);
        try{
            if(getUserPW().equals(oldpw))
                stmt.executeUpdate("UPDATE ROOT.USERINFO SET PASSWORD='" + newpw + "' WHERE USERNAME='" + DBAccess.currentUser + "'");
            else
//                System.out.println("Wrong password");
                JOptionPane.showMessageDialog(new JFrame(), "Password Incorrect! Please check your password field again.");

            conn.commit();
            } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static String getUserCatID() {
        
        String userCatID = "UC0000";
        
        try{
            rs = stmt.executeQuery("SELECT * FROM ROOT.USER_CATEGORY ORDER BY USERCATID DESC FETCH FIRST ROW ONLY");
            
            while(rs.next()){
                userCatID = rs.getString("USERCATID");
            }
            
        } catch(Exception e){
            e.printStackTrace();

        }
        
        int usercatCount = Integer.parseInt(userCatID.substring(2,6));

        usercatCount++;
        String strID= Integer.toString(usercatCount);
        int lengthID = strID.length();
        int requiredZeros = 4 - lengthID;

        String zeropads = "";
        for(int i=0 ; i< requiredZeros ; i++){
            zeropads += "0";
        }

        strID = zeropads + strID;
        strID = "UC" + strID;

        return strID;
        
    }
    
    
    //Update Methods
    public static void updateBudgetTableRowValue(String usercatID, String purpose, double costIncome, String date){

        try{

            String query = ("UPDATE ROOT.USER_CATEGORY SET PURPOSE= ?, COSTINCOME = ?, DATE = ?" 
                    + " WHERE USERCATID = ?");

            //Using this prepare statement would be safer as it prevent SQL injection
            prepstatement = conn.prepareStatement(query);

            prepstatement.setString(1, purpose);
            prepstatement.setDouble(2, costIncome);
            prepstatement.setString(3, date);
            prepstatement.setString(4, usercatID);

            int rowAffected = prepstatement.executeUpdate();

            conn.commit();
            prepstatement.close();

            System.out.println("*****Update Budget Table Row (" + rowAffected + ") Success!");

            } catch(Exception e){
                e.printStackTrace();
        }
    }
    
    public static void updateUserListTableRowValue(String userID, String username, String email, String password){

        try{

            String query = ("UPDATE ROOT.USERINFO SET USERNAME= ?, PASSWORD = ?, EMAIL = ?" 
                    + " WHERE USERID = ?");

            //Using this prepare statement would be safer as it prevent SQL injection
            prepstatement = conn.prepareStatement(query);

            prepstatement.setString(1, username);
            prepstatement.setString(2, password);
            prepstatement.setString(3, email);
            prepstatement.setString(4, userID);

            int rowAffected = prepstatement.executeUpdate();

            conn.commit();
            prepstatement.close();

            JOptionPane.showMessageDialog(new JFrame(), userID + " has been updated successfully!");
            
            System.out.println("*****Update User Table Row (" + rowAffected + ") Success!");

            } catch(Exception e){
                
                JOptionPane.showMessageDialog(new JFrame(), userID + " has NOT been updated!");
                e.printStackTrace();
            }
    }
    
    
    //Delete Methods
    public static void deleteBudgetTableRowValue(String usercatID){

        try{

            String query = ("DELETE FROM ROOT.USER_CATEGORY WHERE USERCATID = ?");

            //Using this prepare statement would be safer as it prevent SQL injection
            prepstatement = conn.prepareStatement(query);

            prepstatement.setString(1, usercatID);

            int rowAffected = prepstatement.executeUpdate();

            conn.commit();
            prepstatement.close();

            System.out.println("*****Delete Budget Table Row (" + rowAffected + ") Success!");

            } catch(Exception e){
                e.printStackTrace();
        }
    }
    
    public static void deleteUserListTableRowValue(String userID){

        try{

            String query = ("DELETE FROM ROOT.USERINFO WHERE USERID = ?");

            //Using this prepare statement would be safer as it prevent SQL injection
            prepstatement = conn.prepareStatement(query);

            prepstatement.setString(1, userID);

            int rowAffected = prepstatement.executeUpdate();

            conn.commit();
            prepstatement.close();

            System.out.println("*****Delete User Table Row (" + rowAffected + ") Success!");

            } catch(Exception e){
                e.printStackTrace();
        }
    }

    
    //overloading for chart
    public static double getExpensesCat(String category,  String month, String year){
        String userid= getUserID(DBAccess.currentUser);
        double expenses = 0.00;
        String monthid = "0";
        
        if(month == "January")
        {
            monthid = "1";
        }
        if(month == "February")
        {
            monthid = "2";
        }
        if(month == "March")
        {
            monthid = "3";
        }
        if(month == "April")
        {
            monthid = "4";
        }
        if(month == "May")
        {
            monthid = "5";
        }
        if(month == "June")
        {
            monthid = "6";
        }
        if(month == "July")
        {
            monthid = "7";
        }
        if(month == "August")
        {
            monthid = "8";
        }
        if(month == "September")
        {
            monthid = "9";
        }
        if(month == "October")
        {
            monthid = "10";
        }
        if(month == "November")
        {
            monthid = "11";
        }
        if(month == "December")
        {
            monthid = "12";
        }
        
        
        System.out.println(month+year+monthid);
        try{
            rs = stmt.executeQuery("SELECT COSTINCOME FROM ROOT.USER_CATEGORY WHERE USERID='" + userid + "' AND CATID='" + category + "' AND month(Date)= " + monthid + "AND year(Date)= "+year+"");
            while(rs.next()){
                expenses -= rs.getDouble("costincome");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return expenses;
    }

}
