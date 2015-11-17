package fr.cnrs.igmm.mg;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class generateTransactionsReducer extends Reducer<Text, Text, Text, Text> {

	  @Override
	  public void reduce(Text key, Iterable<Text> values, Context context)
	      throws IOException, InterruptedException {
		  Text concat = new Text();
		  
		  for (Text value : values) {
			 
				concat.set(concat.toString() + value + "\t");
			}
			context.write(key, concat);
	  }
	}