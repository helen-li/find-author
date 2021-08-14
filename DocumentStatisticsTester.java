import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * DocumentStatisticsTester class tests the DocumentStatistics class with its main method.
 * @author Helen Li
 * @version May 21 2018
 */
public class DocumentStatisticsTester 
{
	/** An array of Double objects that stores the weights of each linguistic features */
	private static Double[] weights = {0.0, 11.0, 33.0, 50.0, 0.4, 4.0};

	/**
	 * Tests the DocumentStatstics class and handles FileNotFoundException and IOException.
	 * 1. Computes the statistics data for the five mystery texts files with 
	 * a BufferedReader and a FileReader to parse the document. All stats are printed out. 
	 * 2. Computes the weighted sum of the five document statistics by multiplying each 
	 * feature with its matching weight value in the instance weights array. Sum is printed out. 
	 * 3. Uses the known statistics in all the signature files to compute the weighted sum for 
	 * all known authors. Stats and weighted sum are printed out. 
	 * 4. Compares the weighted sum of the five mystery texts with the known authors to find 
	 * the closest match. The pair with the minimum difference is the final prediction.
	 * Author name and mystery file number is printed as a result. 
	 * @param args   specify the program to be tested
	 */
	public static void main(String[] args)
	{
		Map<String, Double> mysteryStats = new HashMap<String, Double>();
		try
		{
			for(int i = 1; i < 6; i++)
			{
				String address = "/Users/helenli/Desktop/FindAuthor/src/MysteryText/mystery"
						+ i +".txt";
				BufferedReader br = new BufferedReader(new FileReader(new File(address)));
				Scanner sc = new Scanner(br);
				Document doc = new Document(sc);
				doc.parseDocument();
				DocumentStatistics ds = new DocumentStatistics(doc);

				System.out.println("mystery" + i + " -");
				double wordLength = ds.getAverageWordLength();
				double typeTokenRation = ds.getTypeTokenRation();
				double hapaxLegomanaRatio = ds.getHapaxLegomanaRatio();
				double wordsPerSentence = ds.getAverageWordsPerSentence();
				double complex = ds.getSentenceComplexity();

				System.out.println("Average Word Length: " + wordLength);
				System.out.println("TTR: " + typeTokenRation);
				System.out.println("HLR: " + hapaxLegomanaRatio);
				System.out.println("Average Words/Sentence: " + wordsPerSentence);
				System.out.println("Sentence Complexity: " + complex);

				mysteryStats.put("mystery" + i, weights[1]*wordLength + weights[2]*typeTokenRation +
						weights[3]*hapaxLegomanaRatio + weights[4]*wordsPerSentence 
						+ weights[5]*complex);
				System.out.println("mystery" + i + ": " + mysteryStats.get("mystery" + i));
				System.out.println("******************");
			}
		}
		catch(FileNotFoundException ex)
		{
			System.out.print("You've encountered an error.");
			ex.printStackTrace();
		}

		Map<String, Double> signature = new HashMap<String, Double>();
		File path = new File("/Users/helenli/Desktop/FindAuthor/src/SignatureFiles");
		File[] arr = path.listFiles();
		try
		{
			for(int i = 0; i < arr.length; i++)
			{
				BufferedReader br = new BufferedReader(new FileReader(arr[i]));

				String name = br.readLine();
				double wordLength = Double.parseDouble(br.readLine());
				double typeTokenRation = Double.parseDouble(br.readLine());
				double hapaxLegomanaRatio = Double.parseDouble(br.readLine());
				double wordsPerSentence = Double.parseDouble(br.readLine());
				double complex = Double.parseDouble(br.readLine());

				System.out.println("Name: " + name);
				System.out.println("Average Word Length: " + wordLength);
				System.out.println("TTR: " + typeTokenRation);
				System.out.println("HLR: " + hapaxLegomanaRatio);
				System.out.println("Average Words/Sentence: " + wordsPerSentence);
				System.out.println("Sentence Complexity: " + complex);

				signature.put(name, weights[1]*wordLength + weights[2]*typeTokenRation +
						weights[3]*hapaxLegomanaRatio + weights[4]*wordsPerSentence 
						+ weights[5]*complex);
				System.out.println(name + ": " + signature.get(name));
				System.out.println("******************");
			}
		}
		catch(IOException ex)
		{
			System.out.print("You've encountered an error.");
			ex.printStackTrace();
		}

		double diff = Integer.MAX_VALUE;
		String author = null;
		for(String key: mysteryStats.keySet())
		{
			Double currStats = mysteryStats.get(key);
			for(String sig: signature.keySet())
			{
				Double sigStats = signature.get(sig);
				Double tempDiff = Math.abs(currStats - sigStats);
				if(tempDiff <= diff)
				{
					diff = tempDiff;
					author = sig;
				}
			}
			System.out.println(key + ": " + author);
			diff = Integer.MAX_VALUE;
			author = null;
		}
	}
}
