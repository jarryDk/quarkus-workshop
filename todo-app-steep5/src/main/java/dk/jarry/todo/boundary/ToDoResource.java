package dk.jarry.todo.boundary;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import dk.jarry.todo.entity.ToDo;

@Path("/todos")
@Tag(name = "Todo Resource", description = "All Todo Operations")
public class ToDoResource {

    @Inject
	ToDoService toDoService;
    
    @POST
    @Fallback(fallbackMethod = "createOverloaded")
    @Timeout(value = 1,unit = ChronoUnit.SECONDS)
	@Counted(name = "createPerformed", description = "How many create have been performed.")
	@Timed(name = "createTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	@Operation(description = "Create a new todo")
	public Response create(ToDo toDo) {
    	try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
		return Response.status(201).entity(toDoService.create(toDo)).build();
	}
    
    public Response createOverloaded(ToDo toDo) {
    	return Response.status(503).header("reason", "problems...").build();
    }
    

	@GET
	@Path("{id}")
	@Operation(description = "Get a specific todo by id")
	public ToDo read(@PathParam("id") Long id) {
		return toDoService.read(id);
	}

	@PUT
	@Path("{id}")
	@Operation(description = "Update an exiting todo")
	public ToDo update(@PathParam("id") Long id, ToDo toDo) {
		return toDoService.update(id, toDo);
	}

	@DELETE
	@Path("{id}")	
	@Operation(description = "Delete a specific todo by id")
	public void delete(@PathParam("id") Long id) {
		toDoService.delete(id);
	}

	@GET
	@Bulkhead(value = 2, waitingTaskQueue = 2) 
	@Operation(description = "Get all the todos")
	public List<ToDo> list( //
		@DefaultValue("0") @QueryParam("from") Integer from, //
		@DefaultValue("100") @QueryParam("limit") Integer limit) {
		return toDoService.list(from, limit);
	}

}