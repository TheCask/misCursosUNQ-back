package ar.edu.unq.misCursosUNQ.Services;

import ar.edu.unq.misCursosUNQ.Repos.CourseRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;

@Service
public class CourseService {

	@Autowired
	CourseRepo repository;
	
	@Autowired
	StudentService stService;

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

	public Course createOrUpdateCourse(Course entity) throws RecordNotFoundException {

		if (entity.getCourseId() != null) {

			Optional<Course> optEntity = repository.findById(entity.getCourseId());

			if(optEntity.isPresent()) {

				Course newEntity = optEntity.get();

				newEntity.setName(entity.getName());	
				newEntity.setCode(entity.getCode());
				newEntity.setCourseIsOpen(entity.getCourseIsOpen());
				newEntity.setCourseShift(entity.getCourseShift());
				newEntity.setLessons(entity.getLessons());
				newEntity.setStudents(entity.getStudents());

				newEntity = repository.save(newEntity);

				return newEntity;
			} 
		}
		return repository.save(entity);
	}

	public void deleteCourseById(Integer id) throws RecordNotFoundException {
		Optional<Course> optEntity = repository.findById(id);

		if(optEntity.isPresent()) { 
			
			Course course = optEntity.get();
			
			for (Student st: course.getStudents()) {
			
				st.removeCourse(course);
				stService.createOrUpdateStudent(st);
			}
			repository.deleteById(id);
		} 
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	}
}
