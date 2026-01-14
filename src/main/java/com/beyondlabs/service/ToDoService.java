package com.beyondlabs.service;

import com.beyondlabs.model.Status;
import com.beyondlabs.model.Todo;
import com.beyondlabs.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ToDoService {
    
    private final TodoRepository todoRepository;
    
    
    private final Map<Long, Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong();
    
    public ToDoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public Todo createTodo(Todo todo) {
        todo.setId(idGen.incrementAndGet());
        todo.setCreatedAt(Instant.now());
        todo.setUpdatedAt(Instant.now());
        store.put(todo.getId(), todo);
        return todo;
    }
    
    public List<Todo> getTodosByUserId(String userId) {
        return store.values().stream()
                       .filter(t -> t.getUserid().equals(userId))
                       .toList();
    }
    
    public Todo updateStatus(Long id, Status status) {
        Todo todo = store.get(id);
        if (todo == null) {
            throw new RuntimeException("Todo not found");
        }
        todo.setStatus(status);
        todo.setUpdatedAt(Instant.now());
        return todo;
    }
    
}
