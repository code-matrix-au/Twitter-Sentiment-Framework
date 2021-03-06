package student;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;

import com.opencsv.exceptions.CsvException;


public class Tests {

	// change the below paths to where your own versions of the data are located
	
	String SAMPLE_CSV_FILE_PATH = "C:\\Users\\ASHWIN-PC\\Desktop\\COMP2010 Algorithms and Data Structures\\Assignment 2\\data-bundle\\training-10.csv";
	String BASIC_SENT_FILE_PATH = "C:\\Users\\ASHWIN-PC\\Desktop\\COMP2010 Algorithms and Data Structures\\Assignment 2\\data-bundle\\basic-sent-words.txt";
	String INV_INDEX_FILE_PATH = "C:\\Users\\ASHWIN-PC\\Desktop\\COMP2010 Algorithms and Data Structures\\Assignment 2\\data-bundle\\inv-index-50.txt";
	String FINEGRAINED_SENT_FILE_PATH = "C:\\Users\\ASHWIN-PC\\Desktop\\COMP2010 Algorithms and Data Structures\\Assignment 2\\data-bundle\\finegrained-sent-words.txt";
	
	Double tol = 0.01;
	
	// SAMPLE PASS-LEVEL TESTS
	
	@Test
	public void testNumTweets() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}

		assertEquals(Integer.valueOf(10), d.numTweets());
	}
	
	@Test
	public void testTweetUser() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals("_TheSpecialOne_", d.getTweetByID("1467810369").getUser());
	}

	@Test
	public void testTweetGoldPolarity() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}

		assertEquals(Polarity.NEG, d.getTweetByID("1467810369").getGoldPolarity());
	}

	@Test
	public void testTweetPredictedPolarity() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Polarity.NONE, d.getTweetByID("1467810369").getPredictedPolarity());
	}

	@Test
	public void testTweetText() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals("@switchfoot http://twitpic.com/2y1zl - Awww, that's a bummer.  You shoulda got David Carr of Third Day to do it. ;D", d.getTweetByID("1467810369").getText());
	}

	@Test
	public void testTweetWords() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		String w = d.getTweetByID("1467810369").getWords()[0];
		assertEquals("awww", w);
	}

	@Test
	public void testTweetKeywordSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Polarity.NEG, d.getTweetByID("1467810672").getPredictedPolarity());
	}

	@Test
	public void testTweetKeywordCorrectSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Boolean.TRUE, d.getTweetByID("1467810672").correctlyPredictedPolarity());
	}

	@Test
	public void testTweetKeywordIncorrectSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Boolean.FALSE, d.getTweetByID("1467811184").correctlyPredictedPolarity());
	}

	@Test
	public void testTweetKeywordAccuracy() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Double.valueOf(0.4), d.accuracy(), tol);
	}

	@Test
	public void testTweetKeywordCoverage() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Double.valueOf(0.5), d.coverage(), tol);
	}

	// SAMPLE CREDIT-LEVEL TESTS
	
    @Test
	public void testAddNeighbour() {
		TweetCollection d = new TweetCollection();
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.getTweetByID("1467810672").addNeighbour("1467811372");
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Boolean.TRUE, d.getTweetByID("1467810672").isNeighbour("1467811372"));
	}

    @Test
	public void testDeleteNeighbour() {
		TweetCollection d = new TweetCollection();
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.getTweetByID("1467810672").addNeighbour("1467811372");
			d.getTweetByID("1467810672").deleteAllNeighbours();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Boolean.FALSE, d.getTweetByID("1467810672").isNeighbour("1467811372"));
	}


	@Test
	public void testImportInverseIndex() {
		TweetCollection d = new TweetCollection();
		String[] IDs = {"1467811184", "1467811372"};
		Vector<String> v = new Vector<String>(Arrays.asList(IDs));
		Map<String, Vector<String>> invIndex = null;
		try {
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(v, invIndex.get("whole"));
	}

	@Test
	public void testGraphConstruction() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.constructSharedWordGraph(invIndex);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Integer.valueOf(1), d.getTweetByID("1467810672").numNeighbours());
	}

	@Test
	public void testNumComponents() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Integer.valueOf(7), d.numConnectedComponents());
	}

	@Test
	public void testComponentLabelCountPos() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Integer.valueOf(1), d.componentSentLabelCount("1467811372", Polarity.POS));
	}

	@Test
	public void testComponentLabelCountNeg() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Integer.valueOf(0), d.componentSentLabelCount("1467811372", Polarity.NEG));
	}

	@Test
	public void testComponentLabelCountNeut() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Integer.valueOf(0), d.componentSentLabelCount("1467811372", Polarity.NEUT));
	}

	@Test
	public void testComponentLabelCountNone() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Integer.valueOf(1), d.componentSentLabelCount("1467811372", Polarity.NONE));
	}

	
	// SAMPLE DISTINCTION-LEVEL TESTS
	

	@Test
	public void testLabelPropagationOverComponent1() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
			d.propagateLabelAcrossComponent("1467811372", Polarity.NEUT, Boolean.TRUE);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Polarity.NEUT, d.getTweetByID("1467811372").getPredictedPolarity());
	}

	@Test
	public void testLabelPropagationOverComponent2() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
			d.propagateLabelAcrossComponent("1467811372", Polarity.NEUT, Boolean.FALSE);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Polarity.NEUT, d.getTweetByID("1467811184").getPredictedPolarity());
	}

	@Test
	public void testComponentMajorityLabel() {
		TweetCollection d = new TweetCollection();
		Map<String, Vector<String>> invIndex = null;
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			invIndex = d.importInverseIndexFromFile(INV_INDEX_FILE_PATH);
			d.importBasicSentimentWordsFromFile(BASIC_SENT_FILE_PATH);
			d.predictTweetSentimentFromBasicWordlist();
			d.constructSharedWordGraph(invIndex);
			d.annotateConnectedComponents();
			d.propagateMajorityLabelAcrossComponents(Boolean.FALSE);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Polarity.POS, d.getTweetByID("1467811372").getPredictedPolarity());
	}
	/*
	*/

	@Test
	public void testTweetFinegrainedKeywordSentiment() {
		TweetCollection d = new TweetCollection();
		
		try {
			d.ingestTweetsFromFile(SAMPLE_CSV_FILE_PATH);
			d.importFinegrainedSentimentWordsFromFile(FINEGRAINED_SENT_FILE_PATH);
			d.predictTweetSentimentFromFinegrainedWordlist(2, 1);
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		catch (CsvException e) {
			System.out.println("in exception: " + e);
		}
		assertEquals(Polarity.NEG, d.getTweetByID("1467811594").getPredictedPolarity());
	}


}
