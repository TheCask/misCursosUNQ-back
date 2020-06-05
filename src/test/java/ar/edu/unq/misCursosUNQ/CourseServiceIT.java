package ar.edu.unq.misCursosUNQ;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Exceptions.SeasonException;
import ar.edu.unq.misCursosUNQ.Repos.CourseRepo;
import ar.edu.unq.misCursosUNQ.Repos.SubjectRepo;
import ar.edu.unq.misCursosUNQ.Services.CourseService;
import ar.edu.unq.misCursosUNQ.Services.StudentService;
import ar.edu.unq.misCursosUNQ.Services.UserService;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseServiceIT {

	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	logger.info("Materias: -> {}", subjectService.getSubjects().toString());
	logger.info("Users: -> {}", userService.getUsers().toString());
	*/
	
	 @TestConfiguration
	 static class CourseServiceTestContextConfiguration {

		 @Bean
		 public CourseService csService() {
			 return new CourseService();
		 }
    }
 
    @Autowired
    private CourseService csService;
    
    @MockBean
    private StudentService stService;
    
    @MockBean
    private UserService usService;
    
    @MockBean
    private CourseRepo csRepo;
    
    @MockBean
    private SubjectRepo sjRepo;
    
    @Mock
	Subject subjectMock; 

    
    @BeforeEach
    public void setUp() {
    	
    }
    
    @Test
	public void whenInvalidId_thenRaiseRecordNotFoundException() throws RecordNotFoundException {
    	Optional<Course> emptyOpt = Optional.empty();
     
        Mockito.when(csRepo.findById(1)).thenReturn(emptyOpt);
	
        assertThatExceptionOfType(RecordNotFoundException.class)
        	.isThrownBy(() -> csService.getCourseById(1));  
	}
    
	@Test
	public void whenValidId_thenCourseShouldBeFound() throws RecordNotFoundException, SeasonException {
		Mockito.when(subjectMock.getCode()).thenReturn("80000-CyT1y2");
		
		Course aCourse = new Course("sf17", subjectMock, 2020, "2c");
		
    	Optional<Course> aCourseOpt = Optional.of(aCourse);
     
        Mockito.when(csRepo.findById(aCourse.getCourseId()))
          .thenReturn(aCourseOpt);
		
		Course foundCourse = csService.getCourseById(aCourse.getCourseId());
		
		assertThat(foundCourse.getCourseId()).isEqualTo(aCourse.getCourseId());
	}

}