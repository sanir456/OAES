package com.iiitb.modal;

import java.util.*;
import com.iiitb.modal.Section;

public class QuestionPaperSet {
    private int numberOfQuestionPaper=0;
    private int numberOfSection=0;
    private List<List<Section>> papers;

    public QuestionPaperSet() {
    }

    public  QuestionPaperSet(int numberOfQuestionPaper, int numberOfSection){
        this.numberOfQuestionPaper=numberOfQuestionPaper;
        this.numberOfSection=numberOfSection;
        this.papers = new ArrayList<List<Section>>();
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

    @Override
    public String toString() {
        return "QuestionPaperSet{" +
                "numberOfQuestionPaper=" + numberOfQuestionPaper +
                ", numberOfSection=" + numberOfSection +
                ", papers=" + papers +
                '}';
    }

    public void printQuestionPapers(){
        System.out.println();
        for(int i=0;i<this.numberOfQuestionPaper;i++)
        {
            System.out.println("Paper "+Integer.toString(i+1)+":");
            for(int j=0;j<this.numberOfSection;j++)
            {
                System.out.println("Section "+Integer.toString(j+1)+":");
                int index=1;
                for(Item item : this.papers.get(i).get(j).getQuestions())
                {
                    System.out.print(index);
                    item.printItem();
                    index++;
                }
            }
            System.out.println("\n");
        }
        System.out.println("\n\n");
    }
}
