package com.brunamarcelly.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brunamarcelly.todosimple.models.User;
import com.brunamarcelly.todosimple.repositories.UserRepository;


@Service
public class UserService {
    // vamos implementar metodos, regras etc 

    @Autowired // faz a criação do construtor, é melhor usar isso pois estamos usando uma interface e nao da para instanciar
    private UserRepository userRepository;

    // buscar um usuario

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
            "Usuário não encontrado! Id:" + id + ", Tipo:" + User.class.getName()

        ));
        
    }

    @Transactional
    public User create(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir, pois há entidades relacionadas.");
        }
    }

}
