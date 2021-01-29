package dk.jarry.todo.control.jsonb;

import dk.jarry.todo.entity.ToDo;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ToDoDeserializer extends JsonbDeserializer<ToDo> {
    public ToDoDeserializer(){
        // pass the class to the parent.
        super(ToDo.class);
    }
}