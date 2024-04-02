package ec.lab.asgmt;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Service;


@Service
public class RankImpl implements Rank {
    private String name;
    private Integer[] scores = {71, 71, 85, 70, 85, 99, 70, 79, 89, 83, 96, 85, 82, 84, 96, 77, 89, 81, 71, 90, 89, 71, 99, 99, 84, 74, 90, 75, 73, 86};
    private Grade grade;
    
    public RankImpl() {
    }

    @Override
    public String getGrade(int score) {
        return grade.getLetterGrade(score);
    }

    @Override
    public int getRank(int score) {
        Arrays.sort(scores, Collections.reverseOrder());
        int rank = Arrays.binarySearch(scores, score, Collections.reverseOrder()) + 1;
        return rank > 0 ? rank : Math.abs(rank);
    }

    // Setters and getters for name and grade properties
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

}
