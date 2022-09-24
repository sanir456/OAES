package com.iiitb;

import com.iiitb.modal.Item;
import com.iiitb.modal.Section;

import java.util.ArrayList;
import java.util.List;

public class Iterator2 extends SectionIterator {
    int currentPosition;
    List<Section> sections;
    Iterator2(List<Section> sections )
    {
        this.currentPosition=0;
        this.sections =sections;
        this.order = new ArrayList<String>();

        order.add("subjective");
        order.add("mcq");

    }

    boolean hasNext()
    {
        while(currentPosition< order.size())
        {
            for(int j=0;j<this.sections.size();j++)
            {
                if(this.sections.get(j).getQuestionType().equals(order.get(currentPosition)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    void getNext()
    {
        while(currentPosition< order.size())
        {
            for(int j=0;j<this.sections.size();j++)
            {
                if(this.sections.get(j).getQuestionType().equals(order.get(currentPosition)))
                {
                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("\t\t\t:Section " + Integer.toString(j + 1) + ":\t\t\t Section Marks: " + this.sections.get(j).getSectionMarks());
                    System.out.println();
                    int index = 1;
                    for (Item item : this.sections.get(j).getQuestions()) {
                        System.out.print(index);
                        item.printItem();
                        index++;
                    }
                    System.out.println("---------------------------------------------------------------------------------");
                }
            }
        }

    }
}




