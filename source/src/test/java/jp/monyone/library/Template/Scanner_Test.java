package jp.monyone.library.Template;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static jp.monyone.library.Template.Scanner_Include.Scanner;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Scanner_Test {

	public void tokenize_test(String ... strs){
		try(Scanner sc = new Scanner(new ByteArrayInputStream(String.join(" ", strs).getBytes()))) {
			for(final String str : strs){
				assertThat(sc.next(), is(str));
			}
		}
	}

	public void nextInt_test(int num){
		try(Scanner sc = new Scanner(new ByteArrayInputStream(Integer.toString(num).getBytes()))){
			assertThat(sc.nextInt(), is(num));
		}
	}

	@Test public void basic_usage() {
		tokenize_test("test");
		tokenize_test("test1", "test2", "test3");

		nextInt_test(1);
		nextInt_test(Integer.MAX_VALUE);
		nextInt_test(Integer.MIN_VALUE);
	}
}

