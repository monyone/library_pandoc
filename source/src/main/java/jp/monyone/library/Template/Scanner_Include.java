package jp.monyone.library.Template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Scanner_Include {
	//@start
	public static class Scanner {
		private BufferedReader br;
		private StringTokenizer tok;

		public Scanner(InputStream is) throws IOException {
			br = new BufferedReader(new InputStreamReader(is));
		}

		private void getLine() throws IOException {
			while (!hasNext()) { tok = new StringTokenizer(br.readLine()); }
		}

		private boolean hasNext() {
			return tok != null && tok.hasMoreTokens();
		}

		public String next() throws IOException {
			getLine(); return tok.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
		// 他のnextXXXもXXX.parseXXX()メソッドを使って作れるので省略

		public void close() throws IOException {
			br.close();
		}
	}
	//@end
}
