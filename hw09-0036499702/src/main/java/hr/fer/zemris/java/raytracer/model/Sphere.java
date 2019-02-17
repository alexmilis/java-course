package hr.fer.zemris.java.raytracer.model;

/**
 * Implementation of {@link GraphicalObject} that represents a sphere.
 * Sphere is defined by center and radius.
 * @author Alex
 *
 */
public class Sphere extends GraphicalObject {
	
	/**
	 * Center of sphere.
	 */
	private Point3D center;
	
	/**
	 * Radius of sphere.
	 */
	private double radius;
	
	/**
	 * Coefficient for diffuse component for red color.
	 */
	private double kdr;
	
	/**
	 * Coefficient for diffuse component for green color.
	 */
	private double kdg;
	
	/**
	 * Coefficient for diffuse component for blue color
	 */
	private double kdb;
	
	/**
	 * Coefficient for reflective component for red color.
	 */
	private double krr;
	
	/**
	 * Coefficient for reflective component for green color.
	 */
	private double krg;
	
	/**
	 * Coefficient for reflective component for blue color.
	 */
	private double krb;
	
	/**
	 * Coefficient n for reflective component.
	 */
	private double krn;
	
	/**
	 * Constructor.
	 * @param center
	 * @param radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D startToCenter = ray.start.sub(center);
		
		double disc = Math.pow(startToCenter.scalarProduct(ray.direction), 2)
				- Math.pow(startToCenter.norm(), 2) + Math.pow(radius, 2);
		
		if(disc < 0) {
			return null;
		}
		
		double distance = -startToCenter.scalarProduct(ray.direction) - Math.sqrt(disc);
		Point3D point = ray.start.add(ray.direction.scalarMultiply(distance));
				
		return new MyRayIntersection(point, distance, distance > radius);
	}
	
	/**
	 * Implementation of {@link RayIntersection} returned by method findClosestRayIntersection.
	 * @author Alex
	 *
	 */
	public class MyRayIntersection extends RayIntersection {
		
		/**
		 * Constructor of MyRayIntersection.
		 * @param point intersection point
		 * @param distance distance between ray start point and intersection
		 * @param outer true if intersection is outer, false otherwise
		 */
		protected MyRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}
		
		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrn() {
			return krn;
		}
		
	}
	
}
