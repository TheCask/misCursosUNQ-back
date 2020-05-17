package ar.edu.unq.misCursosUNQ.Services;

import ar.edu.unq.misCursosUNQ.Repos.CourseRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	}

	@Transactional
	public Course createOrUpdateCourse(Course entity) throws RecordNotFoundException {

		if (entity.getCourseId() != null) {

			Optional<Course> course = repository.findById(entity.getCourseId());

			if(course.isPresent()) {

				Course newEntity = course.get();

				newEntity.setCourseName(entity.getCourseName());
				newEntity.setCourseIsOpen(entity.getCourseIsOpen());
				newEntity.setCourseShift(entity.getCourseShift());
				newEntity.setSubject(entity.getSubject());
				
				return repository.save(newEntity);
			} 
		}
		return repository.save(entity);
	}

	@Transactional
	public void deleteCourseById(Integer id) throws RecordNotFoundException {
		Optional<Course> optEntity = repository.findById(id);

		if(optEntity.isPresent()) { 
			
			Course course = optEntity.get();
			
			course.removeStudents();
			repository.saveAndFlush(course);
			repository.delete(course);
		} 
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	}
}
