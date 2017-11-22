package jp.monyone.library.Template;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static jp.monyone.library.Template.Scanner_Include.Scanner;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Scanner_Test {

	public void one_space_test(String ... strs){
		try(Scanner sc = new Scanner(new ByteArrayInputStream(String.join(" ", strs).getBytes()))) {
			for(final String str : strs){
				assertThat(sc.next(), is(str));
			}
		}
	}

	public void two_space_test(String ... strs){
		try(Scanner sc = new Scanner(new ByteArrayInputStream(String.join("  ", strs).getBytes()))) {
			for(final String str : strs){
				assertThat(sc.next(), is(str));
			}
		}
	}

	public void break_line_test(String ... strs){
		try(Scanner sc = new Scanner(new ByteArrayInputStream(String.join("\n", strs).getBytes()))) {
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

	// とりあえず C0 カバレッジは 100% になるように
	@Test public void basic_usage() {
		one_space_test("test");
		one_space_test("test1", "test2", "test3");

		two_space_test("test");
		two_space_test("test1", "test2", "test3");

		break_line_test("test");
		break_line_test("test1", "test2", "test3");

		nextInt_test(1);
		nextInt_test(Integer.MAX_VALUE);
		nextInt_test(Integer.MIN_VALUE);
	}
}

