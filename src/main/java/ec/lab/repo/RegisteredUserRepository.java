package ec.lab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.lab.domain.RegisteredUser;

import java.util.List;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
//    List<RegisteredUser> findByUsername(String name);
    List<RegisteredUser> findAll();

	RegisteredUser findByUsername(String username);

	RegisteredUser findByEmail(String email);
}
