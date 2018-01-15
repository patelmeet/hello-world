package searchjson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONObject;

import java.io.IOException;

public class SearchJsonMapper extends Mapper<Object, Text, Text, IntWritable> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(value.toString());
        String ans = "";
        if(jsonObject.has("facets")){
            if(jsonObject.has("query") && jsonObject.get("query").toString().length() > 2)
                ans = "query+facet";
            else
                ans = "facet";
        }
        else {
            if(jsonObject.has("query") && jsonObject.get("query").toString().length() > 2)
                ans = "query";
            else
                ans = "none";
        }
        context.write(new Text(ans), new IntWritable(1));
    }
}
