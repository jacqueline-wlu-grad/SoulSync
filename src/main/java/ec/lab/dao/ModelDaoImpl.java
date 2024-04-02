


package ec.lab.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.lab.domain.Model;
import ec.lab.repo.ModelRepository;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ModelDaoImpl implements ModelDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ModelRepository repository;
    
    @Override
    public void saveModel(Model model) {
        entityManager.persist(model);
    }

    @Override
    public Model findByName(String modelname) {

    	 List<Model> users = repository.findByName(modelname);
         if (users.size() == 0) return null;
         else return users.get(0);
    }
    
}
