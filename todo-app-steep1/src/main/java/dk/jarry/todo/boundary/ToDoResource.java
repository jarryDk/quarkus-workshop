package dk.jarry.todo.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/todos")
public class ToDoResource {

    @Inject
    @ConfigProperty(name = "message1") 
    String message1;

    @Inject
    @ConfigProperty(name = "message2", defaultValue="message2 not found") 
    String message2;

    @Inject
    @ConfigProperty(name = "message3", defaultValue="message3 not found") 
    String message3;
    
    @Inject
    ToDoService toDoService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy" + 
        		"\n - message1 : " + message1 +
        		"\n - message2 : " + message2 + 
        		"\n - message3 : " + message3 + 
        		"\n - toDoService.getMessage() : " + toDoService.getMessage();
    } 
}