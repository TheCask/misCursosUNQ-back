package ar.edu.unq.misCursosUNQ;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.edu.unq.misCursosUNQ.Exceptions.SeasonException;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CourseTest {
    
	String courseCode = "cf11";
	String courseShift = "Mañana";
	Boolean courseIsOpen = false;
	Integer courseYear = 2020;
	String courseSeason = "2c";
	String incorrectPatternCourseSeason = "p44";
	String courseLocation = "San Fernando";
	
	Course aCourse;
	
	@Mock
	Subject subjectMock;
	String subjectCode = "80000-CyT1y2";
	
	@Mock
	Student inscriptedStudentMock;
	
	@Mock
	Student otherInscriptedStudentMock;
	
	@Mock
	Student nonInscriptedStudentMock;
	
	@Mock
	List<User> teacherListMock;
	
	@Mock
	List<Student> studentListMock;
	
	@Mock
	List<Lesson> lessonListMock;
	
	@Mock
	List<Evaluation> evaluationListMock;

	@BeforeEach
	public void setup() throws SeasonException {
		Mockito.when(subjectMock.getCode()).thenReturn(subjectCode);
		
		aCourse = new Course(courseCode, subjectMock, courseYear, courseSeason);
	}
	
	@Test
    public void setCourseCodeSetsUppercaseCode() {
		assertThat(aCourse.getCourseCode().equals(courseCode.toUpperCase()));
	}
	
	@Test
    public void setSubjectSetsCourseFullCode() {
		String courseFullCode = "80000-"+aCourse.getCourseCode()+"-CyT1y2";
		assertThat(aCourse.getCourseFullCode().equals(courseFullCode));
	}
	
	@Test
    public void setCourseSeasonSetsUppercaseSeason() {
		assertThat(aCourse.getCourseSeason().equals(courseSeason.toUpperCase()));
	}
	
//	@Test
//	public void setIncorrectPatternSeasonThrowsException() {
//	    
//		aCourse.setCourseSeason(incorrectPatternCourseSeason);
//		
//		assertThat(aCourse.getCourseSeason().equals(incorrectPatternCourseSeason));
//		
//		Exception exception = Assertions.assertThrows(SeasonException.class, () -> {
//			aCourse.setCourseSeason(incorrectPatternCourseSeason);
//		});
//		
//	    String expectedMessage = "Season format is one digit followed by one character";
//	    String actualMessage = exception.getMessage();
//	 
//	    assertThat(actualMessage.contains(expectedMessage));
//	}  
}