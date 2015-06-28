package jp.monyone.library.Graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdjList_Include {
	//@start
	public static ArrayList<Map<Integer, Long>> init_adj(final int n){
		ArrayList<Map<Integer, Long>> ret = new ArrayList<Map<Integer, Long>>();
		for(int i = 0; i < n; i++){
			ret.add(new LinkedHashMap<Integer, Long>());
		}
		return ret;
	}
	//@end
}
