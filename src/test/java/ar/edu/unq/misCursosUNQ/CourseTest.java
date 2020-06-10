package ar.edu.unq.misCursosUNQ;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.edu.unq.misCursosUNQ.Exceptions.LessonException;
import ar.edu.unq.misCursosUNQ.Exceptions.SeasonException;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CourseTest {
    
	String courseCode = "cf11";
//	String courseShift = "Mañana";
//	Boolean courseIsOpen = false;
	Integer courseYear = 2020;
	String courseSeason = "2c";
//	String incorrectPatternCourseSeason = "p44";
//	String courseLocation = "San Fernando";
	
	Course aCourse;
	
	@Mock
	Subject subjectMock;
	String subjectCode = "80000-CyT1y2";
	
	@Mock
	Lesson lessonMock;
	
	@Mock
	Student inscriptedStudentMock;
	
	@Mock
	Student nonAttendantStudentMock;
	
	@Mock
	Student nonInscriptedStudentMock;
	
//	@Mock
//	List<User> teacherListMock;
	
	@Mock
	List<Student> attendedlessonStudentListMock;
	
	@Mock
	List<Student> courseStudentListMock;
	
//	@Mock
//	List<Lesson> lessonListMock;
//	
//	@Mock
//	List<Evaluation> evaluationListMock;

	@BeforeEach
	public void setup() throws SeasonException {
		when(subjectMock.getCode()).thenReturn(subjectCode);
		
		aCourse = new Course(courseCode, subjectMock, courseYear, courseSeason);
		
		when(inscriptedStudentMock.isInscriptedInCourse(aCourse)).thenReturn(true);
		when(nonInscriptedStudentMock.isInscriptedInCourse(aCourse)).thenReturn(false);
	}
	
	@Test
    public void setCourseCodeSetsUppercaseCode() {
		assertThat(aCourse.getCourseCode()).isEqualTo(courseCode.toUpperCase());
	}
	
	@Test
    public void setSubjectSetsCourseFullCode() {
		String courseFullCode = "80000-"+aCourse.getCourseCode()+"-CyT1y2";
		assertThat(aCourse.getCourseFullCode()).isEqualTo(courseFullCode);
	}
	
	@Test
    public void setCourseSeasonSetsUppercaseSeason() {
		assertThat(aCourse.getCourseSeason()).isEqualTo(courseSeason.toUpperCase());
	}
	
	@Test
	public void addStudentCheckForStudentInCourse() {
		aCourse.addStudent(nonInscriptedStudentMock);
		aCourse.addStudent(inscriptedStudentMock);
		
		verify(nonInscriptedStudentMock).isInscriptedInCourse(aCourse);
		verify(inscriptedStudentMock).isInscriptedInCourse(aCourse);
	}
	
	@Test
	public void addStudentNotInscriptedAddsStudentToCourse() {
		aCourse.addStudent(nonInscriptedStudentMock);
		
		assertThat(aCourse.getStudents()).hasSize(1);
		assertThat(aCourse.getStudents()).contains(nonInscriptedStudentMock);
		
		// Replaces student list in course by a Mock list
		aCourse.setStudents(courseStudentListMock);
		
		aCourse.addStudent(nonInscriptedStudentMock);
		
		verify(courseStudentListMock).add(nonInscriptedStudentMock);
	}
	
	@Test
	public void addStudentNotInscriptedAddsCourseToStudent() {
		aCourse.addStudent(nonInscriptedStudentMock);
		
		verify(nonInscriptedStudentMock).signOnCurse(aCourse);
	}
	
	@Test
	public void addStudentAlreadyInscriptedDoesNothing() {
		aCourse.addStudent(inscriptedStudentMock);
		
		assertThat(aCourse.getStudents()).hasSize(0);
		verify(inscriptedStudentMock, atMost(0)).signOnCurse(aCourse);
		
		// Replaces student list in course by a Mock list
		aCourse.setStudents(courseStudentListMock);
		
		aCourse.addStudent(inscriptedStudentMock);
		
		verify(courseStudentListMock, atMost(0)).add(inscriptedStudentMock);
	}
	
	@Test
	public void removeStudentInscriptedRemovesStudentFromCourse() {
		aCourse.addStudent(nonInscriptedStudentMock);
		aCourse.removeStudent(nonInscriptedStudentMock);
		
		assertThat(aCourse.getStudents()).isEmpty();
	}
	
	@Test
	public void removeStudentInscriptedRemovesCourseFromStudent() {
		aCourse.addStudent(nonInscriptedStudentMock);
		
		aCourse.removeStudent(nonInscriptedStudentMock);
		
		verify(nonInscriptedStudentMock).signOffCurse(aCourse);
	}
	
	@Test
	public void removeStudentNotIscriptedInCourseDoesNothing() {
		aCourse.removeStudent(nonInscriptedStudentMock);
		
		verify(nonInscriptedStudentMock, atMost(0)).signOffCurse(aCourse);;
	}
	
	
	@Test
	public void addLessonWithAttendStudentNotInCourseThrowsException() {
		
		when(lessonMock.getAttendantStudents()).thenReturn(attendedlessonStudentListMock);
		when(attendedlessonStudentListMock.contains(nonInscriptedStudentMock)).thenReturn(true);
		
		Exception exception = Assertions.assertThrows(LessonException.class, () -> {
			aCourse.addLesson(lessonMock);
		});
		
		assertThatExceptionOfType(LessonException.class).isThrownBy(() -> aCourse.addLesson(lessonMock));  
		
	    String expectedMessage = "There are at least one attendant student not taken this course";
	    String actualMessage = exception.getMessage();
	 
	    assertThat(actualMessage).isEqualTo(expectedMessage);
	}  
}