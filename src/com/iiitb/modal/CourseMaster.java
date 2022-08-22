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

    public CourseMaster() {
    }

    public CourseMaster(int courseId, String courseName, String courseDescription, JSONObject testPattern) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.testPattern = testPattern;

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



    static public CourseMaster rsToObject(ResultSet rs)
    {
        CourseMaster cm = new CourseMaster();
        try{
            if(rs.next()){
                cm.setCourseId(rs.getInt("courseId"));
                cm.setCourseName(rs.getString("courseName"));
                cm.setCourseDescription(rs.getString("courseDescription"));
                cm.setTestPattern( new JSONObject( rs.getString("courseTestPattern")));

            }
        }
        catch (Exception e){
            e.getMessage();
        }
        return cm;
    }
}
