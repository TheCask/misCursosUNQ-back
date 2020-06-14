package ar.edu.unq.mis_cursos_unq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;
import ar.edu.unq.mis_cursos_unq.repos.CourseRepo;
import ar.edu.unq.mis_cursos_unq.services.CourseService;
import ar.edu.unq.mis_cursos_unq.services.EvaluationService;
import ar.edu.unq.mis_cursos_unq.services.StudentService;
import ar.edu.unq.mis_cursos_unq.services.SubjectService;
import ar.edu.unq.mis_cursos_unq.services.UserService;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseServiceIT {

	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	logger.info("Materias: -> {}", subjectService.getSubjects().toString());
	logger.info("Users: -> {}", userService.getUsers().toString());
	*/
	
	@Autowired
    private CourseService csService;
	
    @MockBean
    private CourseRepo csRepo;
    
    @Mock
    private Subject subjectMock;
	
	@TestConfiguration
	static class CourseServiceTestContextConfiguration {

		@Bean
		public CourseService csService() { return new CourseService(); }
		
		@MockBean
	    private StudentService stService;
	    
	    @MockBean
	    private UserService usService;
	    
	    @MockBean
	    private SubjectService sjService;
	    
	    @MockBean
	    private EvaluationService evService;
    }
	
	@BeforeEach
	public void setUp() {
	    // Empty Body
	}
    
    @Test
	public void whenInvalidIdThenRaiseRecordNotFoundException() throws RecordNotFoundException {
    	Optional<Course> emptyOpt = Optional.empty();
     
        when(csRepo.findById(1)).thenReturn(emptyOpt);
	
        assertThatExceptionOfType(RecordNotFoundException.class)
        	.isThrownBy(() -> csService.getCourseById(1));  
	}
    
	@Test
	public void whenValidIdThenCourseShouldBeFound() throws RecordNotFoundException, SeasonException {
		when(subjectMock.getCode()).thenReturn("80000-CyT1y2");
		
		Course aCourse = new Course("sf17", subjectMock, 2020, "2c");
		
    	Optional<Course> aCourseOpt = Optional.of(aCourse);
     
        when(csRepo.findById(aCourse.getCourseId())).thenReturn(aCourseOpt);
		
		Course foundCourse = csService.getCourseById(aCourse.getCourseId());
		
		assertThat(foundCourse.getCourseId()).isEqualTo(aCourse.getCourseId());
	}

}