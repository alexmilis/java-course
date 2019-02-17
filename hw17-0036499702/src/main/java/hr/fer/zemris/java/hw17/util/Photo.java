package hr.fer.zemris.java.hw17.util;

/**
 * Representation of photos in gallery. Each photo has properties:
 * 			name - name of file in folder slike
 * 			description - short description of photo
 * 			tags - tags that describe photo
 * 
 * @author Alex
 *
 */
public class Photo {

	/**
	 * Name of file.
	 */
	private String name;
	
	/**
	 * Description of photo.
	 */
	private String description;
	
	/**
	 * Tags that describe photo.
	 */
	private String tags;
	
	/**
	 * Constructor of photo.
	 * @param name
	 * 			name of file
	 * @param description
	 * 			description of photo
	 * @param tags
	 * 			tags describing photo
	 */
	public Photo(String name, String description, String tags) {
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Gets name of file.
	 * @return
	 * 			filename
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name of file
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets description of photo.
	 * @return
	 * 			description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description of photo.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets tags associated with this photo.
	 * @return
	 * 			tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * Sets tags of photo.
	 * @param tags
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

}
