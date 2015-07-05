package jp.monyone.library.Graph;

import static jp.monyone.library.Graph.MinimumSteinerTreeDreyfusWagner_Include.minimumSteinerTree;
import static jp.monyone.library.Template.LongINF_Include.INF;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class MinimumSteinerTreeDreyfusWagner_Test {
	
	@Test public void check(){ // ただの確認用
		long[][] adj = {
				{  0,   4, INF, INF, INF, INF},
				{  4,   0,   3, INF, INF, INF},
				{INF,   3,   0,   6,   5, INF},
				{INF, INF,   6,   0, INF, INF},
				{INF, INF,   5, INF,   0,   2},
				{INF, INF, INF, INF,   2,   0}
		};
		int[] ts = {1, 3, 4};

		assertThat(minimumSteinerTree(adj, ts), is(14L));
	}
}

