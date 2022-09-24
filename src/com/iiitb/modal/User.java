package com.iiitb.modal;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private int userId;
    private String userType;
    private String userName;
    private String userPassword;

    public User() {
    }

    public User(int userId, String userType, String userName, String userPassword) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    static public User rsToObject(ResultSet rs)
    {
        User user = null;
        try{
            if(rs.next()) {
                user=new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserType(rs.getString("userType"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public void printMainMenu(){
        System.out.println("Menu: ");
        System.out.println("1. Select Subject to generate question paper");
        System.out.println("2. Validate Question Paper Set");
        System.out.println("3. Logout");
        System.out.print("Choose option: ");
    }

    public void printCourseList(Statement stmt, ResultSet rs,String query) throws SQLException{
        query= "SELECT * FROM coursemaster ";
        rs = stmt.executeQuery(query);
        System.out.format("%13s%15s\n","CourseId","CourseName");
        while(rs.next())
        {
            System.out.format("%10d%15s\n",rs.getInt("courseId"),rs.getString("courseName"));
        }
        System.out.print("Choose Course Id: ");
    }

    public void printInvalidQuestionPaperSetList(Statement stmt,ResultSet rs,String query) throws SQLException {
        query = "SELECT * FROM questionpaperset as qps JOIN coursemaster as cm ON qps.courseId = cm.courseId where qps.isValidate=0";
        rs=stmt.executeQuery(query);
        System.out.format("%13s%15s%15s\n","Question Paper Set Id","CourseID","CourseName");
        while(rs.next())
        {
            System.out.format("%10d%22s%15s\n",rs.getInt("setId"),rs.getInt("courseId"),rs.getString("courseName"));
        }
        System.out.print("Choose Course Id: ");
    }

    public void printSubMenu1(){
        System.out.println("Menu: ");
        System.out.println("1. Select predefined test pattern style");
        System.out.println("2. New test Pattern style");
        System.out.println("3. Back");
        System.out.print("Choose option: ");
    }
    public void printSubMenu2(){
        System.out.println("Menu: ");
        System.out.println("1. Update test pattern and generate question paper set");
        System.out.println("2. Only generate question paper test");
        System.out.print("Choose option: ");
    }


    public JSONObject getTestPattern(Statement stmt, ResultSet rs, String query,int courseId ) throws SQLException {
        query = "SELECT * FROM coursemaster WHERE courseId = "+courseId;
        rs = stmt.executeQuery(query);
        CourseMaster cm = CourseMaster.rsToObject(rs);
        return cm.getTestPattern();
    }


    public void updateTestPatternStyle(Statement stmt,String query,int courseId,JSONObject testPattern) throws SQLException {
        query ="UPDATE coursemaster SET courseTestPattern = \'" + testPattern.toString() + "\' WHERE courseId = " + courseId;
        System.out.println(query);
        stmt.executeUpdate(query);
        System.out.println("Update Successfully");
    }

    public void generatePaperSet(Statement stmt, ResultSet rs, String query,int courseId ,JSONObject testPattern,Scanner sc) throws SQLException{
        System.out.print("How many question paper do you want to generate: ");
        int setCount = sc.nextInt();
        QuestionPaperSet questionPaperSet = new QuestionPaperSet(setCount,testPattern.getInt("numberOfSection"),testPattern.getInt("totalMarks"),courseId);

        String paperSetString = "{";
        for(int i=0;i<setCount;i++)
        {
            if(i!=0)
            {
                paperSetString+=",";
            }
            paperSetString += "\""+Integer.toString(i+1)+"\":{";
            paperSetString+=questionPaperSet.createQuestionPaper(stmt,rs,query,courseId,testPattern);
            paperSetString+="}";
        }
        paperSetString+="}";
        System.out.println(paperSetString);
        JSONObject paperSet = new JSONObject(paperSetString);
        query = "INSERT INTO questionpaperset(`courseId`,`numberOfQuestionPaper`,`numberOfSection`,`paperSet`,`totalMarks`,`isValidate`) VALUES ";
        query+="('"+Integer.toString(courseId)+"','"+Integer.toString(setCount)+"','"+Integer.toString(testPattern.getInt("numberOfSection"));
        query+="','"+paperSet.toString()+"','"+Integer.toString(testPattern.getInt("totalMarks"))+"','0')";
        stmt.executeUpdate(query);
        System.out.println("Question paper set generated successfully");
    }

    public QuestionPaperSet getQuestionpaperSet(Statement stmt,ResultSet rs,String query,int setId) throws SQLException{
        query = "SELECT * FROM questionpaperset WHERE setId = "+setId;
        rs = stmt.executeQuery(query);
        QuestionPaperSet questionPaperSet = QuestionPaperSet.rsToObject(stmt,rs,query);
        questionPaperSet.printQuestionPapers();
        return questionPaperSet;
    }


}
