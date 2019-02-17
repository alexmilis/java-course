package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class used to draw rays with parallel calculations using Fork-Join framework.
 * @author Alex
 *
 */
public class RayCasterParallel {

	/**
	 * Main method.
	 * @param args not needed here.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10, 0, 0),
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20, 20);
	}
	
	/**
	 * Recursive method that splits the job into smaller parts.
	 * When parts are smaller than threshold, they are calculated directly.
	 * @author Alex
	 *
	 */
	private static class MyJob extends RecursiveAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Data for color red.
		 */
		private short[] red;
		
		/**
		 * Data for color green;
		 */
		private short[] green;
		
		/**
		 * Data for color blue;
		 */
		private short[] blue;
		
		/**
		 * Coordinates of left upper corner of screen;
		 */
		private Point3D screenCorner;
		
		/**
		 * Scene on which rays are drawn.
		 */
		private Scene scene;
		
		/**
		 * Data for colors.
		 */
		private short[] rgb;
		
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
		 * Coordinates of observer.
		 */
		private Point3D eye;
		
		/**
		 * Horizontal size of image.
		 */
		private double horizontal;
		
		/**
		 * Vertical size of image.
		 */
		private double vertical;

		/**
		 * Vector of x axis.
		 */
		private Point3D xAxis;

		/**
		 * Vector of y axis.
		 */
		private Point3D yAxis;
		
		/**
		 * Threshold for difference in distances.
		 */
		private static final int threshold = 16;

		
		/**
		 * Constructor.
		 * @param yAxis 
		 * @param xAxis 
		 * @param red
		 * @param green
		 * @param blue
		 * @param screenCorner
		 * @param scene
		 * @param rgb
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param eye
		 * @param horizontal
		 * @param vertical
		 */
		public MyJob(Point3D xAxis, Point3D yAxis, short[] red, short[] green, short[] blue, Point3D screenCorner, Scene scene, short[] rgb,
				int width, int height, int yMin, int yMax, Point3D eye, double horizontal, double vertical) {
			super();
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.rgb = rgb;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.eye = eye;
			this.horizontal = horizontal;
			this.vertical = vertical;
		}

		@Override
		protected void compute() {
			if(yMax-yMin+1 <= threshold) {
				computeDirect();
				return;
			}
			
			invokeAll(
					new MyJob(xAxis, yAxis, red, green, blue, screenCorner, scene, rgb, width, height, yMin, yMin+(yMax-yMin)/2, eye, horizontal, vertical),
					new MyJob(xAxis, yAxis, red, green, blue, screenCorner, scene, rgb, width, height, yMin + (yMax-yMin)/2, yMax, eye, horizontal, vertical)
			);
		}

		/**
		 * This method is invoked when y variable is in range smaller than threshold.
		 * It is used to calculate data for drawing.
		 */
		private void computeDirect() {
			synchronized (eye) {
				for(int y = yMin; y < yMax; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((x * horizontal) / (width - 1)))
								.sub(yAxis.scalarMultiply((y * vertical) / (height - 1)));
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb);
						
						red[y * width + x] = rgb[0] > 255 ? 255 : rgb[0];
						green[y * width + x] = rgb[1] > 255 ? 255 : rgb[1];
						blue[y * width + x] = rgb[2] > 255 ? 255 : rgb[2];
						
					}
				}
			}
				
		}
	}

	/**
	 * Returns an implementation of {@link IRayTracerProducer} that is later used to draw rays.
	 * It uses {@link ForkJoinPool} to parallelize jobs.
	 * @return
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2))
						.add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new MyJob(xAxis, yAxis, red, green, blue, screenCorner, scene, rgb, width, height, 0, height - 1, eye, horizontal, vertical));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
				
			}
		};
			
	}
	
	/**
	 * Method used to determine if an intersection even exists.
	 * If it doesn't, pixel is painted black.
	 * Otherwise color of pixel is calculated based on Phong model.
	 * @param scene scene in which are objects and rays
	 * @param ray ray that is being traced
	 * @param rgb array of 3 short values used to store color
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection  intersection = findClosestIntersection(scene, ray);
		
		if(intersection == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			return;
		}
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		for(LightSource ls : scene.getLights()) {
			Ray ray2 = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
			
			RayIntersection intersection2 = findClosestIntersection(scene, ray2);
			
			if(intersection2 == null) {
				continue;
			}
			
			if(intersection2.getDistance() < ls.getPoint().sub(intersection.getPoint()).norm() - 0.001) {
				continue;
			}
			
			Point3D normal1 = intersection.getNormal();
			Point3D neg1 = ray.direction.negate();
			
			Point3D normal2 = intersection2.getNormal();
			Point3D neg2 = ray2.direction.negate();
			
			
			double diffuse = Math.abs(neg2.scalarProduct(normal2));
			
			double reflective = normal1.scalarMultiply(2 * neg2.scalarProduct(normal1)).sub(neg2).scalarProduct(neg1);
			reflective = Math.pow(reflective, intersection.getKrn());
			
			if(reflective < 0) {
				reflective = 0;
			}
			
			rgb[0] += ls.getR() * intersection.getKdr() * diffuse + 
					ls.getR() * intersection.getKrr() * reflective;
			rgb[1] += ls.getG() * intersection.getKdg() * diffuse + 
					ls.getG() * intersection.getKrg() * reflective;
			rgb[2] += ls.getB() * intersection.getKdb() * diffuse + 
					ls.getB() * intersection.getKrb() * reflective;
		}
				
	}
	
	/**
	 * Method that finds the closest intersection of ray and any graphical object in scene.
	 * @param scene scene in which objects are placed
	 * @param ray ray that intersects with objects
	 * @return closest ray intersection, if such exists, otherwise null
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closest = null;
		
		for(GraphicalObject obj : scene.getObjects()) {
			RayIntersection  intersection = obj.findClosestRayIntersection(ray);
			if(closest == null) {
				closest = intersection;
			} else {
				if(intersection != null) {
					if(intersection.getDistance() < closest.getDistance()) {
						closest = intersection;
					}
				}
			}
		}
		
		return closest;
	}

}
