package com.stars.resource;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;
import com.stars.processor.ProfileProcessor;
import com.stars.profile.request_response.AddProfileRequest;
import com.stars.profile.request_response.AddProfileResponse;

@Path("/stars/profile")
public class ProfileResource {
	private static final String WS_RETURN_TYPE_JSON = "application/json";
	private static Logger log = Logger.getLogger(ProfileResource.class
			.getName());

	@POST
	@Path("/addProfile")
	@Consumes({ "application/xml", "application/json" })
	public Response addUser(AddProfileRequest request) {
		PersistenceManager persist = null;
		try {
			log.info("Got Request to add profile: " + request.marshal());

			persist = PersistenceManagerFactory.getInstance()
					.getPersistenceManager();
			persist.beginTransaction();

			ProfileProcessor process = new ProfileProcessor(request);
			AddProfileResponse response = process.processAddProfile();
			persist.commitTransaction();

			return Response.status(Response.Status.OK).entity(response)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {
			
			ex.printStackTrace();
			log.info("Exception occured while processing addProfile request. "
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
