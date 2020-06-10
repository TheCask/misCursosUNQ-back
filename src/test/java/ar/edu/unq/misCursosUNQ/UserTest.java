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
public class UserTest {
    
	User aUser;
	String firstName = "Byul";
	String lastName = "Chung Han";
	String email = "byulchung@biopolitic.com";
	Integer dNI = 12345678;
	
	@Mock
	Subject assignedSubjectMock;
	
	@Mock
	Subject notAssignedSubjectMock;
	
	@Mock
	Course assignedCourseMock;
	
	@Mock
	Course notAssignedCourseMock;
	
	@Mock
	List<Subject> subjectListMock;
	
	@Mock
	List<Course> courseListMock;

	@BeforeEach
	public void setup() {
		aUser = new User(firstName, lastName, email, dNI);
		aUser.setCoordinatedSubjects(subjectListMock);
		aUser.setTaughtCourses(courseListMock);
		
		when(subjectListMock.contains(assignedSubjectMock)).thenReturn(true);
		when(subjectListMock.contains(notAssignedSubjectMock)).thenReturn(false);
		
		when(courseListMock.contains(assignedCourseMock)).thenReturn(true);
		when(courseListMock.contains(notAssignedCourseMock)).thenReturn(false);
	}
	
	@Test
    public void assignSubjectChecksForSubjectInUser() {
		aUser.assignSubject(assignedSubjectMock);
		aUser.assignSubject(notAssignedSubjectMock);
		
		verify(subjectListMock).contains(assignedSubjectMock);
		verify(subjectListMock).contains(notAssignedSubjectMock);
	}
	
	@Test
    public void assignSubjectNotPreviouslyAssignedAddsSubjectToUser() {
		aUser.assignSubject(notAssignedSubjectMock);
		
		verify(subjectListMock).add(notAssignedSubjectMock);
	}
	
	@Test
    public void assignSubjectPreviouslyAssignedDoesNothing() {
		aUser.assignSubject(assignedSubjectMock);
		
		verify(subjectListMock, atMost(0)).add(assignedSubjectMock);
	}
	
	@Test
	public void assignedSubjectIsCoordinatedSubject() {
		
		assertThat(aUser.isCoordinatedSubject(assignedSubjectMock)).isTrue();
	}
	
	@Test
	public void unAssignedSubjectIsNotCoordinatedSubject() {
		
		assertThat(aUser.isCoordinatedSubject(notAssignedSubjectMock)).isFalse();
	}
	
	@Test
    public void unAssignSubjectPreviouslyAssignedRemovesSubjectFromUser() {
		aUser.unAssignSubject(assignedSubjectMock);
		
		verify(subjectListMock).remove(assignedSubjectMock);
	}
	
	@Test
    public void unAssignSubjectNotPreviouslyAssignedDoesNothing() {
		aUser.unAssignSubject(notAssignedSubjectMock);
		
		verify(subjectListMock, atMost(0)).remove(notAssignedSubjectMock);
	}
	
	@Test
    public void assignCourseChecksForCorseInUser() {
		aUser.assignCourse(assignedCourseMock);
		aUser.assignCourse(notAssignedCourseMock);
		
		verify(courseListMock).contains(assignedCourseMock);
		verify(courseListMock).contains(notAssignedCourseMock);
	}
	
	@Test
    public void assignCourseNotPreviouslyAssignedAddsCourseToUser() {
		aUser.assignCourse(notAssignedCourseMock);
		
		verify(courseListMock).add(notAssignedCourseMock);
	}
	
	@Test
    public void assignCoursePreviouslyAssignedDoesNothing() {
		aUser.assignCourse(assignedCourseMock);
		
		verify(courseListMock, atMost(0)).add(assignedCourseMock);
	}
	
	@Test
	public void assignedCourseIsCoordinatedCourse() {
		
		assertThat(aUser.isTaughtCourse(assignedCourseMock)).isTrue();
	}
	
	@Test
	public void unAssignedCourseIsNotCoordinatedCourse() {
		
		assertThat(aUser.isTaughtCourse(notAssignedCourseMock)).isFalse();
	}
	
	@Test
    public void unAssignCoursePreviouslyAssignedRemovesCourseFromUser() {
		aUser.unAssignCourse(assignedCourseMock);
		
		verify(courseListMock).remove(assignedCourseMock);
	}
	
	@Test
    public void unAssignCourseNotPreviouslyAssignedDoesNothing() {
		aUser.unAssignCourse(notAssignedCourseMock);
		
		verify(courseListMock, atMost(0)).remove(notAssignedCourseMock);
	}
	
}