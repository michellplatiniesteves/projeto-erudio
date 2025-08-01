package br.com.erudio.repository;

import br.com.erudio.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Person p set p.ativo = false where p.id =:id")
    void desablePerson(@Param("id")Long id);

    @Query("SELECT p from Person p where p.nome like %:nome%")
    Page findByName(String nome, PageRequest pageable);
}
