package ar.edu.unq.misCursosUNQ;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.atMost;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StudentTest {
    
	Student aStudent;
	String firstName = "Jacques";
	String lastName = "Derrida";
	String email = "derridaJacques@deconstruction.com";
	Integer dNI = 12345678;
	Integer fileNumber = 12345;
	
	@Mock
	Course takenCourseMock;
	
	@Mock
	Course notTakenCourseMock;
	
	@Mock
	Lesson attendedLessonMock;
	
	@Mock
	Lesson unAttendedLessonMock;
	
	@Mock
	List<Lesson> lessonListMock;
	
	@Mock
	List<Course> courseListMock;

	@BeforeEach
	public void setup() {
		aStudent = new Student(firstName, lastName, dNI, email, fileNumber);
		aStudent.setTakenCourses(courseListMock);
		aStudent.setAttendedLessons(lessonListMock);
		
		Mockito.when(lessonListMock.contains(attendedLessonMock)).thenReturn(true);
		Mockito.when(lessonListMock.contains(unAttendedLessonMock)).thenReturn(false);
		
		Mockito.when(courseListMock.contains(takenCourseMock)).thenReturn(true);
		Mockito.when(courseListMock.contains(notTakenCourseMock)).thenReturn(false);
	}
	
	@Test
    public void signOnCourseChecksForCoursesInStudent() {
		aStudent.signOnCurse(takenCourseMock);
		aStudent.signOnCurse(notTakenCourseMock);
		
		Mockito.verify(courseListMock).contains(takenCourseMock);
		Mockito.verify(courseListMock).contains(notTakenCourseMock);
	}
	
	@Test
    public void signOnCourseNotPreviouslyInscriptedAddsCourseToStudent() {
		aStudent.signOnCurse(notTakenCourseMock);
		
		Mockito.verify(courseListMock).add(notTakenCourseMock);
	}
	
	@Test
    public void signOnCoursePreviouslyAssignedDoesNothing() {
		aStudent.signOnCurse(takenCourseMock);
		
		Mockito.verify(courseListMock, atMost(0)).add(takenCourseMock);
	}
	
	@Test
	public void inscriptedCourseIsInscriptedCourse() {
		
		assertThat(aStudent.isInscriptedInCourse(takenCourseMock));
	}
	
	@Test
	public void notInscriptedCourseIsNotInscriptedCourse() {
		assertThat(!aStudent.isInscriptedInCourse(notTakenCourseMock));
	}
	
	@Test
    public void signOffCoursePreviouslyInscriptedRemovesCourseFromUser() {
		aStudent.signOffCurse(takenCourseMock);
		
		Mockito.verify(courseListMock).remove(takenCourseMock);
	}
	
	@Test
    public void signOffCourseNotPreviouslyInscriptedDoesNothing() {
		aStudent.signOffCurse(notTakenCourseMock);
		
		Mockito.verify(courseListMock, atMost(0)).remove(notTakenCourseMock);
	}
	
	@Test
    public void assignCourseChecksForCorseInUser() {
		aStudent.signOnCurse(takenCourseMock);
		aStudent.signOnCurse(notTakenCourseMock);
		
		Mockito.verify(courseListMock).contains(takenCourseMock);
		Mockito.verify(courseListMock).contains(notTakenCourseMock);
	}
	
	@Test
    public void attendLessonNotPreviouslyAttendedAddsLessonToStudent() {
		aStudent.attendLesson(unAttendedLessonMock);
		
		Mockito.verify(lessonListMock).add(unAttendedLessonMock);
	}
	
	@Test
    public void attendLessonPreviouslyAttendedDoesNothing() {
		aStudent.attendLesson(attendedLessonMock);
		
		Mockito.verify(lessonListMock, atMost(0)).add(attendedLessonMock);
	}
	
	@Test
	public void attendLessonIsAttendedLesson() {
		
		assertThat(aStudent.isAttendedLesson(attendedLessonMock));
	}
	
	@Test
	public void unAttendLessonIsNotAttendedLesson() {
		
		assertThat(!aStudent.isAttendedLesson(unAttendedLessonMock));
	}
	
	@Test
    public void unAssignCoursePreviouslyAssignedRemovesCourseFromUser() {
		aStudent.unattendLesson(attendedLessonMock);
		
		Mockito.verify(lessonListMock).remove(attendedLessonMock);
	}
	
	@Test
    public void unAssignCourseNotPreviouslyAssignedDoesNothing() {
		aStudent.unattendLesson(unAttendedLessonMock);
		
		Mockito.verify(lessonListMock, atMost(0)).remove(unAttendedLessonMock);
	}
}