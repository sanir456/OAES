package com.iiitb.modal;

public class SubjectiveItem extends Item{
    public void printItem(){
        System.out.println(" "+this.getItemDescription().getString("title"));
    }
}
