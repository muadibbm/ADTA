package main;

import java.util.Comparator;

public class StringListSort implements Comparator<String> {
	
	private static int PK_SIZE = 7;
	
	public int compare(String string1, String string2) {
		String a = string1.substring(0, PK_SIZE);
		String b = string2.substring(0, PK_SIZE);
		return Integer.parseInt(a) - Integer.parseInt(b);
	}

}
