
package ec.lab.service.bean;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.lab.domain.Model;
import ec.lab.domain.User;
import ec.lab.domain.UserPreferences;
import ec.lab.repo.ModelRepository;
import ec.lab.repo.UserRepository;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.neighboursearch.LinearNNSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ModelBean {
    private static final Logger logger = LoggerFactory.getLogger(ModelBean.class);

	@Autowired
	private ModelRepository modelDao;
	@Autowired
	private UserRepository userDao;
	@Autowired 
	private UserBean userBean;

	public double prediction(String query) {
		try {
			
			// Retrieve the weka_regression object from the Model entity
			List<Model> model = modelDao.findByName("weka-lr");

			byte[] modelData = model.get(0).getObject();

			Instance queryInstance;
			Instances queryValues = createInstancesFromQueryData(query);

			queryInstance = queryValues.get(0);
			for (int i = 0; i < query.split(",").length; i++) {
				queryInstance.setValue(i, Double.parseDouble(query.split(",")[i]));
			}

			Classifier classifier = (Classifier) SerializationHelper.read(new ByteArrayInputStream(modelData));
			double prediction = classifier.classifyInstance(queryInstance);

			return prediction;
		} catch (Exception e) {
			System.out.println("Error parsing query data: " + e.getMessage());

		}
		return -99999999;
	}

	public List<User> topMatches(UserPreferences score, String sex, String orientation) {
		logger.info("Begin processing Matches");
		
		List<User> users = new ArrayList<>();
		Instances nearest = null;
		try {
			// Retrieve the weka_regression object from the Model entity
			Instance queryInstance;
			Instances queryValues = createInstancesFromPreferences(score);

			queryInstance = queryValues.get(0);
			Instances trainingDataSet = createInstancesFromUsersData(sex, orientation);

			trainingDataSet.setClassIndex(trainingDataSet.numAttributes() - 1);

			LinearNNSearch nn = new LinearNNSearch(trainingDataSet);


			nearest = nn.kNearestNeighbours(queryInstance, 5);

			for (Instance i : queryValues) {
				System.out.println(i.toString());

			}
			for (double i : nn.getDistances()) {
				System.out.println(i);
			}
			
			 // Your Weka Instances object
			for (int i = 0; i < nearest.numInstances(); i++) {
			    Instance instance = nearest.instance(i);
			    User user = parseFromInstance(instance);
			    users.add(user);
			    System.out.println(user);
			}

		} catch (Exception e) {
			System.out.println("Error parsing query data: " + e.getMessage());

		}
		logger.info("End processing Matches");

		return users;
	}
	
	public static User parseFromInstance(Instance instance) {
		User user = new User();
		user.setUsername(instance.stringValue(1));
        user.setFirstName(instance.stringValue(2));
        user.setLastName(instance.stringValue(3));
        user.setAge((int) instance.value(4));
        user.setSex(instance.stringValue(5));
        user.setBodyType(instance.stringValue(6));
        user.setDiet(instance.stringValue(7));
        user.setSmokes(instance.stringValue(8));
        user.setDrugs(instance.stringValue(9));
        user.setDrinks(instance.stringValue(10));
        user.setEthnicity(instance.stringValue(11));
        user.setHeight((int) instance.value(12));
        user.setJob(instance.stringValue(13));
        user.setEducation(instance.stringValue(14));
        user.setBio(instance.stringValue(15));
        user.setLocation(instance.stringValue(16));
        user.setImage(instance.stringValue(17));

        return user;
    }
	
	private Instances createInstancesFromQueryData(String queryData) throws Exception {
		String arffContent = "@relation QueryData\n\n" 
				+ "@attribute age numeric\n" 
				+ "@attribute sex string\n"
				+ "@attribute bodyType string\n" 
				+ "@attribute diet string\n"  
				
				+ "@attribute smokes string\n"  
				+ "@attribute drugs string\n" 
				+ "@attribute drinks string\n"

				+ "@attribute ethnicity string\n"
				+ "@attribute height numeric\n"
				+ "@attribute job string\n"
				+ "@attribute education string\n\n" 
				+ "@data\n" + queryData;
//		
	
		// Replace try-with-resources with standard try-catch
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("query_data.arff"));
			writer.write(arffContent.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return DataSource.read("query_data.arff");
	}

	
	private Instances createInstancesFromPreferences(UserPreferences pref ) throws Exception {
		List<UserPreferences> userpref = Arrays.asList(pref);
		StringBuilder arffContent = new StringBuilder();
		arffContent.append("@relation UserPref\n\n");

		// Define attributes
		arffContent.append("@attribute age numeric\n");
		arffContent.append("@attribute bodyType string\n");
		arffContent.append("@attribute diet string\n");
		
		arffContent.append("@attribute smokes string\n");
		arffContent.append("@attribute drugs string\n");
		arffContent.append("@attribute drinks string\n");
		
		arffContent.append("@attribute ethnicity string\n");
		arffContent.append("@attribute height numeric\n");
		arffContent.append("@attribute job string\n");
		arffContent.append("@attribute education string\n\n");
		// Add more attributes as needed...

		arffContent.append("\n@data\n");

		// Append data
		for (UserPreferences user : userpref) {
			arffContent.append(user.getAge()).append(",");
			arffContent.append("'").append(user.getBodyType()).append("',");
			arffContent.append("'").append(user.getDiet()).append("',");
			
			arffContent.append("'").append(user.getSmokes()).append("',");
			arffContent.append("'").append(user.getDrugs()).append("',");
			arffContent.append("'").append(user.getDrinks()).append("',");
			
			arffContent.append("'").append(user.getEthnicity()).append("',");
			arffContent.append(user.getHeight()).append(",");
			arffContent.append("'").append(user.getJob()).append("',");
			arffContent.append("'").append(user.getEducation()).append("'");
			
			arffContent.append("\n");
		}
		

	
		// Replace try-with-resources with standard try-catch
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("query_data.arff"));
			writer.write(arffContent.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return DataSource.read("query_data.arff");

	}

	private Instances createInstancesFromUsersData(String sex, String orientation) throws Exception {
		
		List<User> users = null;
		if("Bisexual".equals(orientation)) {
			users =  userDao.findAll();
		}
		else if("m".equals(sex) && "Straight".equalsIgnoreCase(orientation)) {
			users = userDao.findBySex("f");
		}
		else if("f".equals(sex) && "Straight".equalsIgnoreCase(orientation)) {
			users = userDao.findBySex("m");
		}
		else if("m".equals(sex) && "Gay".equalsIgnoreCase(orientation)) {
			users = userDao.findBySex(sex);
		}
		else if("f".equals(sex) && "Gay".equalsIgnoreCase(orientation)) {
			users = userDao.findBySex(sex);
		}
		else {
			System.out.println("Nobody likes you. Sorry!");
		}
		
		users.removeIf(x->x.getUsername().equalsIgnoreCase(userBean.getUserName()));
		StringBuilder arffContent = new StringBuilder();
		arffContent.append("@relation Users\n\n");

		// Define attributes
		arffContent.append("@attribute id numeric\n");
		arffContent.append("@attribute username string\n");
		arffContent.append("@attribute firstName string\n");
		arffContent.append("@attribute lastName string\n");
		arffContent.append("@attribute age numeric\n");
		arffContent.append("@attribute sex string\n");
		arffContent.append("@attribute bodyType string\n");
		arffContent.append("@attribute diet string\n");
		
		arffContent.append("@attribute smokes string\n");
		arffContent.append("@attribute drugs string\n");
		arffContent.append("@attribute drinks string\n");
		
		arffContent.append("@attribute ethnicity string\n");
		arffContent.append("@attribute height numeric\n");
		arffContent.append("@attribute job string\n");
		arffContent.append("@attribute education string\n");
		arffContent.append("@attribute bio string\n");
		arffContent.append("@attribute location string\n");

		arffContent.append("@attribute image string\n\n");

		// Add more attributes as needed...

		arffContent.append("\n@data\n");

		// Append data
		for (User user : users) {
			arffContent.append(user.getId()).append(",");
			arffContent.append("'").append(user.getUsername()).append("',");
			arffContent.append("'").append(user.getFirstName()).append("',");
			arffContent.append("'").append(user.getLastName()).append("',");
			arffContent.append(user.getAge()).append(",");
			arffContent.append("'").append(user.getSex()).append("',");
			arffContent.append("'").append(user.getBodyType()).append("',");
			arffContent.append("'").append(user.getDiet()).append("',");
			
			arffContent.append("'").append(user.getSmokes()).append("',");
			arffContent.append("'").append(user.getDrugs()).append("',");
			arffContent.append("'").append(user.getDrinks()).append("',");
			
			arffContent.append("'").append(user.getEthnicity()).append("',");
			arffContent.append(user.getHeight()).append(",");
			arffContent.append("'").append(user.getJob()).append("',");
			arffContent.append("'").append(user.getEducation()).append("',");
			arffContent.append("'").append(user.getBio()).append("',");
			arffContent.append("'").append(user.getLocation()).append("',");
			arffContent.append("'").append(user.getImage()).append("'");

			
			arffContent.append("\n");
		}

		// Write ARFF content to a file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.arff"))) {
			writer.write(arffContent.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DataSource.read("users.arff");

	}

	

}
