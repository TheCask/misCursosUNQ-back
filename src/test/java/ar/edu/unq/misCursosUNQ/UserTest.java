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
public class UserTest {
    
	User aUser;
	String firstName = "Byul";
	String lastName = "Chung Han";
	String email = "byulchung@biopolitic.com";
	Integer dNI = 12345678;
	
	@Mock
	PersonalData personalDataMock;
	
	@Mock
	JobDetail jobDetailMock;
	
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
		aUser = new User();
		aUser.setJobDetail(jobDetailMock);
		aUser.setPersonalData(personalDataMock);
		aUser.setCoordinatedSubjects(subjectListMock);
		aUser.setTaughtCourses(courseListMock);
		
		Mockito.when(personalDataMock.getDni()).thenReturn(dNI);
		Mockito.when(personalDataMock.getFirstName()).thenReturn(firstName);
		Mockito.when(personalDataMock.getLastName()).thenReturn(lastName);
		Mockito.when(personalDataMock.getEmail()).thenReturn(email);
		
		Mockito.when(subjectListMock.contains(assignedSubjectMock)).thenReturn(true);
		Mockito.when(subjectListMock.contains(notAssignedSubjectMock)).thenReturn(false);
		
		Mockito.when(courseListMock.contains(assignedCourseMock)).thenReturn(true);
		Mockito.when(courseListMock.contains(notAssignedCourseMock)).thenReturn(false);
	}
	
	@Test
    public void assignSubjectChecksForSubjectInUser() {
		aUser.assignSubject(assignedSubjectMock);
		aUser.assignSubject(notAssignedSubjectMock);
		
		Mockito.verify(subjectListMock).contains(assignedSubjectMock);
		Mockito.verify(subjectListMock).contains(notAssignedSubjectMock);
	}
	
	@Test
    public void assignSubjectNotPreviouslyAssignedAddsSubjectToUser() {
		aUser.assignSubject(notAssignedSubjectMock);
		
		Mockito.verify(subjectListMock).add(notAssignedSubjectMock);
	}
	
	@Test
    public void assignSubjectPreviouslyAssignedDoesNothing() {
		aUser.assignSubject(assignedSubjectMock);
		
		Mockito.verify(subjectListMock, atMost(0)).add(assignedSubjectMock);
	}
	
	@Test
	public void assignedSubjectIsCoordinatedSubject() {
		
		assertThat(aUser.isCoordinatedSubject(assignedSubjectMock));
	}
	
	@Test
	public void unAssignedSubjectIsNotCoordinatedSubject() {
		
		assertThat(!aUser.isCoordinatedSubject(notAssignedSubjectMock));
	}
	
	@Test
    public void unAssignSubjectPreviouslyAssignedRemovesSubjectFromUser() {
		aUser.unAssignSubject(assignedSubjectMock);
		
		Mockito.verify(subjectListMock).remove(assignedSubjectMock);
	}
	
	@Test
    public void unAssignSubjectNotPreviouslyAssignedDoesNothing() {
		aUser.unAssignSubject(notAssignedSubjectMock);
		
		Mockito.verify(subjectListMock, atMost(0)).remove(notAssignedSubjectMock);
	}
	
	@Test
    public void assignCourseChecksForCorseInUser() {
		aUser.assignCourse(assignedCourseMock);
		aUser.assignCourse(notAssignedCourseMock);
		
		Mockito.verify(courseListMock).contains(assignedCourseMock);
		Mockito.verify(courseListMock).contains(notAssignedCourseMock);
	}
	
	@Test
    public void assignCourseNotPreviouslyAssignedAddsCourseToUser() {
		aUser.assignCourse(notAssignedCourseMock);
		
		Mockito.verify(courseListMock).add(notAssignedCourseMock);
	}
	
	@Test
    public void assignCoursePreviouslyAssignedDoesNothing() {
		aUser.assignCourse(assignedCourseMock);
		
		Mockito.verify(courseListMock, atMost(0)).add(assignedCourseMock);
	}
	
	@Test
	public void assignedCourseIsCoordinatedCourse() {
		
		assertThat(aUser.isTaughtCourse(assignedCourseMock));
	}
	
	@Test
	public void unAssignedCourseIsNotCoordinatedCourse() {
		
		assertThat(!aUser.isTaughtCourse(notAssignedCourseMock));
	}
	
	@Test
    public void unAssignCoursePreviouslyAssignedRemovesCourseFromUser() {
		aUser.unAssignCourse(assignedCourseMock);
		
		Mockito.verify(courseListMock).remove(assignedCourseMock);
	}
	
	@Test
    public void unAssignCourseNotPreviouslyAssignedDoesNothing() {
		aUser.unAssignCourse(notAssignedCourseMock);
		
		Mockito.verify(courseListMock, atMost(0)).remove(notAssignedCourseMock);
	}
	
}