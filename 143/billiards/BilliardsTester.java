import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class BilliardsTester {
	public static int NUM_BALLS = 12;

	public static void main(String[] args) {
		Random r = new Random();

		for (int i = 0; i < NUM_BALLS; i++) {
			// make fake ball at index `i`
			System.out.println("Testing i = " + i);

			Globals.resetWeighings();

			int fakeWeight, realWeight;
			do {
				fakeWeight = r.nextInt(100);
				realWeight = r.nextInt(100);
			} while (fakeWeight == realWeight);

			List<BilliardBall> l = new ArrayList<>();
			for (int j = 0; j < NUM_BALLS; j++) {
				l.add(new BilliardBall(j == i ? fakeWeight : realWeight));
			}
			BilliardBallSet balls = new BilliardBallSet(l);

			BilliardBall fake = BilliardsSolver.solve12_3(balls);
			assert fake.getWeight() == fakeWeight : "Incorrect solution";
		}

		System.out.println("Success!");
	}
}
