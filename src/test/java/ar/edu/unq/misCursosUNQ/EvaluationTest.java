package ar.edu.unq.misCursosUNQ;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EvaluationTest {
    
	Evaluation anEvaluation;
	Evaluation otherEvaluation;
	String anInstanceName;
	String otherInstanceName;
	Float note;
	Float otherNote;
	
	@Mock
	Student studentMock;

	@BeforeEach
	public void setup() {
		anInstanceName = "Primer Parcial";
		otherInstanceName = "Segundo Parcial";
		anEvaluation = new Evaluation(anInstanceName);
		otherEvaluation = new Evaluation(otherInstanceName);
		note = 7f;
		otherNote= 3f; 
		
		anEvaluation.setStudentNote(studentMock, note);
		
	}
	
	@Test
    public void getCalificationOfStudent() {
		
		assertThat(anEvaluation.getCalifications())
			.isNotEmpty()
			.anyMatch(cl ->
				cl.getStudent().equals(studentMock) &&
				cl.getNote().equals(note));
	}
	
	@Test
    public void updateCalificationOfStudent() {
		
		anEvaluation.replaceStudentCalification(studentMock, otherNote);
		
		assertThat(anEvaluation.getCalifications())
			.isNotEmpty()
			.anyMatch(cl ->
				cl.getStudent().equals(studentMock) &&
				cl.getNote().equals(otherNote));	
	}
	
	@Test
    public void deleteCalificationOfStudent() {
		
		anEvaluation.deleteStudentCalification(studentMock);
		
		assertThat(anEvaluation.getCalifications()).isEmpty();
	}

}