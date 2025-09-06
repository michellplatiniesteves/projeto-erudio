package br.com.erudio.repository;

import br.com.erudio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(name = "select u from User u where u.userName =:userName ")
    User findByUserName(@Param("userName") String userName);
}
