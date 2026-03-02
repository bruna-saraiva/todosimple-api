package com.brunamarcelly.todosimple.controllers;

import java.net.URI;

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

import com.brunamarcelly.todosimple.models.User;
import com.brunamarcelly.todosimple.models.User.CreateUser;
import com.brunamarcelly.todosimple.models.User.UpdateUser;
import com.brunamarcelly.todosimple.services.UserService;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    //ex.: localhost:8080/user/1
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj); // ok significa que vai retornar um status 200(sem erros), body é o corpo do dado que foi pedido, 
        // dentro dele é o dado que vai ser passado
    }

    @PostMapping 
    @Validated(CreateUser.class) // o validated la em cima indica que alguma coisa vai ter validacao 
    // ja o validated daqui indica que deve-se validar apenas essa interface do user
    public ResponseEntity<Void> create(@Valid @RequestBody User obj){
        this.userService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest() // pega a url que o cliente usou para fazer o post
        .path("/{id}").buildAndExpand(obj.getId()).toUri(); // adiciona /id a url, pega o id real do usuario e salva no lugar de {id} 
        //toUri empacota esse texro na url
        return ResponseEntity.created(uri).build(); // created é para retornar o status 201 e guarda o uri m um Location no header 
        // o build serve para finalizar e empacotar o user
      }
    
    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id); //todo pq precisa desse setId aqui
        this.userService.update(obj);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();

    }


    
}