package ec.lab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.lab.domain.UserPreferences;

import java.util.List;

public interface UserPrefRepository extends JpaRepository<UserPreferences, Long> {
    List<UserPreferences> findByUsername(String name);
    List<UserPreferences> findAll();

}
