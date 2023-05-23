package net.myproject.todo.Service.Impl;

import net.myproject.todo.Dto.TodoDto;
import net.myproject.todo.Entity.Todo;
import net.myproject.todo.Exception.ResourceNotFoundException;
import net.myproject.todo.Repository.TodoRepository;
import net.myproject.todo.Service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TodoDto saveAndUpdateTodo(TodoDto todoDto) {

        if (todoDto != null){
            if(todoDto.getId() != null){
               Optional<Todo> todo = todoRespository.findById(todoDto.getId());
                if(todo != null){
                    if (todoDto.getTitle() != null){
                        todo.get().setTitle(todoDto.getTitle());
                    }
                    if(todoDto.getDescription() != null){
                        todo.get().setDescription(todoDto.getDescription());
                    }
                    todo.get().setCompleted(todoDto.isCompleted());

                    todoRespository.save(todo.get());
                }
            } else {
                Todo todos = new Todo();
                todos.setTitle(todoDto.getTitle());
                todos.setDescription(todoDto.getDescription());
                todos.setCompleted(todoDto.isCompleted());
                todoRespository.save(todos);
            }
        }
        return todoDto;
    }
//    @Override
//    public TodoDto addTodo(TodoDto todoDto) {
//==============================================================================
        //Convert todo dto onto todo jpa entity
//        Todo todo = new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());
//==========================================================================
//        Todo todo = modelMapper.map(todoDto, Todo.class);
//
//        // Todo jpa entity
//        Todo savedTodo = todoRespository.save(todo);
//==========================================================================
        //Convert saved todo jpa entity object into todo object

//        TodoDto savedTodoDto = new TodoDto();
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle(savedTodo.getTitle());
//        if (savedTodo.getTitle() != null){
//            throw new ResourceNotFoundException("Description should be present");
//        }
     /*   savedTodoDto.setDescription(savedTodo.getDescription());
        savedTodoDto.setCompleted(savedTodo.isCompleted());*/
        //====================================================================
//        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
//        return savedTodoDto;
//    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodo() {
        List<Todo> todos = todoRespository.findAll();
        return todos.stream().map((todo)-> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo updatedTodo = todoRespository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todoRespository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        //Retrieve todo using id
        Todo todo = todoRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : "));

        // Update todo obj and save
        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRespository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : "));

        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRespository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
