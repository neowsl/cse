import java.lang.reflect.*;
import java.util.*;
import java.io.*;

public class OrcaTrainer2 {
	// GA parameters
	static final int POP_SIZE = 60; // larger population
	static final int NUM_GENERATIONS = 100;
	static final double INIT_MUTATION_RATE = 0.3;
	static final double INIT_MUTATION_STD = 0.3;
	static final int NUM_ACTIONS = 4;
	static final int NUM_FEATURES = 24;
	static final int RUNS_PER_GENOME = 4;

	static class Genome {
		double[][] w = new double[NUM_ACTIONS][NUM_FEATURES];
		double fitness;

		static Genome random() {
			Genome g = new Genome();
			Random r = new Random();
			for (int i = 0; i < NUM_ACTIONS; i++)
				for (int j = 0; j < NUM_FEATURES; j++)
					g.w[i][j] = r.nextGaussian() * 0.2;
			return g;
		}

		Genome copy() {
			Genome g = new Genome();
			for (int i = 0; i < NUM_ACTIONS; i++)
				g.w[i] = Arrays.copyOf(this.w[i], NUM_FEATURES);
			g.fitness = this.fitness;
			return g;
		}
	}

	public static void main(String[] args) throws Exception {
		Genome pretrained = new Genome();
		pretrained.w = NealORCA.WEIGHTS;

		List<Genome> pop = new ArrayList<>();
		for (int i = 0; i < POP_SIZE / 2; i++)
			pop.add(pretrained.copy());
		for (int i = POP_SIZE / 2; i < POP_SIZE; i++)
			pop.add(Genome.random());

		Random rand = new Random();

		for (int gen = 0; gen < NUM_GENERATIONS; gen++) {
			System.out.println("Generation " + gen);

			// ---- Adaptive mutation for this generation ----
			double mutationRate = INIT_MUTATION_RATE * (1.0 - gen / (double) NUM_GENERATIONS);
			double mutationStd = INIT_MUTATION_STD * (1.0 - gen / (double) NUM_GENERATIONS);

			// ---- Evaluate ----
			for (Genome g : pop)
				g.fitness = evaluate(g);

			pop.sort((a, b) -> Double.compare(b.fitness, a.fitness));
			System.out.printf("Best fitness: %.2f\n", pop.get(0).fitness);

			// ---- Selection: top 20% + diversity ----
			List<Genome> next = new ArrayList<>();
			int survivors = POP_SIZE / 5;
			next.addAll(pop.subList(0, survivors));

			// Maintain some random genomes for diversity
			while (next.size() < survivors + 5)
				next.add(Genome.random());

			// ---- Reproduce until population full ----
			while (next.size() < POP_SIZE) {
				Genome p1 = pop.get(rand.nextInt(survivors));
				Genome p2 = pop.get(rand.nextInt(survivors));
				Genome child = crossover(p1, p2);
				mutate(child, mutationRate, mutationStd);
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

		try (PrintWriter writer = new PrintWriter(new File("best_weights.txt"))) {
			writer.println("public static double[][] WEIGHTS = {");
			for (int i = 0; i < best.w.length; i++) {
				writer.print("    {");
				for (int j = 0; j < best.w[i].length; j++) {
					writer.print(best.w[i][j]);
					if (j < best.w[i].length - 1)
						writer.print(", ");
				}
				writer.print("}");
				if (i < best.w.length - 1)
					writer.println(",");
				else
					writer.println();
			}
			writer.println("};");
		}

		System.out.println("Weights saved to best_weights.txt");
	}

	static Genome crossover(Genome a, Genome b) {
		Genome c = new Genome();
		Random r = new Random();
		for (int i = 0; i < NUM_ACTIONS; i++)
			for (int j = 0; j < NUM_FEATURES; j++)
				c.w[i][j] = r.nextBoolean() ? a.w[i][j] : b.w[i][j];
		return c;
	}

	static void mutate(Genome g, double rate, double std) {
		Random r = new Random();
		for (int i = 0; i < NUM_ACTIONS; i++)
			for (int j = 0; j < NUM_FEATURES; j++)
				if (r.nextDouble() < rate)
					g.w[i][j] += r.nextGaussian() * std;
	}

	static double evaluate(Genome g) throws Exception {
		Class<?> orcaClass = NealORCA.class;
		Field f = orcaClass.getDeclaredField("WEIGHTS");
		f.setAccessible(true);
		f.set(null, g.w);

		double totalFitness = 0;

		for (int run = 0; run < RUNS_PER_GENOME; run++) {
			CritterModel model = new CritterModel(65, 55);

			model.add(30, Bear.class);
			model.add(30, Lion.class);
			model.add(30, Giant.class);
			model.add(30, FlyTrap.class);
			model.add(60, Food.class);
			model.add(30, NealORCA.class);

			model.updateColorString();

			double fitness = 0;
			for (int step = 0; step < 2000; step++) { // slightly shorter per run
				model.update();

				for (var e : model.getCounts()) {
					String name = e.getKey();
					int count = e.getValue();
					if (name.contains("ORCA"))
						fitness += count;
				}
			}

			totalFitness += fitness / 3000.0;
		}

		return totalFitness / RUNS_PER_GENOME;
	}
}
