package ec.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.lab.service.bean.ModelBean;

@RestController
public class ModelController {

   @Autowired
   private ModelBean model;

    @GetMapping("/predict/{score}")
    public double predict(@PathVariable String score) {
    	System.out.println(score);
        return model.prediction(score);
    }

     @GetMapping("/matches/{score}")
    public String matches(@PathVariable String score) {
    	System.out.println(score);
        return model.topMatches(score).toString();
    }

}
