package searchAvro;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SearchAvroMapper extends Mapper<AvroKey<my_record>, NullWritable, Text, IntWritable> {

    public void map(AvroKey<my_record> key, NullWritable value, Context context) throws IOException, InterruptedException {

        String searchquery = String.valueOf(key.datum().get("searchquery"));
        String filters = String.valueOf(key.datum().get("filters"));

        String ans = "";
        if(searchquery.length() > 0 && filters.length() > 0)
            ans = "query+facet";
        else if(searchquery.length() > 0)
            ans = "query";
        else if(filters.length() > 0)
            ans = "facet";
        else
            ans = "none";
        context.write(new Text(ans), new IntWritable(1));
    }
}
