package jp.monyone.library.String;

public class ShiftAnd_Include{
	//@start
	//Mのbit幅 >= パターンの文字列じゃないと死ぬ. 長いならBitSetを使おう.
	public static int shift_and(String t, String p){ //pの長さはbit幅依存
		int[] M = new int[Character.MAX_VALUE]; // alphabet全体分の長さが必要. 
		int count = 0;
		for(int i = 0; i < p.length(); i++){
			M[p.charAt(i)] |= (1 << i);
		}
		
		for(int i = 0, S = 0; i < t.length(); i++){
			S = ((S << 1) | 1) & M[t.charAt(i)];
			
			if((S & (1 << (p.length() - 1))) != 0){
				count++; // t[i - p.length() + 1, i] 
			}
		}
		
		return count;
	}
	//@end
}
