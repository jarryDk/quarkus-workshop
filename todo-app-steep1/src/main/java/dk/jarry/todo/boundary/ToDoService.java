package dk.jarry.todo.boundary;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ToDoService {
	
	public ToDoService() {
	}
	
	public String getMessage() {
		return "Hello for " + this.getClass().getSimpleName();
	}

}
