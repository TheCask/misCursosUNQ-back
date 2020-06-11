package ar.edu.unq.misCursosUNQ;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
		
		when(lessonListMock.contains(attendedLessonMock)).thenReturn(true);
		when(lessonListMock.contains(unAttendedLessonMock)).thenReturn(false);
		
		when(courseListMock.contains(takenCourseMock)).thenReturn(true);
		when(courseListMock.contains(notTakenCourseMock)).thenReturn(false);
	}
	
	@Test
    public void signOnCourseChecksForCoursesInStudent() {
		aStudent.signOnCourse(takenCourseMock);
		aStudent.signOnCourse(notTakenCourseMock);
		
		verify(courseListMock).contains(takenCourseMock);
		verify(courseListMock).contains(notTakenCourseMock);
	}
	
	@Test
    public void signOnCourseNotPreviouslyInscriptedAddsCourseToStudent() {
		aStudent.signOnCourse(notTakenCourseMock);
		
		verify(courseListMock).add(notTakenCourseMock);
	}
	
	@Test
    public void signOnCoursePreviouslyAssignedDoesNothing() {
		aStudent.signOnCourse(takenCourseMock);
		
		verify(courseListMock, atMost(0)).add(takenCourseMock);
	}
	
	@Test
	public void inscriptedCourseIsInscriptedCourse() {
		
		assertThat(aStudent.isInscriptedInCourse(takenCourseMock)).isTrue();
	}
	
	@Test
	public void notInscriptedCourseIsNotInscriptedCourse() {
		assertThat(aStudent.isInscriptedInCourse(notTakenCourseMock)).isFalse();
	}
	
	@Test
    public void signOffCoursePreviouslyInscriptedRemovesCourseFromUser() {
		aStudent.signOffCourse(takenCourseMock);
		
		verify(courseListMock).remove(takenCourseMock);
	}
	
	@Test
    public void signOffCourseNotPreviouslyInscriptedDoesNothing() {
		aStudent.signOffCourse(notTakenCourseMock);
		
		verify(courseListMock, atMost(0)).remove(notTakenCourseMock);
	}
	
	@Test
    public void assignCourseChecksForCorseInUser() {
		aStudent.signOnCourse(takenCourseMock);
		aStudent.signOnCourse(notTakenCourseMock);
		
		verify(courseListMock).contains(takenCourseMock);
		verify(courseListMock).contains(notTakenCourseMock);
	}
	
	@Test
    public void attendLessonNotPreviouslyAttendedAddsLessonToStudent() {
		aStudent.attendLesson(unAttendedLessonMock);
		
		verify(lessonListMock).add(unAttendedLessonMock);
	}
	
	@Test
    public void attendLessonPreviouslyAttendedDoesNothing() {
		aStudent.attendLesson(attendedLessonMock);
		
		verify(lessonListMock, atMost(0)).add(attendedLessonMock);
	}
	
	@Test
	public void attendLessonIsAttendedLesson() {
		
		assertThat(aStudent.isAttendedLesson(attendedLessonMock)).isTrue();
	}
	
	@Test
	public void unAttendLessonIsNotAttendedLesson() {
		
		assertThat(aStudent.isAttendedLesson(unAttendedLessonMock)).isFalse();
	}
	
	@Test
    public void unAssignCoursePreviouslyAssignedRemovesCourseFromUser() {
		aStudent.unattendLesson(attendedLessonMock);
		
		verify(lessonListMock).remove(attendedLessonMock);
	}
	
	@Test
    public void unAssignCourseNotPreviouslyAssignedDoesNothing() {
		aStudent.unattendLesson(unAttendedLessonMock);
		
		verify(lessonListMock, atMost(0)).remove(unAttendedLessonMock);
	}
}