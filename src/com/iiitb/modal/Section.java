package com.iiitb.modal;

import com.iiitb.ItemFactory;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Section {
    private int sectionMarks;
    private int numberOfQuestion;
    private String questionType;
    private int numberOfQuestionAttempt;
    List<Item> questions;

    public Section() {
    }

    public Section(int sectionMarks, int numberOfQuestion, String questionType, int numberOfQuestionAttempt, List<Item> questions) {
        this.sectionMarks = sectionMarks;
        this.numberOfQuestion = numberOfQuestion;
        this.questionType = questionType;
        this.numberOfQuestionAttempt = numberOfQuestionAttempt;
        this.questions = questions;
    }

    public int getSectionMarks() {
        return sectionMarks;
    }

    public void setSectionMarks(int sectionMarks) {
        this.sectionMarks = sectionMarks;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getNumberOfQuestionAttempt() {
        return numberOfQuestionAttempt;
    }

    public void setNumberOfQuestionAttempt(int numberOfQuestionAttempt) {
        this.numberOfQuestionAttempt = numberOfQuestionAttempt;
    }

    public List<Item> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Item> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionMarks=" + sectionMarks +
                ", numberOfQuestion=" + numberOfQuestion +
                ", questionType='" + questionType + '\'' +
                ", numberOfQuestionAttempt=" + numberOfQuestionAttempt +
                ", questions=" + questions +
                '}';
    }

    public String createSection(Statement stmt, ResultSet rs, String query, int courseId ,JSONObject sectionInfo) throws SQLException{
        String sectionString = "";
        sectionString+="\"sectionMarks\":\""+sectionInfo.getString("sectionMarks")+"\"";
        sectionString+=",\"numberOfQuestion\":\""+sectionInfo.getString("numberOfQuestion")+"\"";
        sectionString+=",\"questionType\":\""+sectionInfo.getString("questionType")+"\"";
        sectionString+=",\"numberOfQuestionAttempt\":\""+sectionInfo.getString("numberOfQuestionAttempt")+"\"";
        sectionString+=",\"questions\":[";
        ItemFactory itemFactory = new ItemFactory();
        List<Item> items = itemFactory.createItems(stmt,rs,query,courseId,sectionInfo.getString("questionType"),Integer.parseInt(sectionInfo.getString("numberOfQuestion")));

        for(int k=0;k<items.size();k++)
        {
            if(k!=0)
            {
                sectionString+=",";
            }
            sectionString+=Integer.toString(items.get(k).getItemId());
        }
        return sectionString;
    }
}
