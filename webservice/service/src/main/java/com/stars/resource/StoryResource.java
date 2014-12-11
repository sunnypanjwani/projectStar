package com.stars.resource;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;
import com.stars.processor.StoryProcessor;
import com.stars.stories.request_response.AddStoryRequest;
import com.stars.stories.request_response.AddStoryResponse;

@Path("/stars/story")
public class StoryResource {
	private static final String WS_RETURN_TYPE_JSON = "application/json";
	private static Logger log = Logger.getLogger(StoryResource.class.getName());

	@POST
	@Path("/addStory")
	@Consumes({ "application/xml", "application/json" })
	public Response addStory(AddStoryRequest request) {
		PersistenceManager persist = null;
		try {
			log.info("Got Request for addStory: " + request.marshal());

			persist = PersistenceManagerFactory.getInstance().getPersistenceManager();
			persist.beginTransaction();

			StoryProcessor process = new StoryProcessor();
			AddStoryResponse response = process.processAddStoryRequest(request);

			persist.commitTransaction();

			return Response.status(Response.Status.OK).entity(response)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {

			log.info("Exception occured while processing addStory request. "
					+ ex.getMessage());
			if (persist != null) {
				persist.rollbackTransaction();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.type(WS_RETURN_TYPE_JSON).build();
		} finally {
			if (persist != null) {
				persist.commitTransaction();
				persist.cleanUp();
			}
		}
	}
}
