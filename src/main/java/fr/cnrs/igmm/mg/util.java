package fr.cnrs.igmm.mg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class util {
	 public static List<String> convertItemsToList(String line) {
		if ((line == null) || (line.length() == 0)) {
			// no mapper output will be generated
			return null;
		}
		String[] tokens = StringUtils.split(line, "\t");
		if ((tokens == null) || (tokens.length == 0)) {
			return null;
		}
		List<String> items = new ArrayList<String>();
		for (String token : tokens) {
			if (token != null) {
				items.add(token.trim());
			}
		}
		return items;
	}
}
