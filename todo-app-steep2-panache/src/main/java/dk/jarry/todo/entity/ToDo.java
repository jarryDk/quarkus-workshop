package dk.jarry.todo.entity;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class ToDo extends PanacheEntity {

    public String subject;
    public String body;
}
