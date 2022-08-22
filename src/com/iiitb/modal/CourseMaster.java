package com.iiitb.modal;

import com.mysql.cj.xdevapi.JsonParser;
import org.json.JSONObject;

import javax.xml.transform.Result;
import java.sql.ResultSet;

public class CourseMaster {
    private int courseId;
    private String courseName;
    private String courseDescription;
    private JSONObject testPattern;
    private String itemCategories;

    public CourseMaster() {
    }

    public CourseMaster(int courseId, String courseName, String courseDescription, JSONObject testPattern, String itemCategories) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.testPattern = testPattern;
        this.itemCategories = itemCategories;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public JSONObject getTestPattern() {
        return testPattern;
    }

    public void setTestPattern(JSONObject testPattern) {
        this.testPattern = testPattern;
    }

    public String getItemCategories() {
        return itemCategories;
    }

    public void setItemCategories(String itemCategories) {
        this.itemCategories = itemCategories;
    }

    static public CourseMaster rsToObject(ResultSet rs)
    {
        CourseMaster cm = new CourseMaster();
        try{
            if(rs.next()){
                cm.setCourseId(rs.getInt("courseId"));
                cm.setCourseName(rs.getString("courseName"));
                cm.setCourseDescription(rs.getString("courseDescription"));
                cm.setTestPattern( new JSONObject( rs.getString("courseTestPattern")));
                cm.setItemCategories(rs.getString("itemCategories"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cm;
    }
}
