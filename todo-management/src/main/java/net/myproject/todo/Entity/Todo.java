package net.myproject.todo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "todos")

public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name="TODO_SEQ",sequenceName="TODO_SEQ",allocationSize=1)

    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private boolean completed;

}
