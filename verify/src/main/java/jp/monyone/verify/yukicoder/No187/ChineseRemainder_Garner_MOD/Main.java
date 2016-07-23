package jp.monyone.verify.yukicoder.No187.ChineseRemainder_Garner_MOD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static long gcd(long a, long b){
		return b == 0 ? a : gcd(b, a % b);
	}
	public static long lcm(long a, long b){
		return a / gcd(a, b) * b;
	}
	
	// O(N^2) より上で適当な、中国剰余定理で互いに素にする関数
	public static boolean make_coprime_sequence(long[] as, long[] ms){
		while(true) {
			boolean updated = false;

			for (int fst = 0; fst < ms.length; fst++) {
				for (int snd = fst + 1; snd < ms.length; snd++) {
					long gcd = gcd(ms[fst], ms[snd]);
					if (gcd == 1) { continue; }

					updated = true;

					if (as[fst] % gcd != as[snd] % gcd) { return false; }

					ms[fst] /= gcd;
					ms[snd] /= gcd;
					while (true) {
						long gt = gcd(ms[fst], gcd);
						if (gt == 1) { break; }

						ms[fst] *= gt;
						gcd /= gt;
					}
					ms[snd] *= gcd;
					as[fst] %= ms[fst];
					as[snd] %= ms[snd];
				}
			}

			if(!updated){ break; }
		}

		return true;
	}

	public static long mod_pow(long a, long e, long m){
		if(e == 0){
			return 1;
		}else if(e % 2 == 0){
			long ret = mod_pow(a, e / 2, m);
			return (ret * ret) % m;
		}else{
			return (mod_pow(a, e - 1, m) * a) % m;
		}
	}

	// a and m must be co-prime.
	public static long mod_inv(long a, long m){
		return (a == 1 ? 1 : (1 - m*mod_inv(m%a, a)) / a + m);
	}

	// O(N^2). ms[i] は互いに素でなくてはならない.
	// Π(ms[i]) がオーバーフローする場合でも, 最後に適切に MOD を取れば OK
	public static long chinese_remainder(long[] as, long[] ms, long MOD){
		long[] vs = new long[ms.length];
		long[] sums = new long[ms.length];
		long[] invs = new long[ms.length];

		for(int i = 0; i < ms.length; i++){
			invs[i] = 1; // mod(ms[i]) の中で 1 / (ms[0] * ms[1] ... ms[i - 1]) を求める
			sums[i] = as[i]; // mod(ms[i]) の中で, a[i] - v[0] - v[1]ms[0] ... を求める

			long mult = 1; // mod(ms[i]) の中で, ms[0] * ... * ms[i - 2] を求める
			for(int j = 0; j < i; j++){
				sums[i] -= (vs[j] * mult) % ms[i];
				if(sums[i] < 0){ sums[i] += ms[i]; }

				invs[i] = (invs[i] * mod_inv(ms[j], ms[i])) % ms[i];
				mult = (mult * ms[j]) % ms[i];
			}

			vs[i] = (sums[i] * invs[i]) % ms[i];
		}

		// MOD を取る前に x = 0 かどうかを知る場合, ここで ∀i.(vs[i] == 0) チェック
		{
			boolean flg = true;
			for (final long v : vs) {
				if (v != 0) {
					flg = false;
					break;
				}
			}
			if (flg) { // ここに来るなら 全ての条件を x = 0 で満たす.
				return 0; // 正整数が欲しいなら、Π(ms[i]) の MOD が答え
			}
		}

		long ret = 0;
		for(int i = 0; i < ms.length; i++){
			long mult = 1;
			for(int j = 0; j < i; j++){
				mult *= ms[j]; mult %= MOD; // ここも MOD
			}

			ret += (vs[i] * mult) % MOD; ret %= MOD; // ここも MOD
		}

		return ret;
	}

	public static void main(String[] args) throws IOException {
		try (Scanner sc = new Scanner(System.in)) {
        		final int N = sc.nextInt();
        		long[] as = new long[N];
        		long[] ms = new long[N];
        		for (int i = 0; i < N; i++){
        			as[i] = sc.nextInt();
        			ms[i] = sc.nextInt();
        		}

        		final long MOD = 1000000007L;

        		final boolean is_coprime = make_coprime_sequence(as, ms);
        		if(!is_coprime){
        			System.out.println(-1);
        			return;
        		}else{ // すべての as が 0 mod ms の場合は 0 が答えになるが問題に合うように正整数にする
				boolean all_zero = true;
				for(final long a : as){
					if(a != 0){ all_zero = false; break;  }
				}
				
				if(all_zero){
		        		long prod = 1;
        				for(final long m : ms){
        					prod *= m;
        					prod %= MOD;
        				}
					
					System.out.println(prod);

					return;
				}
			}
			
			
        		final long ret = chinese_remainder(as, ms, MOD);
        		System.out.println(ret);
        	}
	}
	
	public static class Scanner implements AutoCloseable {
		private BufferedReader br;
		private StringTokenizer tok;

		public Scanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		private void getLine() {
			try {
				while (!hasNext()) {tok = new StringTokenizer(br.readLine());}
			} catch(IOException e){ /* ignore */ }
		}

		private boolean hasNext() {
			return tok != null && tok.hasMoreTokens();
		}

		public String next() {
			getLine(); return tok.nextToken();
		}

		public int nextInt(){
			return Integer.parseInt(next());
		}
		// 他のnextXXXもXXX.parseXXX()メソッドを使って作れるので省略

		public void close() {
			try{ br.close(); } catch (IOException e){ /*ignore*/ }
		}
	}
	
}
