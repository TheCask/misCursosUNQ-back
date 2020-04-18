package ar.edu.unq.misCursosUNQ.Services;

import ar.edu.unq.misCursosUNQ.Repos.CourseRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;

@Service
public class CourseService {

	@Autowired
	CourseRepo repository;

	public List<Course> getCourses() {
		List<Course> courseList = repository.findAll();

		if(courseList.size() > 0) { return courseList; } 
		else { return new ArrayList<Course>(); }
	}

	public Course getCourseById(Integer id) throws RecordNotFoundException {
		Optional<Course> course = repository.findById(id);

		if(course.isPresent()) { return course.get(); } 
		else { throw new RecordNotFoundException("Course record not exist for given code"); }
	}

	public Course createOrUpdateCourse(Course entity) throws RecordNotFoundException {

		if (entity.getCourseId() == null) {	return repository.save(entity); }
		
		else {
			Optional<Course> course; course = repository.findById(entity.getCourseId());

			Course newEntity = course.get();
			
			if(course.isPresent()) {
				
				newEntity.setName(entity.getName());	
				newEntity.setCode(entity.getCode());
				newEntity.setCourseIsOpen(entity.getCourseIsOpen());
				newEntity.setCourseShift(entity.getCourseShift());
				newEntity.setLessons(entity.getLessons());
				newEntity.setStudents(entity.getStudents());
	
				newEntity = repository.save(newEntity);
			}
			else { newEntity = repository.save(entity); }
			
			return newEntity;
		}
	} 

	public void deleteCourseById(Integer id) throws RecordNotFoundException {
		Optional<Course> course = repository.findById(id);

		if(course.isPresent()) { repository.deleteById(id); } 
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	} 
	
}
