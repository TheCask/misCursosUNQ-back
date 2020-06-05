package ar.edu.unq.misCursosUNQ;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JobDetailTest {
    
	JobDetail jobDetail;
	JobDetail otherJobDetail;
	String aGradeTitle;
	String aPosGradeTitle;
	
	@BeforeEach
	public void setup() {
		jobDetail = new JobDetail();
		otherJobDetail = new JobDetail();
	    aGradeTitle = "This is a grade title";
	    aPosGradeTitle = "This is a posgrade title";
	    jobDetail.addGradeTitle(aGradeTitle);
	    jobDetail.addPosGradeTitle(aPosGradeTitle);
	}
	
    @Test
    public void addGradeTitle() { assertTrue(jobDetail.getGradeTitles().contains(aGradeTitle));}

    @Test
    public void removeGradeTitle() { 
    	jobDetail.removeGradeTitle(aGradeTitle);
    	assertFalse(jobDetail.getGradeTitles().contains(aGradeTitle));
    	assertTrue(jobDetail.getGradeTitles().isEmpty());
    }
    
    @Test
    public void addPosGradeTitle() { assertTrue(jobDetail.getPosGradeTitles().contains(aPosGradeTitle));}
    
    @Test
    public void removePosGradeTitle() { 
    	jobDetail.removePosGradeTitle(aPosGradeTitle);
    	assertFalse(jobDetail.getPosGradeTitles().contains(aPosGradeTitle));
    	assertTrue(jobDetail.getPosGradeTitles().isEmpty());
    }
    
    @Test
    public void jobDetailEquals() {
    	assertTrue(jobDetail.equals(jobDetail));
    	assertFalse(jobDetail.equals(otherJobDetail));
    }
}