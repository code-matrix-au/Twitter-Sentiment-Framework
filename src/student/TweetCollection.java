package student;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import org.junit.Test;

public class TweetCollection {

	// TODO: add appropriate data types

	private TreeMap<String, Tweet> graph;
	private HashMap<String, String> sentimentWords;
	private HashMap<String, FineGrained> fineGrainedWords;
	private Tweet tweet;
	private int count;
	private boolean importBasicSentiment;
	private boolean importFinegrained;

	public TweetCollection() {
		// Constructor
		graph = new TreeMap<String, Tweet>();
		sentimentWords = new HashMap<String, String>();
		fineGrainedWords = new HashMap<String, FineGrained>();
		count = 0;
		importBasicSentiment = false;
		importFinegrained = false;
		// TODO
	}

	/*
	 * functions for accessing individual tweets
	 */

	public Tweet getTweetByID(String ID) {
		// PRE: -
		// POST: Returns the Tweet object that with tweet ID

		// TODO

		return graph.get(ID);
	}

	public Integer numTweets() {
		// PRE: -
		// POST: Returns the number of tweets in this collection

		// TODO

		return graph.size();
	}

	/*
	 * ingestTweetsFromFile functions for accessing sentiment words
	 */

	public Polarity getBasicSentimentWordPolarity(String w) {
		// PRE: w not null, basic sentiment words already read in from file
		// POST: Returns polarity of w
		if (w != null && isBasicSentWord(w)) {

			String word = sentimentWords.get(w);
			System.out.println(word);
			if (word == "positive") {
				return Polarity.POS;
			} else if (word == "negative") {
				return Polarity.NEG;
			}

		}

		return Polarity.NONE;
	}

	public Polarity getFinegrainedSentimentWordPolarity(String w) {
		// PRE: w not null, finegrained sentiment words already read in from file
		// POST: Returns polarity of w

		if (w != null && isFinegrainedSentWord(w)) {

			String result = sentimentWords.get(w);
			if (result == "positive") {
				return Polarity.POS;
			} else if (result == "negative") {
				return Polarity.NEG;
			}

		}

		// TODO

		return Polarity.NONE;
	}

	public Strength getFinegrainedSentimentWordStrength(String w) {
		// PRE: w not null, finegrained sentiment words already read in from file
		// POST: Returns strength of w
		if (w != null && isFinegrainedSentWord(w)) {

			FineGrained data = fineGrainedWords.get(w);
			if (data.type == "strongsubj") {
				return Strength.STRONG;
			} else if (data.type == "weaksubj") {
				return Strength.WEAK;
			}

		}

		// TODO

		return Strength.NONE;
	}

	/*
	 * functions for reading in tweets
	 * 
	 */

