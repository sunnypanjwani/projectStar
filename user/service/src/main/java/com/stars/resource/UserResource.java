package com.stars.resource;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;
import com.stars.processor.UserProcessor;
import com.stars.request_response.AddUserRequest;
import com.stars.request_response.AddUserResponse;

@Path("/stars")
public class UserResource {
	private static final String WS_RETURN_TYPE_JSON = "application/json";
	private static Logger log = Logger.getLogger(UserResource.class.getName());
	
	@POST
	@Path("/addUser")
	@Consumes({ "application/xml", "application/json" })
	public Response addUser(AddUserRequest request){
		PersistenceManager persist = null;
		try{
			log.info("Got Request: " +request.marshal());
    		
    		persist = PersistenceManagerFactory.getInstance().getPersistenceManager();
    		persist.beginTransaction();
    		
    		UserProcessor process = new UserProcessor();
			AddUserResponse response = process.processAddRequest(request);
			
			persist.commitTransaction();
    		
			return Response.status(Response.Status.OK).entity(response).type(WS_RETURN_TYPE_JSON).build();
		}catch(Exception ex){
			persist.rollbackTransaction();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(WS_RETURN_TYPE_JSON).build();
		}finally {
			if(persist != null){
				persist.commitTransaction();
				persist.cleanUp();
			}
		}
		
	}
}
