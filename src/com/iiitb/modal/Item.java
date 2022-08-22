package com.iiitb.modal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Item {
    private int itemId;
    private int versionId;
    private int courseId;
    private String topicName;
    private String category;
    private JSONObject itemDescription;
    private String deficultyLevel;
    private int createdBy;
    private int modifiedBy;
    private boolean isActive;

    public Item() {
    }

    public Item(int itemId, int versionId, int courseId, String topicName, String category, JSONObject itemDescription, String deficultyLevel, int createdBy, int modifiedBy, boolean isActive) {
        this.itemId = itemId;
        this.versionId = versionId;
        this.courseId = courseId;
        this.topicName = topicName;
        this.category = category;
        this.itemDescription = itemDescription;
        this.deficultyLevel = deficultyLevel;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.isActive = isActive;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public JSONObject getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(JSONObject itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getDeficultyLevel() {
        return deficultyLevel;
    }

    public void setDeficultyLevel(String deficultyLevel) {
        this.deficultyLevel = deficultyLevel;
    }


    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    static public List<Item> rsToObject(ResultSet rs){
        List<Item> questions = new ArrayList<>();
        try {
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("itemId"));
                item.setVersionId(rs.getInt("versionId"));
                item.setCourseId(rs.getInt("courseId"));
                item.setTopicName(rs.getString("topicName"));
                item.setCategory(rs.getString("itemCategory"));
                item.setItemDescription(new JSONObject(rs.getString("itemDescription")));
                item.setDeficultyLevel(rs.getString("difficultyLevel"));
                item.setCreatedBy(rs.getInt("createdBy"));
                item.setModifiedBy(rs.getInt("modifiedBy"));
                item.setActive(rs.getBoolean("isActive"));
                questions.add(item);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return questions;
    }

    public void printItem()
    {
        if(this.getCategory().equals("subjective")){
            System.out.println(" "+this.getItemDescription().getString("title"));
        }
        else if(this.getCategory().equals("mcq")){
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
}
