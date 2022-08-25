package dk.jarry.todo.boundary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import dk.jarry.todo.entity.ToDo;

@RequestScoped
public class ToDoService {

    @Inject
    EntityManager entityManager;

    public ToDoService() {
    }

    @Transactional
    public ToDo create(ToDo toDo) {
        if (toDo.id != null) {
            throw new WebApplicationException( //
                    "ToDo not valid.", //
                    Response.Status.BAD_REQUEST);
        }
        entityManager.persist(toDo);
        entityManager.flush();
        entityManager.refresh(toDo);

        return toDo;
    }

    @Transactional
    public ToDo read(Object id) {
        ToDo toDo = entityManager.find(ToDo.class, id);
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

        if (toDo.id == null) {
            toDo.id = id;
        }

        if (read(id) != null) {
            return entityManager.merge(toDo);
        } else {
            throw new WebApplicationException( //
                    "ToDo with id of " + id + " does not exist.", //
                    Response.Status.NOT_FOUND);
        }
    }

    @Transactional
    public void delete(Object id) {

        ToDo toDo = read(id);

        if (toDo != null) {
            entityManager.remove(toDo);
        } else {
            throw new WebApplicationException( //
                    "ToDo with id of " + id + " does not exist.", //
                    Response.Status.NOT_FOUND);
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<ToDo> list(Integer start, Integer limit) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ToDo> criteriaQuery = criteriaBuilder.createQuery(ToDo.class);

        Root<ToDo> from = criteriaQuery.from(ToDo.class);

        criteriaQuery.select(from);
        criteriaQuery.orderBy( //
                criteriaBuilder.asc(from.get("subject")), //
                criteriaBuilder.desc(from.get("id")));

        Query jpqlQuery = entityManager.createQuery(criteriaQuery);

        jpqlQuery.setFirstResult((start > 0 ? start - 1 : 0));
        jpqlQuery.setMaxResults((limit > 0 ? limit : 100));

        return jpqlQuery.getResultList();

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
                    .add("error", (exception.getMessage() != null ? exception.getMessage() : "")) //
                    // .add("stackTrace", stackTrace(exception)) //
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
