package com.stars.processor;
import java.util.List;
import java.util.logging.Logger;

import com.stars.persistence.dao.*;
import com.stars.request_response.AddStoryRequest;
import com.stars.request_response.AddStoryResponse;

public class StoryProcessor{
	private static Logger log = Logger.getLogger(StoryProcessor.class.getName());
    
    public StoryProcessor(){
    }
    
    public AddStoryResponse processAddStoryRequest(AddStoryRequest request) throws Exception {
    	AddStoryResponse response = new AddStoryResponse();
    	
    	Story story = createStory(request);
    	story.save();
		log.info("story created successfully with storyId: " +story.getStoryId());
		
		response.setStoryId(story.getStoryId());
    	
    	return response;
    }

	private Story createStory(AddStoryRequest request) throws Exception {
		log.info("Saving Story");
		Story story = new Story();
		Users user = Users.loadUserByScreenName(request.getScreenName());
		story.setUser(user);
		story.setDescription(request.getDescription());
		
		return story;
	}
}

