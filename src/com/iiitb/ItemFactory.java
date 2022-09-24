package com.iiitb;

import com.iiitb.modal.Item;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ItemFactory {
    public List<Item> createItems(Statement stmt, ResultSet rs, String query, int courseId, String questionType, int numberOfQuestion) throws SQLException {
        query = "SELECT * FROM itembank where courseId = "+courseId+" and itemCategory = \"" + questionType + "\" ORDER BY RAND() LIMIT " + numberOfQuestion;
        rs = stmt.executeQuery(query);
        return Item.rsToObject(rs);
    }
}
