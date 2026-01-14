package com.beyondlabs.controller;

import com.beyondlabs.model.Status;
import com.beyondlabs.model.Todo;
import com.beyondlabs.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class TodoController {
    
    
    
    private final ToDoService toDoService;
    
    public TodoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }
    
    @PostMapping("todos")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        return ResponseEntity.ok(toDoService.createTodo(todo));
    }
    
    @GetMapping("todos/me")
    public ResponseEntity<List<Todo>> getMyTodos(@RequestParam String userId) {
        return ResponseEntity.ok(toDoService.getTodosByUserId(userId));
    }
    
    @GetMapping("todos/user/{userId}")
    public ResponseEntity<List<Todo>> getTodosByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(toDoService.getTodosByUserId(userId));
    }
    
    @PatchMapping("todos/{id}/status")
    public ResponseEntity<Todo> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        
        return ResponseEntity.ok(
                toDoService.updateStatus(id, Status.valueOf(body.get("status")))
        );
    }
}
