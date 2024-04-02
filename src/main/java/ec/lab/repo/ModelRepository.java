package ec.lab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.lab.domain.Model;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByName(String name);
    List<Model> findAll();

}
