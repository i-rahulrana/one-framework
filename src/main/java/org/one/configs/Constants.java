package org.one.configs;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	private static final Map<String, Integer> states = new HashMap<> ();

	public static Map<String, Integer> getMalaysiaStates () {
		states.put ("Johor", 1);
		states.put("Kedah", 2);
		states.put("Kelantan", 3);
		states.put("Kuala Lumpur", 4);
		states.put("Labuan", 5);
		states.put("Melaka", 6);
		states.put("Pahang", 7);
		states.put("Perils", 8);
		states.put("Perak", 9);
		states.put("Penang", 10);
		states.put("Putrajaya", 11);
		states.put("Sabah", 12);
		states.put("Sarawak", 13);
		states.put("Selangor", 14);
		states.put("Negeri Sembilan", 15);
		states.put("Terangganu", 16);
		return states;
	}
}
