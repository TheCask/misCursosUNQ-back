package ar.edu.unq.misCursosUNQ;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class Classroom implements Serializable{

	private static final long serialVersionUID = 7807228793147656046L;

	private Integer classroomId;
	private String name;
	private String location;
	private String floor;
	private String boardType;
	private Integer capacity;
	private Boolean hasProyector;
	private Boolean isAccessible;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getClassroomId() { return classroomId; }

	/* Protected to avoid set the primary key */
	protected void setClassroomId(Integer classroomId) { this.classroomId = classroomId; }

	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	public String getLocation() { return location; }
	
	public void setLocation(String location) { this.location = location; }
	
	public String getFloor() { return floor; }
	
	public void setFloor(String floor) { this.floor = floor; }
	
	public String getBoardType() { return boardType; }
	
	public void setBoardType(String boardType) { this.boardType = boardType; }
	
	public Integer getCapacity() { return capacity; }
	
	public void setCapacity(Integer capacity) { this.capacity = capacity; }
	
	public Boolean getHasProyector() { return hasProyector; }
	
	public void setHasProyector(Boolean hasProyector) { this.hasProyector = hasProyector; }
	
	public Boolean getIsAccessible() { return isAccessible; }
	
	public void setIsAccessible(Boolean isAccessible) { this.isAccessible = isAccessible; }
	
	
	
}
