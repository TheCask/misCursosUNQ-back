package ar.edu.unq.mis_cursos_unq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Entity
public class PersonalData implements Serializable {

	private static final long serialVersionUID = -2414154033870368530L;
	
	private Integer personalDataId;
	
	private Integer dni;
	
	@Size(max = 50)
	private String firstName;
	
	@Size(max = 50)
	private String lastName;
	
	@Size(max = 50)
	@Pattern(regexp = "^$|^.*@.*\\..*$")
	private String email;
	
	@Pattern(regexp = "^$|\\d{2,4}-\\d{6,8}")
	private String cellPhone;

	// Default constructor for Hibernate
	protected PersonalData() {}
	
	public PersonalData(Integer aDNI, String aFirstName, String aLastName, String anEmail) {
		this.setDni(aDNI);
		this.setFirstName(aFirstName);
		this.setLastName(aLastName);
		this.setEmail(anEmail);
		this.setCellPhone("");
	}

	/* GETTERS & SETTERS */

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getPersonalDataId() { return personalDataId; }

	/* Protected to avoid set the primary key */
	protected void setPersonalDataId(Integer personalDataId) { this.personalDataId = personalDataId; }
	
	@Column(unique = true)
	public Integer getDni() { return dni; }

	public void setDni(Integer dni) { this.dni = dni; }
	
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName;}

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getCellPhone() { return cellPhone; }

	public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }

	/* METHODS */
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalData)) return false;
        return dni != null && dni.equals(((PersonalData) o).getDni());
    }
}
