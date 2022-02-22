package student;

import java.util.*;

public class FineGrained {

	String type;
	String len;
	String word1;
	String pos1;
	String stemmed1;
	String polarity;
	String priorpolarity;

	public FineGrained(String t, String l, String w, String p, String s, String po, String pr) {
		type = t;
		len = l;
		word1 = w;
		pos1 = p;
		stemmed1 = s;
		polarity=po;
		priorpolarity = pr;

	}

}
