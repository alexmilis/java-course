package hr.fer.zemris.java.hw17.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.java.hw17.util.Photo;
import hr.fer.zemris.java.hw17.util.Util;

/**
 * Sends all data relevant for displaying an image on index.html using JSON technology.
 * Data includes name of image, description of image, tags and actual data (base64 encoded image).
 * 
 * @author Alex
 *
 */
@Path("/photo")
public class PhotoJSON {
	
	/**
	 * Sends data relevant for displaying image to page.
	 * @param name
	 * 			name of image to be displayed.
	 * @return
	 * 			{@link Response} that contains said data
	 */
	@Path("{name}")
	@GET
	@Produces("application/json")
	public Response getThumbs(@PathParam("name") String name) {
		Photo photo = Util.getPhoto(name);
		
		JSONObject result = new JSONObject();
		result.put("name", photo.getName());
		result.put("description", photo.getDescription());
		result.put("tags", photo.getTags());
		
		result.put("data", Util.getData(name, false));
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}

}
