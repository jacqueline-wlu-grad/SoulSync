package ec.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.lab.asgmt.Grade;
import ec.lab.asgmt.Rank;

@RestController
public class GradeRankController {

    @Autowired
    private Grade gradeService;

    @Autowired
    private Rank rankService;
   

    @GetMapping("/grade/{score}")
    public String getGrade(@PathVariable int score) {
    	System.out.println(score);
        return gradeService.getLetterGrade(score);
    }

    @GetMapping("/rank/{score}")
    public int getRank(@PathVariable int score) {
    	System.out.println(score);

        return rankService.getRank(score);
    }

    @GetMapping("/grade-rank/{score}")
    public String getGradeAndRank(@PathVariable int score) {
    	System.out.println(score);

        return "Grade: " + gradeService.getLetterGrade(score) + ", Rank: " + rankService.getRank(score);
    }
}
