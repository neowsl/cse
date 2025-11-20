import java.lang.reflect.*;
import java.util.*;

public class OrcaTrainer {
	// GA parameters
	static final int POP_SIZE = 40;
	static final int NUM_GENERATIONS = 10;
	static final double MUTATION_RATE = 0.15;
	static final double MUTATION_STD = 0.20;
	static final int NUM_ACTIONS = 4;
	static final int NUM_FEATURES = 24;

	static class Genome {
		double[][] w = new double[NUM_ACTIONS][NUM_FEATURES];
		double fitness;

		static Genome random() {
			Genome g = new Genome();
			Random r = new Random();
			for (int i = 0; i < NUM_ACTIONS; i++) {
				for (int j = 0; j < NUM_FEATURES; j++) {
					g.w[i][j] = r.nextGaussian() * 0.2;
				}
			}
			return g;
		}

		Genome copy() {
			Genome g = new Genome();
			for (int i = 0; i < NUM_ACTIONS; i++) {
				g.w[i] = Arrays.copyOf(this.w[i], NUM_FEATURES);
			}
			g.fitness = this.fitness;
			return g;
		}
	}

	public static void main(String[] args) throws Exception {
		Genome pretrained = new Genome();
		pretrained.w = ORCA.WEIGHTS;

		List<Genome> pop = new ArrayList<>();
		for (int i = 0; i < POP_SIZE / 2; i++) {
			pop.add(pretrained.copy());
		}
		for (int i = POP_SIZE / 2; i < POP_SIZE; i++) {
			pop.add(Genome.random());
		}

		for (int gen = 0; gen < NUM_GENERATIONS; gen++) {
			System.out.println("Generation " + gen);

			// ---- Evaluate ----
			for (Genome g : pop) {
				g.fitness = evaluate(g);
			}

			pop.sort((a, b) -> Double.compare(b.fitness, a.fitness));
			System.out.printf("Best fitness: %.2f\n", pop.get(0).fitness);

			// ---- Selection (top 20%) ----
			List<Genome> next = new ArrayList<>();
			int survivors = POP_SIZE / 5;
			next.addAll(pop.subList(0, survivors));

			Random r = new Random();

			// ---- Reproduce until population full ----
			while (next.size() < POP_SIZE) {
				Genome p1 = pop.get(r.nextInt(survivors));
				Genome p2 = pop.get(r.nextInt(survivors));
				Genome child = crossover(p1, p2);
				mutate(child);
				next.add(child);
			}

			pop = next;
		}

		// Final best individual
		pop.sort((a, b) -> Double.compare(b.fitness, a.fitness));
		Genome best = pop.get(0);

		System.out.println("\n=== FINAL BEST WEIGHTS ===");
		for (double[] row : best.w) {
			System.out.println(Arrays.toString(row));
		}
	}

	static Genome crossover(Genome a, Genome b) {
		Genome c = new Genome();
		Random r = new Random();
		for (int i = 0; i < NUM_ACTIONS; i++) {
			for (int j = 0; j < NUM_FEATURES; j++) {
				c.w[i][j] = r.nextBoolean() ? a.w[i][j] : b.w[i][j];
			}
		}
		return c;
	}

	static void mutate(Genome g) {
		Random r = new Random();
		for (int i = 0; i < NUM_ACTIONS; i++) {
			for (int j = 0; j < NUM_FEATURES; j++) {
				if (r.nextDouble() < MUTATION_RATE) {
					g.w[i][j] += r.nextGaussian() * MUTATION_STD;
				}
			}
		}
	}

	static double evaluate(Genome g) throws Exception {
		Class<?> orcaClass = ORCA.class;
		Field f = orcaClass.getDeclaredField("WEIGHTS");
		f.setAccessible(true);
		f.set(null, g.w);

		// CritterModel model = new CritterModel(20, 20); // smaller world
		// model.add(5, Bear.class);
		// model.add(5, Lion.class);
		// model.add(5, Giant.class);
		// model.add(5, FlyTrap.class);
		// model.add(20, Food.class);
		// model.add(10, ORCA.class);

		CritterModel model = new CritterModel(65, 55);

		model.add(30, Bear.class);
		model.add(30, Lion.class);
		model.add(30, Giant.class);
		model.add(30, FlyTrap.class);
		model.add(60, Food.class);

		model.add(30, ORCA.class);

		model.updateColorString();

		double fitness = 0;
		for (int step = 0; step < 5000; step++) {
			model.update();

			for (var e : model.getCounts()) {
				if (e.getKey().contains("ORCA")) {
					fitness += e.getValue();
				}
			}
		}

		int finalCount = 0;
		for (var e : model.getCounts()) {
			if (e.getKey().contains("ORCA"))
				finalCount = e.getValue();
		}
		fitness += 5 * finalCount;

		return fitness;
	}
}
