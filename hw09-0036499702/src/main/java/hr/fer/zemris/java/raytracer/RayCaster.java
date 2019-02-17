package hr.fer.zemris.java.raytracer;

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
 * This class is used to draw rays with an implementation of {@link IRayTracerProducer}.
 * There is no parallelization in this class.
 * @author Alex
 *
 */
public class RayCaster {

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
	 * Returns an implementation of {@link IRayTracerProducer} that is later used to draw rays.
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
				int offset = 0;
				
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((x * horizontal) / (width - 1)))
								.sub(yAxis.scalarMultiply((y * vertical) / (height - 1)));
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
				
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
