##Computing the frequency of microRNAs cooperation using the MapReduce framework

microRNAs are small non-coding molecules produced by the genome to regulate its activity. These small ribo-nucleic acids (RNA) molecules are 22-24 nucleotids long and can recognize, by base-pairing (A binds U, C binds G), complementary sequences on messengers RNAs (mRNA). The consequences of the binding of a microRNA (miR) on its complementary targets' sequences (on various mRNAs) is an inhibition of mRNAs translation into proteins, such as cellular enzymes.

Importantly, mRNAs can be targeted by many microRNAs, and the cooperation of these microRNAs can have a major impact on the regulation of their targets. 

**Can we define groups of microRNAs that cooperate frequently ?**

The specific task of finding association between items can be solved by association rules learning. The technic known as “Market Basket Analysis” (MBA) aim at computing the frequency of co-occurrence of items in transactions. Here the items are the microRNAs and the transactions are the mRNAs. I will use the microRNA-mRNA prediction from [TargetScan](http://www.targetscan.org/cgi-bin/targetscan/data_download.cgi?db=vert_70).

Here is a sample:
```
NM_000014	A2M	AAAGAAU	10090	0	0	0	0	1	0	1	0	mmu-miR-186	-0.078	NULL
NM_000014	A2M	AAUCUCU	10090	0	0	0	0	1	0	1	0	mmu-miR-216b	-0.188	0.073
```
The second column is the mRNA gene’s name and the 13th column is the name of the microRNA predicted to interact with the mRNA. From this database I selected a sample of 2000 transactions out of $$ 2.10^6 $$. (`sample-mmu-miR.txt`).

The first step is to generate the transactions : `mRNA		miR1	…	miRn`.
The Map task is performed by [generateTransactionMapper.java](\src\main\java\fr\cnrs\igmm\mg\generateTransactionMapper.java) and the reducer is [generateTransactionsReducer.java](\src\main\java\fr\cnrs\igmm\mg\generateTransactionReducer.java).

Here is the call to Hadoop:
```
hadoop jar generateTransactions.jar fr.cnrs.igmm.mg.generateTransactionsDriver input/sample-mmu-miR.txt list-miR
```

This produce the following result (`list-miR.txt`):
```
A2M	mmu-miR-186	mmu-miR-216b	mmu-miR-291a-5p	mmu-miR-128	mmu-miR-326	mmu-miR-327	mmu-miR-494	mmu-miR-760-3p	mmu-miR-673-5p	mmu-miR-27a	
```

Then I used this list of transactions to perform the Market Basket Analysis implemented by [Mahmoud Parsian](https://github.com/mahmoudparsian/data-algorithms-book/tree/master/src/main/java/org/dataalgorithms/chap07/mapreduce).

I used the following call to Hadoop:
```
hadoop jar MBA.jar org.dataalgorithms.chap07.mapreduce.MBADriver list-miR/part-r-00000 output 2
```
this will compute the number of transactions for each pair of items.
```
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | head
15/04/01 14:33:30 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
[mmu-miR-101a, mmu-miR-101c]	1
[mmu-miR-101a, mmu-miR-1188]	1
[mmu-miR-101a, mmu-miR-1190]	1
[mmu-miR-101a, mmu-miR-1194]	1
[mmu-miR-101a, mmu-miR-1197]	1
[mmu-miR-101a, mmu-miR-1198-3p]	1
[mmu-miR-101a, mmu-miR-1224]	1
[mmu-miR-101a, mmu-miR-1249]	1
[mmu-miR-101a, mmu-miR-125a-3p]	1
[mmu-miR-101a, mmu-miR-125a-5p]	1

```

Then the frequency can be easily computed by:
```
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | cut -f 2 | grep "1" | wc -l
61194
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | cut -f 2 | grep "2" | wc -l
16142
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | cut -f 2 | grep "3" | wc -l
3376
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | cut -f 2 | grep "4" | wc -l
504
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | cut -f 2 | grep "5" | wc -l
69
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | cut -f 2 | grep "6" | wc -l
7
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | cut -f 2 | grep "7" | wc -l
0
mgirardot@rfl-bioinfo:~/Bureau$ hadoop fs -cat output/part* | wc -l
81294

```


|**Counts**		 |**1** |**2** |**3** |**4** |**5** |**6** |**7** |
|---------------:|-----:|-----:|-----:|-----:|-----:|-----:|-----:|
|81294			 |61194 |16142 |3376  |504   |69    |7     |0     |
|**freq**        |0.75  |0.19  |0.04  |0.006 |0.0008|8e-5  |0     |

This show that the vast majority of the microRNA's pairs (75%)appears only once in this sample of 2000 predictions. However, we can see that a significant proportion of miR pairs (25%) appear on 2 or more targets.

**Conclusion**
The Market Basket Analysis perfomed with the Hadoop framework allows to find groups of microRNAs that cooperate frequently. However, this analysis performed for pairs is very inefficient for tuples of 3 microRNAs or more due to the exponential increase of combinaisons to consider.