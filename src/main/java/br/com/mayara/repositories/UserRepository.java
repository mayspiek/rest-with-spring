package br.com.mayara.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mayara.model.Book;
import br.com.mayara.model.User;

@Repository
public interface UserRepository extends JpaRepository<Book, Long>{
    @Query("SELECT u FROM User WHERE u.userName =:userName")
    User findByUserName(@Param("userName") String userName);
}
