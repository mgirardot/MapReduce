package fr.cnrs.igmm.mg;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.cnrs.igmm.mg.generateTransactionsMapper;

public class generateTransactionsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMapLongWritableTextContext() throws IOException {
		Text value = new Text("NM_000014	A2M	AAAGAAU	10090	0	0	0	0	1	0	1	0	mmu-miR-186	-0.078	NULL");
	
		new MapDriver<LongWritable, Text, Text, Text>()
		.withMapper(new generateTransactionsMapper())
		.withInput(new LongWritable(0), value)
		.withOutput(new Text("A2M"), new Text("mmu-miR-186"))
		.runTest();
	}

}
