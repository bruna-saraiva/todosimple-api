package com.brunamarcelly.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brunamarcelly.todosimple.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Quero buscar uma lista de tasks de um usuario, ou seja temos que encontrar uma lista
    // a partir de um user_id

    List<Task> findByUser_Id(Long id); // o metodo do spring vai procurar User na classe Task
    // mas o id está na classe User, ai atraves da instancia de user dentro de task vc vai entrar em User
    // E vai encontrar o id
    
    //JPQL
    // @Query(value = "SELECT t FROM Task t WHERE t.user.id =:id")
    // List<Task> findByUser_Id(@Param ("id") Long id);

    //SQL PURO
    // @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    // List<Task> findByUser_Id(@Param ("id") Long id);
}
