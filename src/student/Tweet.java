package student;

import java.util.*;

enum Polarity {
	POS, NEG, NEUT, NONE;
}

enum Strength {
	STRONG, WEAK, NONE;
}

public class Tweet {

	// TODO: Add appropriate data types
	private Polarity goldPolarity;
	private Polarity predictedPolarity;
	private String goldPol;
	private String id;
	private String date;
	private String user;
	private String text;
	private boolean marked;
	private Integer num;

	Vector<String> neighbourList;

	public Tweet(String p, String i, String d, String u, String t) {

		switch (p) {
		case "0":
			goldPolarity = Polarity.NEG;

			break;
		case "2":
			goldPolarity = Polarity.NEUT;

			break;
		case "4":
			goldPolarity = Polarity.POS;
			break;
		case "_":
			goldPolarity = Polarity.NONE;
			break;
		}
		predictedPolarity = Polarity.NONE;
		neighbourList = new Vector<>();
		goldPol = p;
		id = i;
		date = d;
		user = u;
		text = t;

		marked = false;
		num = 0;

	}

	public void addNeighbour(String ID) {
		// PRE: -
		// POST: Adds a neighbour to the current tweet as part of graph structure
		neighbourList.add(ID);

		// TODO
	}

	public Integer numNeighbours() {
		// PRE: -
		// POST: Returns the number of neighbours of this tweet

		// TODO
		return neighbourList.size();

	}

	public boolean getMarked() {
		// PRE: -
		// POST: Returns the number of neighbours of this tweet

		// TODO
		return this.marked;
	}

	public void setMarked(boolean val) {
		this.marked = val;

	}

	public int num() {
		// PRE: -
		// POST: Returns the number of neighbours of this tweet

		// TODO
		return this.num;
	}

	public void setNum(int val) {
		this.num = val;

	}

	public void deleteAllNeighbours() {
		// PRE: -
		// POST: Deletes all neighbours of this tweet
		neighbourList.clear();

		// TODO
	}

	public Vector<String> getNeighbourTweetIDs() {
		// PRE: -
		// POST: Returns IDs of neighbouring tweets as vector of strings

		// TODO

		return neighbourList;
	}

	public Boolean isNeighbour(String ID) {
		// PRE: -
		// POST: Returns true if ID is neighbour of the current tweet, false otherwise

		// TODO
		if (neighbourList.contains(ID)) {
			return true;
		}

		return false;
	}

	public Boolean correctlyPredictedPolarity() {
		// PRE: -
		// POST: Returns true if predicted polarity matches gold, false otherwise
		if (predictedPolarity == goldPolarity) {
			return true;
		}
		// TODO

		return false;
	}

	public Polarity getGoldPolarity() {
		// PRE: -
		// POST: Returns the gold polarity of the tweet

		// TODO

		return goldPolarity;
	}

	public Polarity getPredictedPolarity() {
		// PRE: -
		// POST: Returns the predicted polarity of the tweet

		// TODO

		return predictedPolarity;
	}

	public void setPredictedPolarity(Polarity p) {
		// PRE: -
		// POST: Sets the predicted polarity of the tweet
		predictedPolarity = p;

		// TODO
	}

	public String getID() {
		// PRE: -
		// POST: Returns ID of tweet

		return id;
	}

	public String getDate() {
		// PRE: -
		// POST: Returns date of tweet

		return date;
	}

	public String getUser() {
		// PRE: -
		// POST: Returns identity of tweeter

		return user;
	}

	public String getText() {
		// PRE: -
		// POST: Returns text of tweet as a single string

		return text;
	}

	public String[] getWords() {
		// PRE: -
		// POST: Returns tokenised text of tweet as array of strings

		if (this.getText() == null)
			return null;

		String[] words = null;

		String tmod = this.getText();
		tmod = tmod.replaceAll("@.*?\\s", "");
		tmod = tmod.replaceAll("http.*?\\s", "");
		tmod = tmod.replaceAll("\\s+", " ");
		tmod = tmod.replaceAll("[\\W&&[^\\s]]+", "");
		tmod = tmod.toLowerCase();
		tmod = tmod.trim();
		words = tmod.split("\\s");

		return words;

	}

}
