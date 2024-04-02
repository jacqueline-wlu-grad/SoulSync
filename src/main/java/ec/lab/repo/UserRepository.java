package ec.lab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.lab.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String name);
    List<User> findAll();
    List<User> findBySex(String sex);
}
