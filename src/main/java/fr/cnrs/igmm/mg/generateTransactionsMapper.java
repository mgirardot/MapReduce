package fr.cnrs.igmm.mg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class generateTransactionsMapper extends Mapper<LongWritable, Text, Text, Text> {
	 @Override
	  public void map(LongWritable key, Text value, Context context)
	      throws IOException, InterruptedException {
		 
		 
		 String line = value.toString();
	      List<String> items = convertItemsToList(line);
	      if ((items == null) || (items.isEmpty())) {
	         // no mapper output will be generated
	         return;
	      }
	      
	      generateMapperOutput(items, context);
	  }
	 
	 private void generateMapperOutput(List<String> items,
			Context context) throws IOException, InterruptedException {
		 Text key = new Text();
		key.set(items.get(1));
		
		Text value = new Text();
		value.set(items.get(12));
		
		context.write(key, value);
		
	}

	private static List<String> convertItemsToList(String line) {
	      if ((line == null) || (line.length() == 0)) {
	         // no mapper output will be generated
	         return null;
	      }      
	      String[] tokens = StringUtils.split(line, "\t");   
	      if ( (tokens == null) || (tokens.length == 0) ) {
	         return null;
	      }
	      List<String> items = new ArrayList<String>();         
	      for (String token : tokens) {
	         if (token != null) {
	             items.add(token.trim());
	         }         
	      }         
	      return items;
	   }
	   
}
