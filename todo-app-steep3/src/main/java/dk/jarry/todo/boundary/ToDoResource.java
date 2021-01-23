package dk.jarry.todo.boundary;

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

import dk.jarry.todo.entity.ToDo;

@Path("/todos")
public class ToDoResource {

    @Inject
	ToDoService toDoService;

	@POST
	public ToDo create(ToDo toDo) {
		return toDoService.create(toDo);
	}

	@GET
	@Path("{id}")
	public ToDo read(@PathParam("id") Long id) {
		return toDoService.read(id);
	}

	@PUT
	@Path("{id}")
	public ToDo update(@PathParam("id") Long id, ToDo toDo) {
		return toDoService.update(id, toDo);
	}

	@DELETE
	@Path("{id}")	
	public void delete(@PathParam("id") Long id) {
		toDoService.delete(id);
	}

	@GET
	public List<ToDo> list( //
		@DefaultValue("0") @QueryParam("from") Integer from, //
		@DefaultValue("100") @QueryParam("limit") Integer limit) {
		return toDoService.list(from, limit);
	}

}