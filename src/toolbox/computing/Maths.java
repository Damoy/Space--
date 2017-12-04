package toolbox.computing;

import java.util.Random;

public class Maths {

	public static Random random = new Random();

	public static int irand(int max) {
		return random.nextInt(max);
	}

	public static float frand(float max) {
		return random.nextFloat() * max;
	}

	public static boolean brand() {
		return random.nextBoolean();
	}

	public static double drand(double d) {
		return random.nextDouble() * d;
	}

	public static void randomize() {
		random = new Random();
	}

	public static double log(double nb, int base) {
		return Math.log10(nb) / Math.log10(base);
	}

	public static int log2(int nb) {
		return (int) log(nb, 2);
	}

}
