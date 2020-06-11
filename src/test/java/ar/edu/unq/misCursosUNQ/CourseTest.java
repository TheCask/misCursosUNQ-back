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
	
	@Mock
	User nonTeachingTeacherMock;

	@Mock
	User teachingTeacherMock;
	
	@Mock
	List<Student> attendedlessonStudentListMock;
	
	@Mock
	List<Student> courseStudentListMock;
	
	@Mock
	List<User> courseTeacherListMock;
	
	@Mock
	List<Lesson> lessonListMock;
	
//	@Mock
//	List<Evaluation> evaluationListMock;

	@BeforeEach
	public void setup() throws SeasonException {
		when(subjectMock.getCode()).thenReturn(subjectCode);
		
		aCourse = new Course(courseCode, subjectMock, courseYear, courseSeason);
		
		when(inscriptedStudentMock.isInscriptedInCourse(aCourse)).thenReturn(true);
		when(nonInscriptedStudentMock.isInscriptedInCourse(aCourse)).thenReturn(false);
		
		when(teachingTeacherMock.isTaughtCourse(aCourse)).thenReturn(true);
		when(nonTeachingTeacherMock.isTaughtCourse(aCourse)).thenReturn(false);
		
		when(lessonMock.attendedStudentsAreInscriptedInCourse(aCourse)).thenReturn(true);
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
	
	/* STUDENT */
	
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
		
		verify(nonInscriptedStudentMock).signOnCourse(aCourse);
	}
	
	@Test
	public void addStudentAlreadyInscriptedDoesNothing() {
		aCourse.addStudent(inscriptedStudentMock);
		
		assertThat(aCourse.getStudents()).hasSize(0);
		verify(inscriptedStudentMock, atMost(0)).signOnCourse(aCourse);
		
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
		
		verify(nonInscriptedStudentMock).signOffCourse(aCourse);
	}
	
	@Test
	public void removeStudentNotIscriptedInCourseDoesNothing() {
		aCourse.removeStudent(nonInscriptedStudentMock);
		
		verify(nonInscriptedStudentMock, atMost(0)).signOffCourse(aCourse);;
	}
	
	@Test
	public void removeAllStudentsClearStudentsFromCourse() {
		// change behaviour of students mocks
		when(inscriptedStudentMock.isInscriptedInCourse(aCourse)).thenReturn(false);
		when(nonAttendantStudentMock.isInscriptedInCourse(aCourse)).thenReturn(false);
		
		aCourse.addStudent(nonInscriptedStudentMock);
		aCourse.addStudent(inscriptedStudentMock);
		aCourse.addStudent(nonAttendantStudentMock);
		
		assertThat(aCourse.getStudents()).hasSize(3);
		
		aCourse.removeAllStudents();
		
		assertThat(aCourse.getStudents()).hasSize(0);
	}

	@Test
	public void removeAllStudentsRemovesCourseFromStudents() {
		// change behaviour of students mocks
		when(inscriptedStudentMock.isInscriptedInCourse(aCourse)).thenReturn(false);
		when(nonAttendantStudentMock.isInscriptedInCourse(aCourse)).thenReturn(false);
		
		aCourse.addStudent(nonInscriptedStudentMock);
		aCourse.addStudent(inscriptedStudentMock);
		aCourse.addStudent(nonAttendantStudentMock);
		
		assertThat(aCourse.getStudents()).hasSize(3);
		
		aCourse.removeAllStudents();
		
		verify(nonInscriptedStudentMock).signOffCourse(aCourse);
		verify(inscriptedStudentMock).signOffCourse(aCourse);
		verify(nonAttendantStudentMock).signOffCourse(aCourse);
	}
	
	/* TEACHERS */
	
	@Test
	public void addTeacherCheckForTeacherInCourse() {
		aCourse.addTeacher(nonTeachingTeacherMock);
		aCourse.addTeacher(teachingTeacherMock);
		
		verify(nonTeachingTeacherMock).isTaughtCourse(aCourse);
		verify(teachingTeacherMock).isTaughtCourse(aCourse);
	}
	
	@Test
	public void addTeacherNotTeachingAddsTeacherToCourse() {
		aCourse.addTeacher(nonTeachingTeacherMock);
		
		assertThat(aCourse.getTeachers()).hasSize(1);
		assertThat(aCourse.getTeachers()).contains(nonTeachingTeacherMock);
		
		// Replaces teachers list in course by a Mock list
		aCourse.setTeachers(courseTeacherListMock);
		
		aCourse.addTeacher(nonTeachingTeacherMock);
		
		verify(courseTeacherListMock).add(nonTeachingTeacherMock);
	}
	
	@Test
	public void addTeacherNotTeachingAddsCourseToTeacher() {
		aCourse.addTeacher(nonTeachingTeacherMock);
		
		verify(nonTeachingTeacherMock).assignCourse(aCourse);
	}
	
	@Test
	public void addTeacherAlreadyTeachingDoesNothing() {
		aCourse.addTeacher(teachingTeacherMock);
		
		assertThat(aCourse.getTeachers()).hasSize(0);
		verify(teachingTeacherMock, atMost(0)).assignCourse(aCourse);
		
		// Replaces teachers list in course by a Mock list
		aCourse.setTeachers(courseTeacherListMock);
		
		aCourse.addTeacher(teachingTeacherMock);
		
		verify(courseTeacherListMock, atMost(0)).add(teachingTeacherMock);
	}
	
	@Test
	public void removeTeacherTeachingRemovesTeacherFromCourse() {
		aCourse.addTeacher(nonTeachingTeacherMock);
		aCourse.removeTeacher(nonTeachingTeacherMock);
		
		assertThat(aCourse.getTeachers()).isEmpty();
	}
	
	@Test
	public void removeTeacherTeachingRemovesCourseFromTeacher() {
		aCourse.addTeacher(nonTeachingTeacherMock);
		
		aCourse.removeTeacher(nonTeachingTeacherMock);
		
		verify(nonTeachingTeacherMock).unAssignCourse(aCourse);
	}
	
	@Test
	public void removeTeacherNotTeachingCourseDoesNothing() {
		aCourse.removeTeacher(nonTeachingTeacherMock);
		
		verify(nonTeachingTeacherMock, atMost(0)).unAssignCourse(aCourse);;
	}
	
	@Test
	public void removeAllTeachersClearTeachersFromCourse() {
		// change behaviour of teachers mocks
		when(teachingTeacherMock.isTaughtCourse(aCourse)).thenReturn(false);
		
		aCourse.addTeacher(nonTeachingTeacherMock);
		aCourse.addTeacher(teachingTeacherMock);
		
		assertThat(aCourse.getTeachers()).hasSize(2);
		
		aCourse.removeAllTeachers();
		
		assertThat(aCourse.getTeachers()).hasSize(0);
	}

	@Test
	public void removeAllTeachersRemovesCourseFromTeachers() {
		// change behaviour of teachers mocks
		when(teachingTeacherMock.isTaughtCourse(aCourse)).thenReturn(false);
		
		aCourse.addTeacher(nonTeachingTeacherMock);
		aCourse.addTeacher(teachingTeacherMock);
		
		assertThat(aCourse.getTeachers()).hasSize(2);
		
		aCourse.removeAllTeachers();
		
		verify(nonTeachingTeacherMock).unAssignCourse(aCourse);
		verify(teachingTeacherMock).unAssignCourse(aCourse);
	}
	
	/* LESSONS */
	
	@Test
	public void addLessonCheckForLessonInCourse() throws LessonException {
		aCourse.setLessons(lessonListMock);
		aCourse.addLesson(lessonMock);
		
		verify(lessonListMock).contains(lessonMock);
	}
	
	@Test
	public void addLessonCheckAttendantStudentsAreInscriptedInCourse() throws LessonException {
		aCourse.addLesson(lessonMock); 
		
		verify(lessonMock).attendedStudentsAreInscriptedInCourse(aCourse);
	}
	
	@Test
	public void addNewEmptyLessonAddsLessonToCourse() throws LessonException {
		aCourse.addLesson(lessonMock);
		
		assertThat(aCourse.getLessons()).hasSize(1);
		assertThat(aCourse.getLessons()).contains(lessonMock);
		
		verify(lessonMock).setCourse(aCourse);
		
		// Replaces lesson list in course by a Mock list
		aCourse.setLessons(lessonListMock);
		
		aCourse.addLesson(lessonMock);
		
		verify(lessonListMock).add(lessonMock);
	}
	
	@Test
	public void addNewEmptyLessonAddsCourseToLesson() throws LessonException {
		aCourse.addLesson(lessonMock);
		
		verify(lessonMock).setCourse(aCourse);
	}
	
	@Test
	public void addLessonAlreadyAddedDoesNothing() throws LessonException {
		//first time lesson is added
		aCourse.addLesson(lessonMock);
		
		// second time lesson mock is not added
		when(lessonMock.getCourse()).thenReturn(aCourse);
		aCourse.addLesson(lessonMock);
		
		assertThat(aCourse.getLessons()).hasSize(1);
		verify(lessonMock, atMost(1)).setCourse(aCourse);
		
		// Replaces lesson list in course by a Mock list
		aCourse.setLessons(lessonListMock);
		when(lessonListMock.contains(lessonMock)).thenReturn(true);

		// lesson is still not added
		aCourse.addLesson(lessonMock);
		
		verify(lessonListMock).contains(lessonMock);
		verify(lessonListMock, atMost(0)).add(lessonMock);
	}
	
	@Test
	public void addLessonWithAttendStudentNotInCourseThrowsException() {
		when(lessonMock.attendedStudentsAreInscriptedInCourse(aCourse)).thenReturn(false);
		
		Exception exception = Assertions.assertThrows(LessonException.class, () -> {
			aCourse.addLesson(lessonMock);
		});
		
		assertThatExceptionOfType(LessonException.class).isThrownBy(() -> aCourse.addLesson(lessonMock));  
		
	    String expectedMessage = "There are at least one attendant student not taken this course";
	    String actualMessage = exception.getMessage();
	 
	    assertThat(actualMessage).isEqualTo(expectedMessage);
	}
	
	
}