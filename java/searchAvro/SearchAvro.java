package searchAvro;

import org.apache.avro.Schema;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyValueOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import searchjson.SearchJsonReducer;

import java.io.IOException;

public class SearchAvro extends Configured implements Tool {

    public int run(String[] rawArgs) throws IOException, ClassNotFoundException, InterruptedException {
        if (rawArgs.length != 2) {
            System.err.println("Usage: MapReduceColorCount <input path> <output path>");
            return -1;
        }

        Job job = new Job(getConf());

        job.setJarByClass(SearchAvro.class);
        job.setJobName("Avro Average");

        String[] args = new GenericOptionsParser(rawArgs).getRemainingArgs();
        Path inPath = new Path(args[0]);
        Path outPath = new Path(args[1]);

        FileInputFormat.setInputPaths(job, inPath);
        FileOutputFormat.setOutputPath(job, outPath);
        outPath.getFileSystem(super.getConf()).delete(outPath, true);

        job.setInputFormatClass(AvroKeyInputFormat.class);
        job.setMapperClass(SearchAvroMapper.class);
        AvroJob.setInputKeySchema(job, my_record.getClassSchema());
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setCombinerClass(SearchJsonReducer.class);

        job.setOutputFormatClass(AvroKeyValueOutputFormat.class);
        job.setReducerClass(SearchJsonReducer.class);

        return (job.waitForCompletion(true) ? 0 : 1);

    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new SearchAvro(), args);
        System.exit(res);
    }
}
