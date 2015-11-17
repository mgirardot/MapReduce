package fr.cnrs.igmm.mg;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.cnrs.igmm.mg.generateTransactionsReducer;

public class generateTransactionsReducerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReduceTextIterableOfTextContext() throws IOException, InterruptedException {
		
		new ReduceDriver<Text, Text, Text, Text>()
		.withReducer(new generateTransactionsReducer())
		.withInput(new Text("A2M"), Arrays.asList(new Text("mmu-miR-186"), new Text("mmu-miR-210")))
		.withOutput(new Text("A2M"), new Text("mmu-miR-186,mmu-miR-210,"))
		.runTest();
	}

}
