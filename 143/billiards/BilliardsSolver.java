import java.util.List;
import java.util.ArrayList;

public class BilliardsSolver {
	public static BilliardBall solve12_3(BilliardBallSet balls) {
		BilliardBallSet s1 = new BilliardBallSet(balls.getBalls(0, 4));
		BilliardBallSet s2 = new BilliardBallSet(balls.getBalls(4, 8));
		BilliardBallSet s3 = new BilliardBallSet(balls.getBalls(8, balls.size()));

		int c1 = s1.compareTo(s2);
		if (c1 == 0) {
			// fake must be in other 4
			return solve4_2(s3);
		}

		// make `s1` the lighter set
		if (c1 > 0) {
			BilliardBallSet tmp = s1;
			s1 = s2;
			s2 = tmp;
		}

		BilliardBallSet s4 = new BilliardBallSet();
		s4.add(s2.getBall(0));
		s4.add(s1.getBall(1));
		s4.add(s3.getBall(0));
		BilliardBallSet s5 = new BilliardBallSet();
		s5.add(s1.getBall(0));
		s5.add(s2.getBall(1));
		s5.add(s2.getBall(2));

		int c2 = s4.compareTo(s5);
		if (c2 > 0) {
			BilliardBallSet s6 = new BilliardBallSet(s1.getBall(0));
			BilliardBallSet s7 = new BilliardBallSet(s3.getBall(0));
			if (s6.compareTo(s7) == 0)
				return s2.getBall(0);
			return s1.getBall(0);
		}

		if (c2 == 0) {
			// removed balls
			BilliardBallSet s6 = new BilliardBallSet();
			s6.add(s1.getBall(2));
			s6.add(s1.getBall(3));
			s6.add(s2.getBall(3));
			return solve3_1(s6, -1);
		}

		// kept balls
		BilliardBallSet s6 = new BilliardBallSet();
		s6.add(s2.getBall(1));
		s6.add(s2.getBall(2));
		s6.add(s1.getBall(1));
		return solve3_1(s6, 1);
	}

	public static BilliardBall solve10_3(BilliardBallSet balls) {
		BilliardBallSet s1 = new BilliardBallSet(balls.getBalls(0, 3));
		BilliardBallSet s2 = new BilliardBallSet(balls.getBalls(3, 6));
		BilliardBallSet s3 = new BilliardBallSet(balls.getBalls(6, balls.size()));

		int c1 = s1.compareTo(s2);
		if (c1 == 0) {
			// fake must be in other 4
			return solve4_2(s3);
		}

		// make `s1` the lighter set
		if (c1 > 0) {
			BilliardBallSet tmp = s1;
			s1 = s2;
			s2 = tmp;
		}

		List<BilliardBall> l1 = new ArrayList<>(s1.getBalls(0, 2));
		l1.add(s2.getBall(2));
		// `s5` is the lighter set
		BilliardBallSet s5 = new BilliardBallSet(l1);

		List<BilliardBall> l2 = new ArrayList<>(s2.getBalls(0, 2));
		l2.add(s1.getBall(2));
		// `s6` is the heavier set
		BilliardBallSet s6 = new BilliardBallSet(l2);

		BilliardBallSet real = new BilliardBallSet(s3.getBalls(0, 3));

		if (s5.compareTo(real) == 0) {
			return solve3_1(s6, 1);
		}
		return solve3_1(s5, -1);
	}

	public static BilliardBall solve4_2(BilliardBallSet balls) {
		BilliardBallSet s1 = new BilliardBallSet(balls.getBall(0));
		BilliardBallSet s2 = new BilliardBallSet(balls.getBall(1));
		BilliardBallSet s3 = new BilliardBallSet(balls.getBall(2));

		if (s1.compareTo(s2) == 0) {
			if (s1.compareTo(s3) == 0) {
				// process of elimination
				return balls.getBall(3);
			}
			return balls.getBall(2);
		}

		if (s1.compareTo(s3) == 0) {
			return balls.getBall(1);
		}
		return balls.getBall(0);
	}

	/**
	 * @param direction negative for lighter fake ball,
	 *                  positive for heavier fake ball
	 */
	public static BilliardBall solve3_1(BilliardBallSet balls, int direction) {
		BilliardBallSet s1 = new BilliardBallSet(balls.getBall(0));
		BilliardBallSet s2 = new BilliardBallSet(balls.getBall(1));

		int c = s1.compareTo(s2);
		if (c == 0) {
			// process of elimination
			return balls.getBall(2);
		}

		// set must be in same direction as `direction`
		return (c * direction > 0 ? s1 : s2).getBall(0);
	}
}
