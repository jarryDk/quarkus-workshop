package dk.jarry.todo.control;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("todos")
@RegisterRestClient(baseUri = "http://localhost:8080")
public interface ToDoResourceClient {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject create(JsonObject toDo);
    
    @GET
	@Path("{id}")
	JsonObject read(@PathParam("id") Integer id);

	@PUT
	@Path("{id}")
	JsonObject update(@PathParam("id") Integer id, JsonObject toDo);

	@DELETE
	@Path("{id}")	
	public void delete(@PathParam("id") Integer id);

	@GET
	public JsonArray list( //
		@QueryParam("from") Integer from, //
		@QueryParam("limit") Integer limit) ;
}
