package org.xoanon.assignment.api.controller;



import com.datastax.driver.core.Row;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.RestExpress;
import org.xoanon.assignment.api.db.CassandraConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by viki on 10/2/15.
 */
public class UsersController {
    public CassandraConnector cassandraConnector;
    public static JSONParser parser=new JSONParser();

     public UsersController(CassandraConnector cassandraConnector){
         this.cassandraConnector=cassandraConnector;
     }
    public static String createUsers(Request request,Response response){
        try {
            Object obj = parser.parse(Bytes.toString(request.getBodyAsByteBuffer().array()));
            JSONObject json = (JSONObject) obj;
            String name=json.get("name").toString();
            String location=json.get("location").toString();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("name",name);
            map.put("location",location);
            CassandraConnector.insertDetails("users",map);
            return "User Details Inserted";
        }
        catch(Exception ex){
            ex.printStackTrace();
            return "Internal Server Error";
        }
    }
    public static String getUsers(Request request,Response response){
        try {
            String name=request.getHeader("name").toString();
            List<Row> rows=CassandraConnector.getRowListWhere("users", "name", name);
            JSONArray list = new JSONArray();
            for(Row row:rows){
            JSONObject obj = new JSONObject();
            obj.put("name",row.getString("name"));
            obj.put("location",row.getString("location"));
            list.add(obj);
            }
            return list.toJSONString();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return "Internal Server Error";
        }
    }
}
