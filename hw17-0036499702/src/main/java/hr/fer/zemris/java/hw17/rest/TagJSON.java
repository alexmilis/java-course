package hr.fer.zemris.java.hw17.rest;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.java.hw17.util.Util;

/**
 * Contains method for sending all available tags to page using JSON technology.
 * Tags are in alphabetical order and there are no duplicates.
 * 
 * @author Alex
 *
 */
@Path("/tag")
public class TagJSON {
	
	/**
	 * Gets and sends all tags to page.
	 * @return
	 * 			{@link Response} that contains tags
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		Collection<String> tags = Util.getTags();
		
		JSONObject result = new JSONObject();
		result.put("tags", tags);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}

}
