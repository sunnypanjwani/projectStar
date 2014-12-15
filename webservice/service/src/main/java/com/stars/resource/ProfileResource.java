package com.stars.resource;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.stars.exception.InvalidRequestException;
import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;
import com.stars.processor.AddProfileProcessor;
import com.stars.processor.DeleteProfileProcessor;
import com.stars.processor.EditProfileProcessor;
import com.stars.profile.request_response.AddProfileRequest;
import com.stars.profile.request_response.AddProfileResponse;
import com.stars.profile.request_response.DeleteProfileRequest;
import com.stars.profile.request_response.EditProfileRequest;

@Path("/stars/profile")
public class ProfileResource {
	private static final String WS_RETURN_TYPE_JSON = "application/json";
	private static Logger log = Logger.getLogger(ProfileResource.class
			.getName());

	@POST
	@Path("/addProfile")
	@Consumes({ "application/xml", "application/json" })
	public Response addProfile(AddProfileRequest request) {
		PersistenceManager persist = null;
		try {
			log.info("Got Request to add profile: " + request.marshal());

			persist = PersistenceManagerFactory.getInstance()
					.getPersistenceManager();
			persist.beginTransaction();

			AddProfileProcessor processor = new AddProfileProcessor(request);
			AddProfileResponse response = processor.processAddProfile();
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

	@POST
	@Path("/editProfile")
	@Consumes({ "application/xml", "application/json" })
	public Response editProfile(EditProfileRequest request) {
		PersistenceManager persist = null;
		try {
			log.info("Got Request to edit profile: " + request.marshal());

			persist = PersistenceManagerFactory.getInstance()
					.getPersistenceManager();
			persist.beginTransaction();

			EditProfileProcessor processor = new EditProfileProcessor(request);
			processor.editProfile();
			persist.commitTransaction();

			return Response.status(Response.Status.OK).build();
		} catch (InvalidRequestException ex) {

			ex.printStackTrace();
			log.info("Invalid editProfile request. " + ex.getMessage());
			if (persist != null) {
				persist.rollbackTransaction();
			}
			return Response.status(Response.Status.BAD_REQUEST)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {

			ex.printStackTrace();
			log.info("Exception occured while processing editProfile request. "
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
	
	@POST
	@Path("/deleteProfile")
	@Consumes({ "application/xml", "application/json" })
	public Response getProfile(DeleteProfileRequest request) {
		PersistenceManager persist = null;
		try {
			log.info("Got Request to edit profile: " + request.marshal());

			persist = PersistenceManagerFactory.getInstance()
					.getPersistenceManager();
			persist.beginTransaction();

			DeleteProfileProcessor processor = new DeleteProfileProcessor(request);
			processor.processRequest();
			persist.commitTransaction();

			return Response.status(Response.Status.OK).build();
		} catch (InvalidRequestException ex) {

			ex.printStackTrace();
			log.info("Invalid deleteProfile request. " + ex.getMessage());
			if (persist != null) {
				persist.rollbackTransaction();
			}
			return Response.status(Response.Status.BAD_REQUEST)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {

			ex.printStackTrace();
			log.info("Exception occured while processing deleteProfile request. "
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
