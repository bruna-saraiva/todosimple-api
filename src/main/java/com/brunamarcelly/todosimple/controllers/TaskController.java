package com.brunamarcelly.todosimple.controllers;

import java.net.URI;
import java.security.PublicKey;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brunamarcelly.todosimple.models.Task;
import com.brunamarcelly.todosimple.services.TaskService;
import com.brunamarcelly.todosimple.services.UserService;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    //GET - LISTAR APENAS UM ITEM
    // é uma entidade que vai ser retornada com o tipo dela: Task
    // passar o id
    // Atribuir o objeto encontrado pelo metodo do objeto servico findbyid ao objeto Task
    // retornar o obj
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId){
        this.userService.findById(userId);
        List<Task> objs = this.taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(objs);
        
      

    }


    //CREATE
    // retorno vazio. validar no parametro
    //usar o metodo create do taskService passando o objeto
    // Criar o uri com servlet, 
    // retorna o response com created(uri)
    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    //UPDATE
    // passar o objeto novo e o id do objeto a ser atualizado
    // setar o id para nao ter risco de ser outro id
    // usar a funcao de update no objeto
    // retornar o response entity
    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id){
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    //DELETE
    // passar o id da task que deve ser apagada
    // chamar a funcao delete do servico de task passando o id
    // retornar a entidade com no content e build
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    
}
//todo como as tasks se ligam com o usuario? 
