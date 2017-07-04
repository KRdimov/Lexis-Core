package com.getlexis.resources;

import com.getlexis.json.JsonStructure;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/templates")
public class TemplateResource {
    private List<String> dummyTemplates = new ArrayList<String>(){{
        add("Hi, this is me!");
        add("I am sorry for your message");
        add("Dumb customer!!!");
    }};

    @POST
    @Path("/email")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postEmail(String email) {
    	JsonStructure json = new JsonStructure();
    	json.templates = dummyTemplates.toArray(new String[0]);
    	
    	String output = produceJsonOutput(json);
    	return Response.ok(output,MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplates() {
    	JsonStructure json = new JsonStructure();
    	json.templates = dummyTemplates.toArray(new String[0]);
    	
    	String output = produceJsonOutput(json);
    	return Response.ok(output,MediaType.APPLICATION_JSON).build();
    }
    
    private String produceJsonOutput(JsonStructure json) {
    	Gson gson = new Gson();
    	return gson.toJson(json);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplate(@PathParam("id") int id) {
    	JsonStructure json = new JsonStructure();
    	json.templates = new String[] {dummyTemplates.get(id)};
    	
    	String output = produceJsonOutput(json);
    	return Response.ok(output, MediaType.APPLICATION_JSON).build();
    }

	@PUT
    @Path("/update/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updateTemplate(@PathParam("id") int id, String newTemplate) {
        dummyTemplates.set(id, newTemplate);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.TEXT_PLAIN)
    public void postTemplate(String template) {
        dummyTemplates.add(template);
    }
}