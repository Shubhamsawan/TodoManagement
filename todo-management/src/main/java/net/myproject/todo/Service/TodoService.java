package net.myproject.todo.Service;

import net.myproject.todo.Dto.TodoDto;
import net.myproject.todo.Entity.Todo;

import java.util.List;

public interface TodoService {

    TodoDto saveAndUpdateTodo(TodoDto todoDto);

    TodoDto getTodo(Long id);

    List<TodoDto> getAllTodo();

    TodoDto updateTodo(TodoDto todoDto, Long id);

    void deleteTodo(Long id);

    TodoDto completeTodo(Long id);

    TodoDto  inCompleteTodo(Long id);



}
