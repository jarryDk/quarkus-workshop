package dk.jarry.todo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class ToDo {

    @Id
    @SequenceGenerator( //
            name = "todosSequence", //
            sequenceName = "todos_id_seq", //
            allocationSize = 1, //
            initialValue = 10)
    @GeneratedValue( //
            strategy = GenerationType.SEQUENCE, //
            generator = "todosSequence")
    @Schema(readOnly = true)
    public Long id;
    public String subject;
    public String body;
}
