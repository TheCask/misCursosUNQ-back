package ar.edu.unq.misCursosUNQ.Services;

import ar.edu.unq.misCursosUNQ.Repos.CourseRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.misCursosUNQ.Course;
import ar.edu.unq.misCursosUNQ.Student;
import ar.edu.unq.misCursosUNQ.Subject;
import ar.edu.unq.misCursosUNQ.User;

import ar.edu.unq.misCursosUNQ.Exceptions.RecordNotFoundException;
import ar.edu.unq.misCursosUNQ.Exceptions.SeasonException;
import ar.edu.unq.misCursosUNQ.Exceptions.SubjectException;

@Service
public class CourseService {
	
	@Autowired
	CourseRepo csRepo;
	
	@Autowired
	SubjectService sbService;
	
	@Autowired
	StudentService stService;
	
	@Autowired
	UserService usService;

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
	public Course createOrUpdateCourse(Course entity) throws RecordNotFoundException, SeasonException, SubjectException {
	
		if (entity.getSubject() != null && entity.getSubject().getCode() != null) {
		
			Subject courseSubject = null;
			courseSubject = sbService.getSubjectByCode(entity.getSubject().getCode());
			
			// Update an existing course
			if (entity.getCourseId() != null) {
	
				Optional<Course> course = csRepo.findById(entity.getCourseId());
	
				if(course.isPresent()) {
	
					Course newEntity = course.get();
					
					// TODO Update lessons if needed
					newEntity.setSubject(courseSubject);
					newEntity.setCourseCode(entity.getCourseCode());
					newEntity.setCourseIsOpen(entity.getCourseIsOpen());
					newEntity.setCourseShift(entity.getCourseShift());
					newEntity.setCourseYear(entity.getCourseYear());
					newEntity.setCourseSeason(entity.getCourseSeason());
					newEntity.setCourseLocation(entity.getCourseLocation());
					
					this.updateStudents(newEntity, entity);
					this.updateTeachers(newEntity, entity);
							
					return csRepo.save(newEntity);
				}
				// Course id not found
				throw new RecordNotFoundException("Course record not exist for given id");
			}
			// Create a new Course		
			return csRepo.save(entity);
		}
		else { throw new SubjectException("Subject code not found"); }
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
	
	private void updateTeachers(Course dbCourse, Course newDataCourse) {
		newDataCourse.getTeachers().forEach(tc -> {
			try {
				User dbTeacher = usService.getUserById(tc.getUserId());
				dbCourse.addTeacher(dbTeacher);
			} 
			catch (RecordNotFoundException e) { e.printStackTrace(); }
		});
		
	}

	@Transactional
	public void deleteCourseStudentById(Integer courseId, Integer studentId) throws RecordNotFoundException {
		
		Optional<Course> optEntity = csRepo.findById(courseId);
		
		Student student;
		try { student = stService.getStudentByFileNumber(studentId); } 
		catch (RecordNotFoundException e) { 
			e.printStackTrace();
			throw new RecordNotFoundException("Student record not found for given id");
		}

		if(optEntity.isPresent()) { 
			
			Course course = optEntity.get();
			
			course.removeStudent(student);
			
			csRepo.save(course);
		} 
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	}
}
