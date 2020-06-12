package ar.edu.unq.mis_cursos_unq;

import javax.persistence.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
//import org.apache.lucene.analysis.charfilter.MappingCharFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.bridge.builtin.IntegerBridge;
import java.io.Serializable;

/* ANALIZERS */

@AnalyzerDef(name = "personalAnalizer",
charFilters = {
//		@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
//				@Parameter(name = "mapping",
//						value = ""org/hibernate/search/test/analyzer/mapping-chars.properties"")
//		}),
		@CharFilterDef(factory = HTMLStripCharFilterFactory.class)
},
tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), //ClassicTokenizerFactory is better for hotnames and email
filters = {
		@TokenFilterDef(factory = StandardFilterFactory.class), // removes dots and 's
		@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class), // removes accents
		@TokenFilterDef(factory = LowerCaseFilterFactory.class), //lowecase all words
		@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = { // reduces words to root
				@Parameter(name = "language", value = "Spanish")
		})
})

@Entity
@Analyzer(definition = "personalAnalizer")
public class PersonalData implements Serializable {

	private static final long serialVersionUID = -2414154033870368530L;
	
	private Integer personalDataId;
	
	@Field @FieldBridge(impl = IntegerBridge.class)
	private Integer dni;
	
	@Field 
	@Size(max = 50)
	private String firstName;
	
	@Field
	@Size(max = 50)
	private String lastName;
	
	@Field
	@Size(max = 50) @Pattern(regexp = "^$|^.*@.*\\..*$")
	private String email;
	
	@Field
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

	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
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