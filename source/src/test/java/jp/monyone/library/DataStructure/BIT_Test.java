package jp.monyone.library.DataStructure;

import static org.junit.Assert.*;
import org.junit.Test;

import jp.monyone.library.DataStructure.BIT_Include.BIT;

public class BIT_Test{
	
	private static BIT prepare_BIT(int ... inputs){
		final int n = inputs.length;
		
		BIT bit = new BIT(n);
		for(int i = 0; i < n; i++){
			bit.add(i, inputs[i]);
		}
		return bit;
	}
	
	private static int naive(int s, int t, int ... inputs){
		int sum = 0;
		for(int i = s; i < t; i++){
			sum += inputs[i];
		}
		return sum;
	}
	
	@Test public void test1(){
		int[] inputs = new int[]{1, 2, 3};
		BIT bit = prepare_BIT(inputs);
		
		final int expected = naive(0, 1, inputs);
		final int actual = bit.sum(0, 1);
		
		assertEquals(expected, actual);
	}
}

