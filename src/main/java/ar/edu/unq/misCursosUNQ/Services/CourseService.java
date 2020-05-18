package ar.edu.unq.misCursosUNQ.Services;

import ar.edu.unq.misCursosUNQ.Repos.CourseRepo;
import ar.edu.unq.misCursosUNQ.Repos.SubjectRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Subject;
import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;

@Service
public class CourseService {
	
	@Autowired
	SubjectRepo sbRepo;
	
	@Autowired
	CourseRepo csRepo;
	
	@Autowired
	StudentService stService;

	public List<Course> getCourses() {
		List<Course> courseList = csRepo.findAll();

		if(courseList.size() > 0) { return courseList; } 
		else { return new ArrayList<Course>(); }
	}

	public Course getCourseById(Integer id) throws RecordNotFoundException {
		Optional<Course> course = csRepo.findById(id);

		if(course.isPresent()) { return course.get(); } 
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	}

	@Transactional
	public Course createOrUpdateCourse(Course entity) throws RecordNotFoundException {

		Optional<Subject> courseSubject = sbRepo.findByCode(entity.getSubject().getCode());
		
		// Update an existing course
		if (entity.getCourseId() != null) {

			Optional<Course> course = csRepo.findById(entity.getCourseId());

			if(course.isPresent()) {

				Course newEntity = course.get();
				
				if(courseSubject.isPresent()) { 
					
					// TODO Update teachers and lessons if needed
					newEntity.setSubject(courseSubject.get());
					newEntity.setCourseName(entity.getCourseName());
					newEntity.setCourseIsOpen(entity.getCourseIsOpen());
					newEntity.setCourseShift(entity.getCourseShift());
					
					this.updateStudents(newEntity, entity);
							
					return csRepo.save(newEntity);
				}
				// Subject code not found in database
				throw new RecordNotFoundException("Subject record not exist for given code");
			}
			// Course id not found
			throw new RecordNotFoundException("Course record not exist for given id");
		}
		
		// Create a new Course
		if (entity.getSubject() != null && courseSubject.isPresent()) {
			
			return csRepo.save(entity);
		}
		throw new RecordNotFoundException("Subject record not exist for given code");
	}

	@Transactional
	public void deleteCourseById(Integer id) throws RecordNotFoundException {
		Optional<Course> optEntity = csRepo.findById(id);

		if(optEntity.isPresent()) { 
			
			Course course = optEntity.get();
			
			// Before delete course, all students and teachers has to be dissociated
			// if not, foreign key constraints errors appear
			course.removeAllStudents();
			course.removeAllTeachers();
			//course.removeAllLessons(); // the lessons are completely removed by cascade anotation
			
			csRepo.deleteById(course.getCourseId());
		} 
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	}
	
	protected void updateStudents(Course dbCourse, Course newDataCourse) {
		newDataCourse.getStudents().forEach(st -> {
			try {
				Student dbStudent = stService.getStudentByFileNumber(st.getFileNumber());
				dbCourse.addStudent(dbStudent);
			} 
			catch (RecordNotFoundException e) { e.printStackTrace(); }
		});
	}
}