	public void ingestTweetsFromFile(String fInName) throws IOException, CsvException {
		// PRE: -
		// POST: Reads tweets from .csv file, stores in data structure

		// NOTES
		// Data source, file format description at
		// http://help.sentiment140.com/for-students
		// Using opencsv reader: https://zetcode.com/java/opencsv/

		try (CSVReader reader = new CSVReader(new FileReader(fInName))) {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) { // nextLine[] is an array of values from the line

				Tweet tw = new Tweet(nextLine[0], // gold polarity
						nextLine[1], // ID
						nextLine[2], // date
						nextLine[4], // user
						nextLine[5]); // text
				// TODO: insert tweet tw into appropriate data type
				graph.put(nextLine[1], tw);
			}
		}
	}

	/*
	 * functions for sentiment words
	 */

	public void importBasicSentimentWordsFromFile(String fInName) throws IOException {
		// PRE: -
		// POST: Read in and store basic sentiment words in appropriate data type
		try (BufferedReader br = new BufferedReader(new FileReader(fInName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitTxt = line.split(" ");
				sentimentWords.put(splitTxt[0], splitTxt[1]);
			}
			importBasicSentiment = true;
		}
	}

	public void importFinegrainedSentimentWordsFromFile(String fInName) throws IOException {
		// PRE: -
		// POST: Read in and store finegrained sentiment words in appropriate data type

		try (BufferedReader br = new BufferedReader(new FileReader(fInName))) {
			String line;
			while ((line = br.readLine()) != null) {

				String[] SplitTxt = line.split(" ");

				String[] arr = new String[7];
				int counter = 0;

				for (String i : SplitTxt) {
					String[] s = i.split("=");
					arr[counter] = s[1];
					counter++;

				}

				FineGrained fine = new FineGrained(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);

				fineGrainedWords.put(arr[2], fine);

			}
			importFinegrained = true;
		}

	}

	public Boolean isBasicSentWord(String w) {
		// PRE: Basic sentiment words have been read in and stored
		// POST: Returns true if w is a basic sentiment word, false otherwise
		if (importBasicSentiment) {
			if (sentimentWords.containsKey(w)) {
				return true;
			}
		}
		// TODO

		return false;
	}

	public Boolean isFinegrainedSentWord(String w) {
		// PRE: Finegrained sentiment words have been read in and stored
		// POST: Returns true if w is a finegrained sentiment word, false otherwise
		if (importFinegrained) {
			if (fineGrainedWords.containsValue(w)) {
				return true;
			}
		}
		// TODO

		return false;
	}

	public void predictTweetSentimentFromBasicWordlist() {
		// PRE: Finegrained word sentiment already imported
		// POST: For all tweets in collection, tweet annotated with predicted sentiment
		// based on count of sentiment words in sentWords
		for (Map.Entry<String, Tweet> e : graph.entrySet()) {

			Tweet tweet = e.getValue();
			String[] text = tweet.getWords();
			int pos = 0, neg = 0;

			for (int i = 0; i < text.length; i++) {

				if (sentimentWords.containsKey(text[i])) {
					if (sentimentWords.get(text[i]).contains("positive")) {
						pos++;
					} else if (sentimentWords.get(text[i]).contains("negative")) {
						neg++;
					}
				}

			}

			if (pos > neg) {
				tweet.setPredictedPolarity(Polarity.POS);

			} else if (pos < neg) {
				tweet.setPredictedPolarity(Polarity.NEG);

			} else if (pos == neg && pos > 0) {
				tweet.setPredictedPolarity(Polarity.NEUT);

			} else
				tweet.setPredictedPolarity(Polarity.NONE);

		}

		// TODO
	}

	public void predictTweetSentimentFromFinegrainedWordlist(Integer strongWeight, Integer weakWeight) {
		// PRE: Finegrained word sentiment already imported
		// POST: For all tweets in v, tweet annotated with predicted sentiment
		// based on count of sentiment words in sentWords

		if (importFinegrained) {

			for (Map.Entry<String, Tweet> e : graph.entrySet()) {

				Tweet tweet = e.getValue();
				String[] text = tweet.getWords();

				int pos = 0, neg = 0;

				for (int i = 0; i < text.length; i++) {
					if (fineGrainedWords.containsKey(text[i])) {
						FineGrained getFine = fineGrainedWords.get(text[i]);

						if (getFine.type.equals("strongsubj") && getFine.polarity.equals("positive")) {

							pos += strongWeight;

						}

						else if (getFine.type.equals("weaksubj") && getFine.polarity.equals("positive")) {
							pos += weakWeight;

						} else if (getFine.type.equals("strongsubj") && getFine.polarity.equals("negative")) {
							neg += strongWeight;
						} else if (getFine.type.equals("weaksubj") && getFine.polarity.equals("negative")) {
							neg += weakWeight;
						}
					}

				}

				if (pos > neg) {
					tweet.setPredictedPolarity(Polarity.POS);

				} else if (pos < neg) {
					tweet.setPredictedPolarity(Polarity.NEG);

				} else if (pos == neg && pos > 0) {
					tweet.setPredictedPolarity(Polarity.NEUT);

				} else {
					tweet.setPredictedPolarity(Polarity.NONE);
				}

			}

		}

		// TODO
	}

	/*
	 * functions for inverse index
	 * 
	 */

	public Map<String, Vector<String>> importInverseIndexFromFile(String fInName) throws IOException {
		// PRE: -
		// POST: Read in and returned contents of file as inverse index
		// invIndex has words w as key, IDs of tweets that contain w as value

		try (BufferedReader br = new BufferedReader(new FileReader(fInName))) {
			String line;

			Map<String, Vector<String>> invIndex = new HashMap<String, Vector<String>>();

			while ((line = br.readLine()) != null) {
				String[] splitTxt = line.split(" ");
				String[] idsplit = splitTxt[1].split(",");
				Vector<String> list = new Vector<>();

				for (int i = 0; i < idsplit.length; i++) {
					list.add(idsplit[i]);
				}
				invIndex.put(splitTxt[0], list);

			}
			return invIndex;
		}

	}

	/*
	 * functions for graph construction
	 */

	public void constructSharedWordGraph(Map<String, Vector<String>> invIndex) {

		// PRE: invIndex has words w as key, IDs of tweets that contain w as value
		// POST: Graph constructed, with tweets as vertices,
		// and edges between them if they share a word
		for (Map.Entry<String, Vector<String>> e : invIndex.entrySet()) {
			if (e.getValue().size() > 1) {
				for (String id : e.getValue()) {
					if (graph.containsKey(id)) {
						Tweet tweet = graph.get(id);
						for (String neighbour : e.getValue()) {
							if (id != neighbour && graph.containsKey(neighbour) && !tweet.isNeighbour(neighbour)) {
								tweet.addNeighbour(neighbour);
							}

						}

					}
				}

			}

		}

	}

	public Integer numConnectedComponents() {
		// PRE: -
		// POST: Returns the number of connected components
		Integer count = 0;
		for (Tweet tweet : graph.values()) {
			count = count + tweet.numNeighbours();
		}

		return count;
	}

	public void annotateConnectedComponents() {
		// PRE: -
		// POST: Annotates graph so that it is partitioned into components

		for (Tweet tweet : graph.values()) {
			if (!tweet.getMarked()) {
				dfs(tweet);
			}

		}

	}

	public void dfs(Tweet tweet) {
		tweet.setMarked(true);
		count = count + 1;
		tweet.setNum(count);

		Iterator i = tweet.neighbourList.iterator();
		while (i.hasNext()) {
			Tweet neighbour = graph.get(i.next());
			if (!neighbour.getMarked()) {
				dfs(neighbour);
			}
		}

	}

	public Integer componentSentLabelCount(String ID, Polarity p) {
		// PRE: Graph components are identified, ID is a valid tweet
		// POST: Returns count of labels corresponding to Polarity p in component
		// containing ID

		if (!graph.containsKey(ID)) {
			return null;
		}
		Integer count = 0;

		Tweet tweet = graph.get(ID);
		if (tweet.getPredictedPolarity() == p) {
			count++;
		}

		Iterator i = tweet.neighbourList.iterator();
		while (i.hasNext()) {
			Tweet neighbour = graph.get(i.next());
			if (p == neighbour.getPredictedPolarity()) {
				count++;
			}

		}

		return count;

	}

	public void propagateLabelAcrossComponent(String ID, Polarity p, Boolean keepPred) {
		// PRE: ID is a tweet id in the graph
		// POST: Labels tweets in component with predicted polarity p
		// (if keepPred == T, only tweets w pred polarity None; otherwise all tweets
		if (graph.containsKey(ID)) {

			Tweet tweet = graph.get(ID);

			if (!keepPred) {
				tweet.setPredictedPolarity(p);

				Iterator i = tweet.neighbourList.iterator();
				while (i.hasNext()) {
					Tweet neighbour = graph.get(i.next());
					neighbour.setPredictedPolarity(p);

				}

			} else {
				if (tweet.getPredictedPolarity().equals(Polarity.NONE)) {
					tweet.setPredictedPolarity(p);
				}
				Iterator i = tweet.neighbourList.iterator();
				while (i.hasNext()) {
					Tweet neighbour = graph.get(i.next());
					if (neighbour.getPredictedPolarity() == Polarity.NONE) {
						neighbour.setPredictedPolarity(p);

					}

				}

			}

		}

	}

	public void propagateMajorityLabelAcrossComponents(Boolean keepPred) {
		// PRE: Components are identified
		// POST: Tweets in each component are labelled with the majority sentiment for
		// that component
		// Majority label is defined as whichever of POS or NEG has the larger count;
		// if POS and NEG are both zero, majority label is NONE
		// otherwise, majority label is NEUT
		// If keepPred is True, only tweets with predicted label None are labelled in
		// this way
		// otherwise, all tweets in the component are labelled in this way

		for (Tweet tweet : graph.values()) {
			int pos = 0, neg = 0;

			if (keepPred) {

				if (tweet.getPredictedPolarity() == Polarity.NONE) {

					Iterator i = tweet.neighbourList.iterator();
					while (i.hasNext()) {
						Tweet neighbour = graph.get(i.next());
						switch (neighbour.getPredictedPolarity()) {

						case POS:
							pos++;

							break;
						case NEG:
							neg++;
							break;

						}

					}
					if (pos > neg) {
						tweet.setPredictedPolarity(Polarity.POS);
					} else if (pos < neg) {
						tweet.setPredictedPolarity(Polarity.NEG);
					} else if (pos == neg && pos > 0) {
						tweet.setPredictedPolarity(Polarity.NEUT);
					}

				}

			} else {

				switch (tweet.getPredictedPolarity()) {

				case POS:
					pos++;

					break;
				case NEG:
					neg++;
					break;

				}

				Iterator i = tweet.neighbourList.iterator();
				while (i.hasNext()) {
					Tweet neighbour = graph.get(i.next());
					switch (neighbour.getPredictedPolarity()) {

					case POS:
						pos++;

						break;
					case NEG:
						neg++;
						break;

					}

				}
				Polarity Final = Polarity.NONE;

				if (pos > neg) {
					Final = Polarity.POS;

				} else if (pos < neg) {
					Final = Polarity.NEG;
				} else if (pos == neg && pos > 0) {
					Final = Polarity.NEUT;
				}

				tweet.setPredictedPolarity(Final);
				Iterator j = tweet.neighbourList.iterator();
				while (j.hasNext()) {
					Tweet neighbour = graph.get(j.next());
					neighbour.setPredictedPolarity(Final);
				}

			}

		}

	}

	/*
	 * functions for evaluation
	 */

	public Double accuracy() {
		// PRE: -
		// POST: Calculates and returns accuracy of labelling

		int size = 0;
		int count = 0;

		for (Map.Entry<String, Tweet> e : graph.entrySet()) {

			tweet = e.getValue();
			if (tweet.getPredictedPolarity() != Polarity.NONE) {
				size++;

				if (tweet.getGoldPolarity().equals(tweet.getPredictedPolarity())) {
					count++;
				}
			}
		}

		return (double) count / size;
	}

	public Double coverage() {
		// PRE: -
		// POST: Calculates and returns coverage of labelling
		int size = graph.size();
		int count = 0;

		for (Map.Entry<String, Tweet> e : graph.entrySet()) {

			tweet = e.getValue();
			if (tweet.getPredictedPolarity() != Polarity.NONE) {
				count++;
			}
		}
		return (double) count / size;
	}

	public static void main(String[] args) {

	}

}
