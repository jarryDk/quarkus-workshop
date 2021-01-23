package dk.jarry.todo.boundary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import dk.jarry.todo.entity.ToDo;

@RequestScoped
public class ToDoService {
   
    public ToDoService() {
    }

    @Transactional
    public ToDo create(ToDo toDo) {
       
        toDo.persist();

        return toDo;
    }

    @Transactional
    public ToDo read(Object id) {
        ToDo toDo =  ToDo.findById(id);
        if (toDo != null) {
            return toDo;
        } else {
            throw new WebApplicationException( //
                    "ToDo with id of " + id + " does not exist.", //
                    Response.Status.NOT_FOUND);
        }
    }

    @Transactional
    public ToDo update(Long id, ToDo toDo) {
        
        if (ToDo.findById(id) != null) {
            return toDo.getEntityManager().merge(toDo);
        } else {
            throw new WebApplicationException( //
                    "ToDo with id of " + id + " does not exist.", //
                    Response.Status.NOT_FOUND);
        }
    }

    @Transactional
    public void delete(Object id) {

        ToDo toDo = ToDo.findById(id);

        if (toDo != null) {
            toDo.delete();
        } else {
            throw new WebApplicationException( //
                    "ToDo with id of " + id + " does not exist.", //
                    Response.Status.NOT_FOUND);
        }
    }

    @Transactional
    public List<ToDo> list(Integer from, Integer limit) {
        return ToDo.findAll() //
                    .page(from, limit) //
                    .list();
    }

    @Provider
	public static class ErrorMapper implements ExceptionMapper<Exception> {

		@Override
		public Response toResponse(Exception exception) {
			int code = 500;
			if (exception instanceof WebApplicationException) {
				code = ((WebApplicationException) exception).getResponse().getStatus();
			}
			return Response.status(code).entity(Json.createObjectBuilder() //
					.add("error", (exception.getMessage() != null ? exception.getMessage() :"")) //
					//.add("stackTrace", stackTrace(exception)) //
					.add("code", code).build()).build();
		}

		String stackTrace(Exception exception) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			exception.printStackTrace(printWriter);
			return writer.toString();
		}

	}

}
