package com.iiitb.modal;

import org.json.JSONObject;

import java.util.Iterator;

public class MCQItem extends Item{
    public void printItem(){
        System.out.println(" "+this.getItemDescription().getString("title"));
            JSONObject options = this.getItemDescription().getJSONObject("options");
            Iterator<String> keys = options.keys();
            while (keys.hasNext()){
                String key = keys.next();
                System.out.println("("+key+") "+ options.getString(key));
            }
            System.out.println();
    }

}
