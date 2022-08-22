package com.iiitb;

import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.iiitb.modal.*;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class Driver {

    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        Connection conn = JDBCconnectorUtil.getDBConnection();
        Statement stmt = JDBCconnectorUtil.getStatement(conn);
        String query="";
        ResultSet rs=null;
        User admin;
        try {
            while(true){
                System.out.print("Enter User Name: ");
                String userName = sc.nextLine();
                System.out.print("Enter Password: ");
                String password = sc.nextLine();
                query = "SELECT * FROM user WHERE userName = \""+userName+"\"";

                rs = stmt.executeQuery(query);
                admin = User.rsToObject(rs);
                if(admin.getUserPassword().equals(password)){
                    System.out.println("Sucessfully login!!");
                    break;
                }
                else {
                    System.out.println("wrong credentials!!");
                }

            }
            boolean isLogout = false;
            while(!isLogout) {
                admin.printMainMenu();
                int option  = sc.nextInt();
                switch (option){
                    case 1: {
                        admin.printCourseList(stmt, rs, query);
                        int courseId = sc.nextInt();
                        boolean isBreak = false;
                        while (!isBreak){
                            admin.printSubMenu1();
                            option = sc.nextInt();
                            switch (option){
                                case 1:{
                                    admin.generatePaperSet(stmt,rs,query,courseId,sc);
                                    break;
                                }
                                case 2:{
                                  break;
                                }
                                case 3:{
                                    isBreak = true;
                                    break;
                                }
                                default: {
                                    System.out.println("Wrong Input");
                                    break;
                                }


                            }
                        }
                        break;
                    }
                    case 2: {
                        JDBCconnectorUtil.closeConnection();
                        isLogout = true;
                        break;
                    }
                    default: {
                        System.out.println("Wrong Input");
                        break;
                    }
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
