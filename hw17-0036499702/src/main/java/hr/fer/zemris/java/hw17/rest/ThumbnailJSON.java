package hr.fer.zemris.java.hw17.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * Contains method that sends response with data needed for generating thumbnails on page.
 * 
 * @author Alex
 *
 */
@Path("/thumbnails")
public class ThumbnailJSON {

	/**
	 * Sends data needed for rendering thumbnails of photos that have tag given as argument.
	 * It prepares two structures:
	 * 			thumbs - encoded data of thumbnails
	 * 			photos - other relevant information about photos
	 * @param tag
	 * 			tag used to select photos
	 * @return
	 * 			{@link Response} that contains all data needed to render thumbnails on page
	 */
	@Path("{tag}")
	@GET
	@Produces("application/json")
	public Response getThumbs(@PathParam("tag") String tag) {
		List<Photo> photos = Util.getPhotosByTag(tag);
		
		List<String> thumbs = new ArrayList<>();
		for(Photo photo : photos) {
			try {
				thumbs.add(Util.getThumbnail(photo));
			} catch (IOException e) {
				return Response.status(Status.EXPECTATION_FAILED).build();
			}
		}
		
		JSONObject result = new JSONObject();
		result.put("photos", photos);
		result.put("thumbs", thumbs);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
}
