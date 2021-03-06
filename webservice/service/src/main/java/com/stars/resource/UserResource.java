package com.stars.resource;

import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.stars.exception.InvalidSubscriptionException;
import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;
import com.stars.processor.UserProcessor;
import com.stars.user.request_response.AddUserRequest;
import com.stars.user.request_response.AddUserResponse;
import com.stars.user.request_response.ChangePasswordRequest;
import com.stars.user.request_response.GetUserResponse;
import com.stars.user.request_response.ValidateUserRequest;
import com.stars.user.request_response.ValidateUserResponse;
import com.stars.user.request_response.GenerateTemporaryPasswordRequest;

@Path("/stars/user")
public class UserResource {
	private static final String WS_RETURN_TYPE_JSON = "application/json";
	private static Logger log = Logger.getLogger(UserResource.class.getName());

	@POST
	@Path("/addUser")
	@Consumes({ "application/xml", "application/json" })
	public Response addUser(AddUserRequest request) {
		PersistenceManager persist = null;
		try {
			log.info("Got Request for add user: " + request.marshal());

			persist = PersistenceManagerFactory.getInstance()
					.getPersistenceManager();
			persist.beginTransaction();

			UserProcessor process = new UserProcessor();
			AddUserResponse response = process.processAddRequest(request);

			persist.commitTransaction();

			return Response.status(Response.Status.OK).entity(response)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {

			log.info("Exception occured while processing addUser request. "
					+ ex.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.type(WS_RETURN_TYPE_JSON).build();
		} finally {
			if (persist != null) {
				persist.rollbackTransaction();
				persist.cleanUp();
			}
		}
	}

	@POST
	@Path("/validateUser")
	@Consumes({ "application/xml", "application/json" })
	public Response validateUser(ValidateUserRequest request) {
		log.info("Got Request for user validation. Screen Name: "
				+ request.getScreenName());
		try {
			UserProcessor process = new UserProcessor();
			ValidateUserResponse response = process.validateUser(request);
			return Response.status(Response.Status.OK).entity(response)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {
			log.info("Exception while executing validateUser: "
					+ ex.getMessage());
			ex.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.type(WS_RETURN_TYPE_JSON).build();
		}

	}

	@GET
	@Path("/isSubscriptionValid")
	@Consumes({ "application/xml", "application/json" })
	public Response isSubscriptionValid(
			@QueryParam("screenname") String screenName,
			@QueryParam("email") String email) {
		log.info("Got Request for subscription validation. Screen Name: "
				+ screenName + " and email: " + email);
		try {
			UserProcessor process = new UserProcessor();
			process.validateUserSubscription(screenName, email);
			return Response.status(Response.Status.OK)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (InvalidSubscriptionException ex) {
			return Response.status(Response.Status.CONFLICT)
					.entity(ex.getMessage()).type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.type(WS_RETURN_TYPE_JSON).build();
		}
	}

	@GET
	@Path("/getUserByScreenName")
	@Consumes({ "application/xml", "application/json" })
	public Response getUserByScreenName(
			@QueryParam("screenname") String screenName) {
		log.info("GetUser request for screen Name: " + screenName);
		try {
			UserProcessor process = new UserProcessor();
			GetUserResponse response = process.getUserByScreenName(screenName);
			return Response.status(Response.Status.OK).entity(response)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).type(WS_RETURN_TYPE_JSON).build();
		}
	}

	@GET
	@Path("/getUserByEmail")
	@Consumes({ "application/xml", "application/json" })
	public Response getUserByEmail(@QueryParam("email") String email) {
		log.info("GetUser request for email: " + email);
		try {
			UserProcessor process = new UserProcessor();
			GetUserResponse response = process.getUserByEmail(email);
			return Response.status(Response.Status.OK).entity(response)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).type(WS_RETURN_TYPE_JSON).build();
		}
	}

	@GET
	@Path("/getUser")
	@Consumes({ "application/xml", "application/json" })
	public Response getUser(@QueryParam("screenname") String screenName,
			@QueryParam("email") String email) {
		log.info("GetUser request for screen Name: " + screenName
				+ " and email: " + email);
		try {
			UserProcessor process = new UserProcessor();
			GetUserResponse response = process.getUser(screenName, email);
			return Response.status(Response.Status.OK).entity(response)
					.type(WS_RETURN_TYPE_JSON).build();
		} catch (Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).type(WS_RETURN_TYPE_JSON).build();
		}
	}
	
	@POST
	@Path("/generateTemporaryPassword")
	@Consumes({ "application/xml", "application/json" })
	public Response generateTemoraryPassword(GenerateTemporaryPasswordRequest request){
		PersistenceManager persist = null;
		try{
			log.info("GenerateTemoraryPassword request for screen Name: " +request.getScreenName());
    		
    		persist = PersistenceManagerFactory.getInstance().getPersistenceManager();
    		persist.beginTransaction();
    		
			UserProcessor process = new UserProcessor();
			process.createTempPassword(request.getScreenName());    		
			
			persist.commitTransaction();
			
			return Response.status(Response.Status.OK).type(WS_RETURN_TYPE_JSON).build();
		}catch(NoResultException ex){
			ex.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).type(WS_RETURN_TYPE_JSON).build();
		}catch(Exception ex){
			ex.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).type(WS_RETURN_TYPE_JSON).build();
		}finally {
			if(persist != null){
				persist.rollbackTransaction();
				persist.cleanUp();
			}
		}
	}
	
	@POST
	@Path("/changePassword")
	@Consumes({ "application/xml", "application/json" })
	public Response changePassword(ChangePasswordRequest request){
		PersistenceManager persist = null;
		try{
			log.info("changePassword request for screen Name: " +request.getScreenName());
    		
    		persist = PersistenceManagerFactory.getInstance().getPersistenceManager();
    		persist.beginTransaction();
    		
			UserProcessor process = new UserProcessor();
			process.changePassword(request);    		
			
			persist.commitTransaction();
			
			return Response.status(Response.Status.OK).type(WS_RETURN_TYPE_JSON).build();
		}catch(NoResultException ex){
			ex.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).type(WS_RETURN_TYPE_JSON).build();
		}catch(Exception ex){
			ex.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).type(WS_RETURN_TYPE_JSON).build();
		}finally {
			if(persist != null){
				persist.rollbackTransaction();
				persist.cleanUp();
			}
		}
	}
}
