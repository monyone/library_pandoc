package jp.monyone.library.Puzzle;

public class Dice_Include {
	//@start
	public static class Dice {
		public static int MAX = 5, SIZE = 7;
		public static int TOP = 0, FRONT = 1, RIGHT = 2;
		public static int BOTTOM = 5, BACK = 4, LEFT = 3;

		int[] dice; // in [1, 2, 3, 4, 5, 6]

		public Dice(int top, int front, int right, int sum) {
			dice = new int[MAX + 1];
			dice[TOP] = top; dice[FRONT] = front; dice[RIGHT] = right;
			dice[BOTTOM] = sum - top; dice[BACK] = sum - front; dice[LEFT] = sum - right;
		}

		public Dice(int top, int front, int right) {
			this(top, front, right, SIZE);
		}

		// rotate_from は (top | front) から見てどの方向に転がるを指定すると転がしてくれる.
		public void rotate_from_top(final int dir) { rotate_dice(TOP, dir, BOTTOM, MAX - dir); }
		public void rotate_from_front(final int dir){ rotate_dice(FRONT, dir, BACK, MAX - dir); }

		public void rotate_dice(int... args) {
			final int tmp = dice[args[args.length - 1]];
			for (int now = args.length - 1; now > 0; now--) {
				dice[args[now]] = dice[args[now - 1]];
			}

			dice[args[0]] = tmp;
		}
	}
	//@end
}
