package ec.lab.dao;

import ec.lab.domain.Model;

public interface ModelDao {
	
	void saveModel(Model model);
	
    Model findByName(String modelname);
}
