package de.fh.rosenheim.aline.repository;

import de.fh.rosenheim.aline.model.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    Iterable<User> findByDivision(String division);
}
