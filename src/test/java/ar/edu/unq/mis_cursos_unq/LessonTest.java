package ar.edu.unq.mis_cursos_unq;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.edu.unq.mis_cursos_unq.exceptions.LessonException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LessonTest {
    
	private Lesson aLesson;
	
	@Mock
	private Course courseMock;
	
	@Mock
	private Student inscriptedStudentMock;
	
	@Mock
	private Student otherInscriptedStudentMock;
	
	@Mock
	private Student nonInscriptedStudentMock;

	@BeforeEach
	public void setUp() throws LessonException {
		aLesson = new Lesson(LocalDate.of(2020, 8, 16));
		aLesson.setCourse(courseMock);
		
		when(inscriptedStudentMock.isInscriptedInCourse(courseMock)).thenReturn(true);
		when(otherInscriptedStudentMock.isInscriptedInCourse(courseMock)).thenReturn(true);
		
		aLesson.setAttendance(inscriptedStudentMock);
	}
	
	@Test
    public void setAttendanceChecksForStudentInCourse() {
		verify(inscriptedStudentMock).isInscriptedInCourse(courseMock);
	}
	
	@Test
    public void setAttendanceOfIncriptedStudentAddsStudentToLesson() {
		assertThat(aLesson.getAttendantStudents()).contains(inscriptedStudentMock);
	}
	
	@Test
    public void setAttendanceOfIncriptedStudentAddsLessonToStudent() {
		verify(inscriptedStudentMock).attendLesson(aLesson);
	}
	
	@Test
	public void setAttendanceOfNonIncriptedStudentThrowsException() {
	    
		Exception exception = assertThrows(LessonException.class, () -> {
			aLesson.setAttendance(nonInscriptedStudentMock);
		});
		
	    String expectedMessage = "Student is not inscribed in lesson course";
	    String actualMessage = exception.getMessage();
	 
	    assertThat(actualMessage).contains(expectedMessage);
	}
	
	@Test
	public void setAttendanceOfAlreadyAttendStudentDoesNothing() throws LessonException {
		aLesson.setAttendance(inscriptedStudentMock);
		
		assertThat(aLesson.getAttendantStudents()).hasSize(1);
		verify(inscriptedStudentMock).attendLesson(aLesson);
	}
	
	@Test
	public void removeAttendanceOfAttendStudentRemovesStudentFromLesson() {
		aLesson.removeAttendance(inscriptedStudentMock);
		assertThat(aLesson.getAttendantStudents()).isEmpty();
	}
	
	@Test
	public void removeAttendanceOfAttendStudentRemovesLessonFromStudent() {
		aLesson.removeAttendance(inscriptedStudentMock);
		verify(inscriptedStudentMock).unattendLesson(aLesson);
	}
	
	@Test
	public void removeAttendanceOfNotAttendStudentDoesNothing() {
		aLesson.removeAttendance(nonInscriptedStudentMock);
		
		assertThat(aLesson.getAttendantStudents()).contains(inscriptedStudentMock);
		assertThat(aLesson.getAttendantStudents()).hasSize(1);
		
		verify(inscriptedStudentMock, atMost(0)).unattendLesson(aLesson);
		
		verify(nonInscriptedStudentMock, atMost(0)).unattendLesson(aLesson);
	}
	
	@Test
	public void removeAllAttendanceLeavesLessonWithoutStudents() throws LessonException {
		aLesson.setAttendance(otherInscriptedStudentMock);
		aLesson.removeAllAttendance();
		
		assertThat(aLesson.getAttendantStudents()).isEmpty();
	}
	
	@Test
	public void removeAllAttendanceRemovesLessonFromAllAttendantStudents() throws LessonException {
		aLesson.setAttendance(otherInscriptedStudentMock);
		aLesson.removeAllAttendance();
		
		verify(inscriptedStudentMock).unattendLesson(aLesson);
		verify(otherInscriptedStudentMock).unattendLesson(aLesson);
	}
	
	@Test
	public void attendedStudentsAreInscriptedInCourseReturnsTrueWithValidLesson() throws LessonException {
		aLesson.setAttendance(inscriptedStudentMock);
		aLesson.setAttendance(otherInscriptedStudentMock);
		
		List<Student> stList = new ArrayList<Student>();
		stList.add(inscriptedStudentMock); 
		stList.add(otherInscriptedStudentMock);
		when(courseMock.getStudents()).thenReturn(stList);
		
		assertThat(aLesson.attendedStudentsAreInscriptedInCourse(courseMock)).isTrue();
	}
}