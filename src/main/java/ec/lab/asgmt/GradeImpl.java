package ec.lab.asgmt;

import org.springframework.stereotype.Service;

//@Service
//public class GradeService {
//	
//	
//	
//	
//
//	public String predict(int score) {
//		if (score >= 70)
//			return "pass";
//		else
//			return "fail";
//	}
//}

//import org.springframework.stereotype.Service;
//
//@Service
//public class GradeService implements GradeServiceI {
//
//	public String getGrade(int score) {
//		String result ="F";
//		if (score >= 90) 
//			result = "A+";
//		else if (score >= 85)
//			result = "A";
//		else if (score >= 80)
//			result = "A-";
//		else if (score >= 77)
//			result = "B+";
//		else if (score >= 73)
//			result = "B";
//		else if (score >= 70)
//			result = "B-";
//
//		return result;
//	}
//
//}




@Service
public class GradeImpl implements Grade {
    private String name;
    private int[] gradeBoundary = {100, 90, 85, 80, 77, 73, 70, 0};
    private String[] letterGrade = {"A+", "A", "A-", "B+", "B", "B-", "F"};
    private int count = 8;

    @Override
    public String getLetterGrade(int numerical_grade) {
        int i = 0;
        int j = count - 1;
        while (i < j) {
            int mid = (i + j) / 2;
            if (numerical_grade >=gradeBoundary[mid]) {
                j = mid;
            } else {
                i = mid + 1;
            }
        }
        if(j != 0) {
        	return letterGrade[j-1];
        } else {
        return letterGrade[j];
        }
        
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getGradeBoundary() {
        return gradeBoundary;
    }

    public void setGradeBoundary(int[] gradeBoundary) {
        this.gradeBoundary = gradeBoundary;
    }

    public String[] getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String[] letterGrade) {
        this.letterGrade = letterGrade;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

