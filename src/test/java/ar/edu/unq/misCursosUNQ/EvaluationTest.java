package ar.edu.unq.misCursosUNQ;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EvaluationTest {
    
	Evaluation anEvaluation;
	Evaluation otherEvaluation;
	String anInstanceName;
	String otherInstanceName;
	Float calification;
	Float otherCalification;
	
	@Mock
	Student studentMock;

	@BeforeEach
	public void setup() {
		anInstanceName = "Primer Parcial";
		otherInstanceName = "Segundo Parcial";
		anEvaluation = new Evaluation(anInstanceName);
		otherEvaluation = new Evaluation(otherInstanceName);
		calification = 7f;
		otherCalification = 3f; 
		
		anEvaluation.setStudentCalification(studentMock, calification);
		
	}
	
	@Test
    public void getCalificationOfStudent() {
		
		assertThat(anEvaluation.getAttendantStudentCalificationMap())
			.isNotEmpty()
			.containsEntry(studentMock, calification);	
	}
	
	@Test
    public void updateCalificationOfStudent() {
		
		anEvaluation.replaceStudentCalification(studentMock, otherCalification);
		
		assertThat(anEvaluation.getAttendantStudentCalificationMap())
			.isNotEmpty()
			.containsEntry(studentMock, otherCalification);	
	}
	
	@Test
    public void deleteCalificationOfStudent() {
		
		anEvaluation.deleteStudentCalification(studentMock);
		
		assertThat(anEvaluation.getAttendantStudentCalificationMap()
			.isEmpty());
		
		assertFalse(anEvaluation.getAttendantStudentCalificationMap()
				.containsKey(studentMock));
	}

}


//assertThat(personas)
//      .isNotEmpty()
//      .allMatch(persona -> persona.getEdad() > 18);

//assertThatIllegalArgumentException().isThrownBy(()
//-> personaService.buscarPorEdadMayorA(null))
//.withMessage("La edad indicada es inválida");