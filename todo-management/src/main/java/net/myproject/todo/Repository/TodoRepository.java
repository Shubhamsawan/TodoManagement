package net.myproject.todo.Repository;


import net.myproject.todo.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
