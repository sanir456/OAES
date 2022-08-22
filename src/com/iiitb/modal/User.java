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
        System.out.println("1. Select Subject");
        System.out.println("2. LogOut");
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

    public List<Item> getQuestion(Statement stmt, ResultSet rs, String query,int courseId ,JSONObject testPattern,int sectionNumber) throws SQLException {
        JSONObject sectionInfo = testPattern.getJSONObject(Integer.toString(sectionNumber));
        query = "SELECT * FROM itembank where courseId = "+courseId+" and itemCategory = \"" + sectionInfo.getString("questionType") + "\" ORDER BY RAND() LIMIT " + sectionInfo.getInt("numberOfQuestion");
        rs = stmt.executeQuery(query);
        return Item.rsToObject(rs);
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
        QuestionPaperSet questionPaperSet = new QuestionPaperSet(setCount,testPattern.getInt("numberOfSection"),testPattern.getInt("totalMarks"));
        for(int i=0;i<setCount;i++)
        {
            List<Section> paper = new ArrayList<>();
            for(int j=0;j<questionPaperSet.getNumberOfSection();j++)
            {
                Section section = new Section();
                JSONObject sectionInfo = testPattern.getJSONObject(Integer.toString(j+1));
                section.setSectionMarks(sectionInfo.getInt("sectionMarks"));
                section.setNumberOfQuestion(sectionInfo.getInt("numberOfQuestion"));
                section.setQuestionType(sectionInfo.getString("questionType"));
                section.setNumberOfQuestionAttempt(sectionInfo.getInt("numberOfQuestionAttempt"));
                section.setQuestions(this.getQuestion(stmt,rs,query,courseId,testPattern,j+1));
                paper.add(section);
            }
            questionPaperSet.getPapers().add(paper);
        }
        questionPaperSet.printQuestionPapers();

    }

}
