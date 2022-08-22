package com.iiitb.modal;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private int totalMarks;
    private int numberOfQuestion;
    private String questionType;
    private int numberOfQuestionAttempt;
    List<Item> questions;

    public Section() {
    }

    public Section(int totalMarks, int numberOfQuestion, String questionType, int numberOfQuestionAttempt, List<Item> questions) {
        this.totalMarks = totalMarks;
        this.numberOfQuestion = numberOfQuestion;
        this.questionType = questionType;
        this.numberOfQuestionAttempt = numberOfQuestionAttempt;
        this.questions = questions;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
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
                "totalMarks=" + totalMarks +
                ", numberOfQuestion=" + numberOfQuestion +
                ", questionType='" + questionType + '\'' +
                ", numberOfQuestionAttempt=" + numberOfQuestionAttempt +
                ", questions=" + questions +
                '}';
    }
}
