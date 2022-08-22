package com.iiitb.modal;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import com.iiitb.modal.Section;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionPaperSet {
    private int setId;
    private int courseId;
    private int numberOfQuestionPaper=0;
    private int numberOfSection=0;
    private int totalMarks=0;
    private boolean isValidate=false;
    private List<List<Section>> papers;

    public QuestionPaperSet() {
    }

    public  QuestionPaperSet(int numberOfQuestionPaper, int numberOfSection,int totalMarks,int courseId){
        this.courseId = courseId;
        this.numberOfQuestionPaper=numberOfQuestionPaper;
        this.numberOfSection=numberOfSection;
        this.totalMarks = totalMarks;
        this.papers = new ArrayList<List<Section>>();
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public int getCourseId() {
        return courseId;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean validate) {
        isValidate = validate;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getNumberOfQuestionPaper() {
        return numberOfQuestionPaper;
    }

    public void setNumberOfQuestionPaper(int numberOfQuestionPaper) {
        this.numberOfQuestionPaper = numberOfQuestionPaper;
    }

    public int getNumberOfSection() {
        return numberOfSection;
    }

    public void setNumberOfSection(int numberOfSection) {
        this.numberOfSection = numberOfSection;
    }

    public List<List<Section>> getPapers() {
        return papers;
    }

    public void setPapers(List<List<Section>> papers) {
        this.papers = papers;
    }

    static QuestionPaperSet rsToObject(Statement stmt, ResultSet rs,String query){
        QuestionPaperSet questionPaperSet = new QuestionPaperSet();
        try{
            if(rs.next()) {
                questionPaperSet.setSetId(rs.getInt("setId"));
                questionPaperSet.setCourseId(rs.getInt("courseId"));
                questionPaperSet.setNumberOfQuestionPaper(rs.getInt("numberOfQuestionPaper"));
                questionPaperSet.setNumberOfSection(rs.getInt("numberOfSection"));
                questionPaperSet.setTotalMarks(rs.getInt("totalMarks"));
                questionPaperSet.setValidate(rs.getBoolean("isValidate"));
                JSONObject paperSet = new JSONObject(rs.getString("paperSet"));
                for(int i=1;i<=questionPaperSet.getNumberOfQuestionPaper();i++)
                {
                    JSONObject paper = paperSet.getJSONObject(Integer.toString(i));
                    JSONArray questions = paper.getJSONArray("questions");
                    query =  "SELECT * FROM itembank where courseId = "+questionPaperSet.getCourseId() + " and itemId in (";
                    for(int j=0;j<questions.length();j++)
                    {
                        if(j!=0)
                            query+=",";
                        query+=Integer.toString(questions.getInt(j));
                    }
                    query+=")";
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return questionPaperSet;
    }


    public void printQuestionPapers(){
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");
        for(int i=0;i<this.numberOfQuestionPaper;i++) {
            System.out.println("\t\t\t :Paper " + Integer.toString(i + 1) + ": \t\t\t Total Marks: " + this.totalMarks);
            for (int j = 0; j < this.numberOfSection; j++) {
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("\t\t\t:Section " + Integer.toString(j + 1) + ":\t\t\t Section Marks: " + this.papers.get(i).get(j).getSectionMarks());
                System.out.println();
                int index = 1;
                for (Item item : this.papers.get(i).get(j).getQuestions()) {
                    System.out.print(index);
                    item.printItem();
                    index++;
                }
                System.out.println("---------------------------------------------------------------------------------");
            }
            System.out.println("---------------------------------------------------------------------------------");
        }
        System.out.println("---------------------------------------------------------------------------------");
    }


}
