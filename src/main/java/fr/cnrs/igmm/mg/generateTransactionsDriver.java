package fr.cnrs.igmm.mg;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class generateTransactionsDriver {

	  public static void main(String[] args) throws Exception {

		    /*
		     * Validate that two arguments were passed from the command line.
		     */
		    if (args.length != 2) {
		      System.out.printf("Usage: generateTransactionsDriver <input dir> <output dir>\n");
		      System.exit(-1);
		    }

		    String inputPath = args[0];
		    String outputPath = args[1];
		      
		    /*
		     * Instantiate a Job object for your job's configuration. 
		     */
		    @SuppressWarnings("deprecation")
			Job job = new Job();
		    
		    /*
		     * Specify the jar file that contains your driver, mapper, and reducer.
		     * Hadoop will transfer this jar file to nodes in your cluster running 
		     * mapper and reducer tasks.
		     */
		    job.setJarByClass(generateTransactionsDriver.class);
		    
		    /*
		     * Specify an easily-decipherable name for the job.
		     * This job name will appear in reports and logs.
		     */
		    job.setJobName("Generate Transaction Data");

		  //input/output path
		      FileInputFormat.setInputPaths(job, new Path(inputPath));
		      FileOutputFormat.setOutputPath(job, new Path(outputPath)); 
		      
		    //Mapper K, V output
		      job.setMapOutputKeyClass(Text.class);
		      job.setMapOutputValueClass(Text.class);   
		      //output format
		      job.setOutputFormatClass(TextOutputFormat.class);
		      
		      //Reducer K, V output
		      job.setOutputKeyClass(Text.class);
		      job.setOutputValueClass(Text.class);
		      
		      // set mapper/reducer
		      job.setMapperClass(generateTransactionsMapper.class);
		      job.setReducerClass(generateTransactionsReducer.class);
		      
		     
		      
		    /*
		     * Start the MapReduce job and wait for it to finish.
		     * If it finishes successfully, return 0. If not, return 1.
		     */
		    boolean success = job.waitForCompletion(true);
		    System.exit(success ? 0 : 1);
		  }
		}
