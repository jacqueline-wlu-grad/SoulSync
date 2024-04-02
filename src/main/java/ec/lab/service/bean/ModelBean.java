
package ec.lab.service.bean;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.lab.dao.ModelDao;
import ec.lab.domain.Model;
import ec.lab.repo.ModelRepository;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.neighboursearch.LinearNNSearch;

@Service
public class ModelBean{

@Autowired
   private ModelRepository modelDao;

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
       }catch(Exception e) {
           System.out.println("Error parsing query data: " + e.getMessage());

       }
       return -99999999;
   }
   
   
   public Instances topMatches(String query) {
	   Instances nearest = null;
       try {
           // Retrieve the weka_regression object from the Model entity
    	   Instances neighbourhood =  DataSource.read("query_data.arff");
           Instance queryInstance;
           Instances queryValues = createInstancesFromQueryData(query);

           queryInstance = queryValues.get(0);
           for (int i = 0; i < query.split(",").length; i++) {
               queryInstance.setValue(i, Double.parseDouble(query.split(",")[i]));
           }
       
           Instances trainingDataSet = DataSource.read("house.arff");
   		
   		
   		trainingDataSet.setClassIndex(trainingDataSet.numAttributes()-1);
   		
   		LinearNNSearch nn = new LinearNNSearch(trainingDataSet);
   		
   		//linear regression algorithm setting and configure
   		
   	    //get coefficients of the linear regression model
   
   	
   		nearest = nn.kNearestNeighbours(queryInstance, 3);
   	    
           for(Instance i : queryValues) {
        	   System.out.println(i.toString());
        	   
           }
           for(double i : nn.getDistances()) {
        	   System.out.println(i);
           }
           

       }catch(Exception e) {
           System.out.println("Error parsing query data: " + e.getMessage());

       }
       return nearest;
   }
   private static Instances createInstancesFromQueryData(String queryData) throws Exception {
       String arffContent = "@relation QueryData\n\n" +
               "@attribute attr1 numeric\n" +
               "@attribute attr2 numeric\n" +
               "@attribute attr3 numeric\n" +
               "@attribute attr4 numeric\n" +
               "@attribute attr5 numeric\n" +
               "@attribute class numeric\n\n" +
               "@data\n" +
               queryData;

    // Replace try-with-resources with standard try-catch
       BufferedWriter writer = null;
       try {
           writer = new BufferedWriter(new FileWriter("query_data.arff"));
           writer.write(arffContent);
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
   
   
 
}
