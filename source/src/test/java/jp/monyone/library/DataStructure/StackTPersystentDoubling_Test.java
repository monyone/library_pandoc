package jp.monyone.library.DataStructure;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static jp.monyone.library.DataStructure.StackTPersystentDoubling_Include.Stack;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StackTPersystentDoubling_Test {

	@Test(timeout=1000) public void test_size(){
		long[] array = new long[10];
		for(int i = 0; i < array.length; i++){
			array[i] = i;
		}

		Stack<Long> stack = new Stack<Long>();
		for(int i = 0; i < array.length; i++){
			stack = stack.push(array[i]);
		}

		for(int i = 0; i < array.length; i++){
			assertThat(stack.popN(i).size(), is(array.length - i));
		}
	}

	@Test(timeout=1000) public void test_compare_array(){
		long[] array = new long[100000];
		for(int i = 0; i < array.length; i++){
			array[i] = i * i;
		}

		Stack<Long> stack = new Stack<Long>();
		for(int i = 0; i < array.length; i++){
			stack = stack.push(array[i]);
		}

		for(int i = 0; i < array.length; i++){
			assertThat(stack.topN(i), is(array[array.length - i - 1]));
		}
	}
}

