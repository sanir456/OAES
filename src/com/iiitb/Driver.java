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
            System.out.println("\t\tWelcome Online Assessment and Evolution System (OAES)");
            while(true){
                System.out.print("Enter User Name: ");
                String userName = sc.nextLine();
                System.out.print("Enter Password: ");
                String password = sc.nextLine();
                query = "SELECT * FROM user WHERE userName = \""+userName+"\"";

                rs = stmt.executeQuery(query);
                admin = User.rsToObject(rs);
                if(admin!=null && admin.getUserPassword().equals(password)){
                    System.out.println("\tSucessfully login!!");
                    break;
                }
                else {
                    System.out.println("\twrong credentials!!");
                }

            }
            boolean isLogout = false;
            while(!isLogout) {
                admin.printMainMenu();
                int option  = sc.nextInt();
                switch (option){
                    case 1: {
                          PaperGenerator paperGenerator = new PaperGenerator();
                          paperGenerator.generatePaper(stmt,rs,query,admin);
                        break;
                    }
                    case 2: {
                        admin.printInvalidQuestionPaperSetList(stmt,rs,query);
                        int setId = sc.nextInt();
                        admin.getQuestionpaperSet(stmt,rs,query,setId);
                        System.out.println("Validate Question Paper set:(yes/no)?");
                        sc = new Scanner(System.in);
                        String response = sc.nextLine();
                        System.out.println("Successfully Validate");
                        break;
                    }
                    case 3: {
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
