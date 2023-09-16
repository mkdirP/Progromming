import java.math.*;

public class App {

	public static void main(String[] args) {
		long[] a = new long[9];

		int start = 23;
		for (int i = 0; i < a.length; i++) {
			a[i] = start - i * 2;
		}
		double[] x = new double[19];
		start = -6;
		for (int i = 0; i < x.length; i++) {
			x[i] = (double) (-6.0 + Math.random() * (12.0 - (-6.0) + 1));
		}

		double[][] b = new double[9][19];

		for (int i = 0; i < 9; i++) {

			if (a[i] == 9) {
				for (int j = 0; j < 19; j++) {
					b[i][j] = Math.asin(1 / Math.exp(Math.pow(Math.sin(Math.sin(Math.pow(Math.PI * x[j], 3))), 2)));
				}
			}

			if (a[i] == 7 || a[i] == 11 || a[i] == 17 || a[i] == 21) {
				for (int j = 0; j < 19; j++) {
					b[i][j] = Math.pow(Math.pow(Math.pow(((1.0 / 4) + x[j]) / x[j], 2), Math.pow((x[j]), 2)),
							3 / Math.exp(Math.tan(x[j])));
				}

			}
			if (a[i] == 15 || a[i] == 19 || a[i] == 13 || a[i] == 23) {
				for (int j = 0; j < 19; j++) {
					b[i][j] = Math.log(Math.pow(Math.tan(Math.sin(Math.asin(0.5 * (x[j] + 3) / 18))), 2));
				}
			}
		}

		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 19; j++) {

				System.out.printf("%.3f\t", b[i][j]);
			}
			System.out.println();
		}
	}
}