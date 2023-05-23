package net.myproject.todo.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.myproject.todo.Dto.TodoDto;
import net.myproject.todo.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {


    @Autowired
    TodoService todoService;

    //url :  http://localhost:8080/api/todos
    @SecurityRequirement(
            name = "Basic"
    )
    @PreAuthorize("hasRole('ADMIN')")  //method level Security
    @PostMapping

    public ResponseEntity<TodoDto> saveAndUpdateTodo(@RequestBody TodoDto dto){
        TodoDto savedTodo = todoService.saveAndUpdateTodo(dto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    //url :  http://localhost:8080/api/todos/2
    @SecurityRequirement(
            name = "Basic"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
        TodoDto todoDto = todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Basic"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity <List<TodoDto>> getAllTodo(){
        List<TodoDto> todo = todoService.getAllTodo();
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    //url :  http://localhost:8080/api/todos/1
    @SecurityRequirement(
            name = "Basic"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoId){
        TodoDto updatedTodoDto = todoService.updateTodo(todoDto, todoId);
        return ResponseEntity.ok(updatedTodoDto);
    }

    // url :  http://localhost:8080/api/todos/1
    @SecurityRequirement(
            name = "Basic"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
         todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo deleted Successfully");
    }

    //Build complete Todo Rest api
    // url :  http://localhost:8080/api/todos/4/complete
    @SecurityRequirement(
            name = "Basic"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completedTodo(@PathVariable("id") Long todoId){
        TodoDto updatTodo = todoService.completeTodo(todoId);
        return ResponseEntity.ok(updatTodo);
    }

    @SecurityRequirement(
            name = "Basic"
    )
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/incomplete")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable("id") Long todoId){
        TodoDto updateTodo = todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(updateTodo);
    }

    @GetMapping("thirdParty")
    public String getHelloExtApi(){
        String uri = "https://www.boredapi.com/api/activity";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri,String.class);
        return (result);
    }

}

/*
{
    "title": "Learn Spring Boot",
    "description": "Learn Springboot with project",
    "completed": false
    }
 */

//Swagger doc url : http://localhost:8080/v3/api-docs
//Swagger UI url : http://localhost:8080/swagger-ui/index.html