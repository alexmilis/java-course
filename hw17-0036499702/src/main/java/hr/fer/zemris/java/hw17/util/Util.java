package hr.fer.zemris.java.hw17.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

/**
 * Util class that imitates working with database. It provides static methods for working with photos.
 * Photos are stored in folder WEB-INF/slike and thumbnails in folder WEB-INF/thumbnails.
 * List of photos and other relevant information are stored in file opisnik.txt in folder WEB-INF.
 * 
 * @author Alex
 *
 */
public class Util {
	
	/**
	 * Path to folder with photos.
	 */
	private static Path pathPhoto = Paths.get("slike");
	
	/**
	 * Path to file with relevant information about photos.
	 */
	private static Path pathDescription = Paths.get("opisnik.txt");

	/**
	 * Path to folder with thumbnails.
	 */
	private static Path pathThumb = Paths.get("thumbnails");
	
	/**
	 * Gets set of all tags that appear in opisnik.txt in alphabetical order.
	 * @return
	 * 			set of tags
	 * 			null if an error occurs while reading the file
	 */
	public static Collection<String> getTags(){
		Set<String> tags = new TreeSet<>();
		try {
			List<String> lines = Files.readAllLines(pathDescription);
			for(int i = 0; i < lines.size() / 3; i++) {
				String[] parts = lines.get(i*3 + 2).split(",");
				for(String tag : parts) {
					tags.add(tag.trim());
				}
			}
		} catch (IOException e) {
			return null;
		}
		return tags;
	}
	
	/**
	 * Gets list of photos that are contained in opisnik.txt.
	 * @return
	 * 			list of {@link Photo}.
	 * 			null if an error occurs while reading the file
	 */
	public static List<Photo> getPhotos(){
		List<Photo> photos = new LinkedList<>();
		try {
			List<String> lines = Files.readAllLines(pathDescription);
			for(int i = 0; i < lines.size() / 3; i++) {
				photos.add(new Photo(lines.get(i*3), lines.get(i*3 + 1), lines.get(i*3 + 2)));
			}
		} catch (IOException e) {
			return null;
		}
		return photos;
	}
	
	/**
	 * Gets all photos that have given tag.
	 * @param tag
	 * 			tag used to select photos
	 * @return
	 * 			list of {@link Photo}
	 * 			null if an error occurs while reading the file
	 */
	public static List<Photo> getPhotosByTag(String tag){
		List<Photo> photos = new LinkedList<>();
		try {
			List<String> lines = Files.readAllLines(pathDescription);
			for(int i = 0; i < lines.size() / 3; i++) {
				if(lines.get(i*3 + 2).contains(tag)) {
					photos.add(new Photo(lines.get(i*3), lines.get(i*3 + 1), lines.get(i*3 + 2)));
				}
			}
		} catch (IOException e) {
			return null;
		}
		return photos;
	}
	
	/**
	 * Gets data of thumbnail as base64 encoded string so it can be sent via JSON.
	 * If thumbnail does not already exist, it creates it from photo and stores it in folder thumbnails.
	 * If folder thumbnails does not exist, it creates it in folder WEB-INF.
	 * @param photo
	 * 			photo whose thumbnail should be returned
	 * @return
	 * 			thumbnail in form of base64 encoded string
	 * @throws IOException
	 * 			if an error occurs while reading/writing from/to files
	 */
	public static String getThumbnail(Photo photo) throws IOException{
		if(!Files.exists(pathThumb)) {
			Files.createDirectory(pathThumb);
		}
		
		BufferedImage thumb;
		Path path = pathThumb.resolve(photo.getName());
		if(!Files.exists(path)) {
			thumb = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
			thumb.createGraphics().drawImage(
					ImageIO.read(pathPhoto.resolve(photo.getName()).toFile())
					.getScaledInstance(150, 150, Image.SCALE_SMOOTH),0,0,null);
			ImageIO.write(thumb, "jpg", path.toFile());
		} else {
			thumb = ImageIO.read(path.toFile());
		}
		
		return getData(photo.getName(), true);
	}
	
	/**
	 * Gets photo with given name from opisnik.txt.
	 * @param name
	 * 			name of file (photo)
	 * @return
	 * 			{@link Photo}
	 * 			null if photo with given name does not exist or an error occurs while reading the file
	 */
	public static Photo getPhoto(String name) {
		try {
			List<String> lines = Files.readAllLines(pathDescription);
			for(int i = 0; i < lines.size() / 3; i++) {
				if(lines.get(i*3).equals(name)) {
					return new Photo(lines.get(i*3), lines.get(i*3 + 1), lines.get(i*3 + 2));
				}
			}
		} catch (IOException e) {
			return null;
		}
		return null;
	}
	
	/**
	 * Gets data of image as base64 encoded string.
	 * @param name
	 * 			name of file
	 * @param thumbnail
	 * 			true if requested image is a thumbnail, false otherwise
	 * @return
	 * 			image as base64 encoded string
	 * 			null if an error occurs while reading the file or encoding the image
	 */
	public static String getData(String name, boolean thumbnail) {
		Path path = null;
		if(thumbnail) {
			path = pathThumb.resolve(name);
		} else {
			path = pathPhoto.resolve(name);
		}
		
		try {
			BufferedImage thumb = ImageIO.read(path.toFile());
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(thumb, "jpg", out);
			
			String data = new String(Base64.getEncoder().encodeToString(out.toByteArray()));
			return data;
		} catch (IOException ex) {
			return null;
		}
		
	}
	
	/**
	 * Sets all path variables of this class.
	 * It resolves default paths with path given as argument so results are real paths.
	 * @param path
	 * 			path that is resolved with default values
	 */
	public static void setPath(Path path) {
		pathPhoto = path.resolve(pathPhoto);
		pathDescription = path.resolve(pathDescription);
		pathThumb = path.resolve(pathThumb);
	}
}
