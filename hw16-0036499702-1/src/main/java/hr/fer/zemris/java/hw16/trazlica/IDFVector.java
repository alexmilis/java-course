package hr.fer.zemris.java.hw16.trazlica;

import java.util.ArrayList;
import java.util.List;

/**
 * Vectors used to represent documents in search engine {@link Konzola}.
 * Implementation used is TF-IDF vectors.
 * Values are calculated for each word in vocabulary.
 * 			value = tf(word, document) * idf(word, directory)
 * 			tf(word, document) = number of times word appears in document
 * 			idf(word, directory) = log(number of documents / number of documents in which word appears)
 * 
 * Methods this class provides for working with {@link IDFVector} are:
 * 			norm, scalar, sim
 * 
 * @author Alex
 *
 */
public class IDFVector {
	
	/**
	 * List of tf-idf values for each word in vocabulary.
	 */
	List<Double> values = new ArrayList<>();
	
	/**
	 * Constructor of IDF-Vector.
	 * @param values
	 * 				double[] tf-idf values for each word
	 */
	public IDFVector(double...values) {
		if(values == null || values.length == 0) {
			throw new IllegalArgumentException("No values were given to initialize vector.");
		}
		for(int i = 0; i < values.length; i++) {
			this.values.add(values[i]);
		}
	}

	/**
	 * Getter for values.
	 * @return
	 * 				list of tf-idf values
	 */
	public List<Double> getValues() {
		return values;
	}

	/**
	 * Setter for values.
	 * @param values
	 * 				list of tf-idf values
	 */
	public void setValues(List<Double> values) {
		this.values = values;
	}

	/**
	 * Calculates norm of vector.
	 * @return
	 * 				norm of vector
	 */
	public double norm() {
		double norm = 0;
		for(double n : values) {
			norm += n * n;
		}
		return Math.sqrt(norm);
	}

	/**
	 * Calculates scalar product of this and other vector.
	 * @param other
	 * 				other {@link IDFVector}
	 * @return
	 * 				double value of scalar product
	 */
	public double scalar(IDFVector other) {
		double scalar = 0;
		for(int i = 0; i < values.size(); i++) {
			scalar += values.get(i) * other.values.get(i);
		}
		return scalar;
	}
	
	/**
	 * Calculates similarity between this and other vector.
	 * Similarity is equal to value of cos(angle between vectors).
	 * Similarity is from interval [0, 1].
	 * @param other
	 * 				other {@link IDFVector}
	 * @return
	 * 				similarity between vector.
	 */
	public double sim(IDFVector other) {
		return (scalar(other)/Math.abs(norm() * other.norm()));
	}
	
	

}
