package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.*;

/**
 * Class that calculates and draws a fractal of given polynomial based on Newton-Raphson iteration.
 * User inputs complex roots of the polynomial that should be drawn. 
 * Program makes a polynomial of those roots and produces its fractal.
 * @author Alex
 *
 */
public class Newton {

	/**
	 * Main method that does previously described work.
	 * At the beginning of program, user needs to input complex roots.
	 * @param args not needed here.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new ArrayList<>();
		
		
		System.out.print("Root 1> ");
		String line = sc.nextLine();		
		int counter = 2;

		while(!line.equals("done")) {
			int breakpoint = line.lastIndexOf("-");
			
			if(breakpoint == -1 || breakpoint == 0) {
				breakpoint = line.lastIndexOf("+");
			}
			
			double re = 0;
			double im = 0;
			
			try {
				if(breakpoint == -1) {
					if(line.contains("i")) {
						if(line.trim().equals("i")) {
							im = 1;
						} else if(line.trim().equals("-i")){
							im = -1;
						} else {
							im = Double.parseDouble(line.replace("i", "").trim());
						}
					} else {
						re = Double.parseDouble(line.trim());
					}
				} else {
					if(line.indexOf("i") == -1) {
						throw new NumberFormatException();
					}
					if(line.indexOf("i") < breakpoint) {
						String imStr = line.substring(0, line.indexOf("i")).trim();
						if(imStr.equals("")) {
							im = 1;
						} else if (imStr.equals("-")) {
							im = -1;
						} else {
							im = Double.parseDouble(imStr);
						}
						re = Double.parseDouble(line.substring(breakpoint + 1).trim());
					} else {
						re = Double.parseDouble(line.substring(0, breakpoint).trim());
						
						String istr = line.substring(breakpoint + 1).replace("i", "").trim();
						im = istr.isEmpty() ? 1 : Double.parseDouble(istr);
					}
				}
			} catch (NumberFormatException ex) {
				System.out.println(line + " is not a complex number.");
			}
			
			roots.add(new Complex(re, im));
			
			System.out.print("Root " + counter + "> ");
			counter++;
			line = sc.nextLine();
		}
		
		sc.close();
		
		if(roots.size() < 2) {
			throw new IllegalArgumentException("Not enough roots were given: " + roots.size());
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		ComplexRootedPolynomial rooted = new ComplexRootedPolynomial(roots.toArray(new Complex[1]));
		
		FractalViewer.show(new MyProducer(rooted));
		
	}
	
	/**
	 * Class MyJob is used to describe job of each thread that is running in the program. 
	 * Each thread calculates a part of image that is drawn.
	 * Which part is specified by yMin and yMax.
	 * @author Alex
	 *
	 */
	public static class MyJob implements Callable<Void> {
		
		/**
		 * Minimum value of real component.
		 */
		private double reMin;
		
		/**
		 * Maximum value of real component.
		 */
		private double reMax;
		
		/**
		 * Minimum value of imaginary component.
		 */
		private double imMin;
		
		/**
		 * Maximum value of imaginary component.
		 */
		private double imMax;
		
		/**
		 * Width of image.
		 */
		private int width;
		
		/**
		 * Height of image.
		 */
		private int height;
		
		/**
		 * Minimum value of y that this job should calculate.
		 */
		private int yMin;
		
		/**
		 * Maximum value of y that this job should calculate.
		 */
		private int yMax;
		
		/**
		 * Number of iterations.
		 */
		private int m;
		
		/**
		 * Array in which values needed for drawing the fractal are stored.
		 */
		private short[] data;
		
		/**
		 * Polynomial whose fractal is drawn.
		 */
		private ComplexRootedPolynomial rooted;
		
		/**
		 * Threshold for module of complex number.
		 */
		private double threshold;
		
		/**
		 * Constructor.
		 * @param reMin minimum value of real component.
		 * @param reMax maximum value of real component.
		 * @param imMin minimum value of imaginary component.
		 * @param imMax maximum value of imaginary component.
		 * @param width width of image.
		 * @param height height of image.
		 * @param yMin minimum value of y that this job should calculate.
		 * @param yMax maximum value of y that this job should calculate.
		 * @param m number of iterations.
		 * @param data array in which values needed for drawing the fractal are stored.
		 * @param rooted polynomial whose fractal is drawn.
		 * @param threshold threshold for module of complex number.
		 */
		public MyJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				int m, short[] data, ComplexRootedPolynomial rooted, double threshold) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.rooted = rooted;
			this.threshold = threshold;
		}

		@Override
		public Void call() throws Exception {
			ComplexPolynomial polynomial = rooted.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
						
			int offset = yMin * width;
			for(int y = yMin; y < yMax; y++) {
				for(int x = 0; x < width; x++) {
					Complex zn = mapToComplexPlain(x, y);
					int iter = 0;
					double module;
					Complex zn1;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
					} while(module > threshold && iter < m);
					
					int index = rooted.indexOfClosestRootFor(zn1, threshold);
					
					if(index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) index;
					}
				}
			}
			
			return null;
		}

		/**
		 * Maps point(x, y) to complex plain.
		 * @param x coordinate of point in image.
		 * @param y coordinate of point in image.
		 * @return complex number.
		 */
		private Complex mapToComplexPlain(int x, int y) {
			double re = x / (width-1.0) * (reMax - reMin) + reMin;;
			double im = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;

			return new Complex(re, im);
		}
		
	}
	
	/**
	 * ThreadFactory that produces daemonic threads.
	 * @author Alex
	 *
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
		
	}
	
	/**
	 * Fractal producer that produces threads and jobs needed to calculate data for fractal.
	 * Number of threads and jobs is determined by number of available processors.
	 * @author Alex
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		
		/**
		 * Pool of threads.
		 */
		private ExecutorService pool;
		
		/**
		 * Polynomial.
		 */
		private ComplexRootedPolynomial rooted;

		/**
		 * Constructor.
		 * @param rooted polynomial.
		 */
		public MyProducer(ComplexRootedPolynomial rooted) {
			this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
			this.rooted = rooted;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, 
				int width, int height, long requestNo, IFractalResultObserver observer) {
			
			System.out.println("Starting calculations...");
			
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			int noOfJobs = 8 * Runtime.getRuntime().availableProcessors();
			int yJob = height / noOfJobs;
			double threshold = 1e-3;
			
			List<Future<Void>> jobs = new ArrayList<>();
			
			for(int i = 0; i < noOfJobs; i++) {
				int ymin = yJob * i;
				int ymax = i==noOfJobs-1 ? height-1 : yJob * (i + 1);
								
				MyJob job = new MyJob(reMin, reMax, imMin, imMax, width, height, ymin, ymax, m, data, rooted, threshold);
				jobs.add(pool.submit(job));
			}
			
			for(Future<Void> job : jobs) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			System.out.println("Calculating over, please wait until GUI is notified...");
			observer.acceptResult(data, (short) (rooted.toComplexPolynom().order() + 1), requestNo);
		}
		
	}

}
