package ar.edu.unq.mis_cursos_unq.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.mis_cursos_unq.Course;
import ar.edu.unq.mis_cursos_unq.Evaluation;
import ar.edu.unq.mis_cursos_unq.Lesson;
import ar.edu.unq.mis_cursos_unq.Student;
import ar.edu.unq.mis_cursos_unq.Subject;
import ar.edu.unq.mis_cursos_unq.User;
import ar.edu.unq.mis_cursos_unq.exceptions.RecordNotFoundException;
import ar.edu.unq.mis_cursos_unq.exceptions.SeasonException;
import ar.edu.unq.mis_cursos_unq.exceptions.SubjectException;
import ar.edu.unq.mis_cursos_unq.repos.CourseRepo;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepo csRepo;
	
	@Autowired
	private SubjectService sbService;
	
	@Autowired
	private StudentService stService;
	
	@Autowired
	private UserService usService;
	
	@Autowired
	private EvaluationService evService;

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
	protected void deleteCourseStudent(Integer courseId, Student st) throws RecordNotFoundException {
		
		Course course = this.getCourseById(courseId);
		
		List<Lesson> stCsLessons = new ArrayList<Lesson>();
		
		st.getAttendedLessons().stream().forEach(ln -> {
			if (ln.getCourse().equals(course)) { stCsLessons.add(ln); };
		});
		
		stCsLessons.forEach(ln -> ln.removeAttendance(st));
		
		evService.removeCourseStudentCalifications(course, st);
		
		course.removeStudent(st);
		
		csRepo.save(course);
	}
	
	@Transactional
	public void deleteCourseStudentById(Integer courseId, Integer studentId) throws RecordNotFoundException {
		
		Student student = stService.getStudentByFileNumber(studentId);
		this.deleteCourseStudent(courseId, student);
	}

	@Transactional
	public void deleteCourseTeacherById(Integer courseId, Integer teacherId) throws RecordNotFoundException {
		
		Optional<Course> optEntity = csRepo.findById(courseId);
		
		User teacher;
		try { teacher = usService.getUserById(teacherId); } 
		catch (RecordNotFoundException e) { 
			e.printStackTrace();
			throw new RecordNotFoundException("Teacher record not found for given id");
		}

		if(optEntity.isPresent()) { 
			
			Course course = optEntity.get();
			
			course.removeTeacher(teacher);
			
			csRepo.save(course);
		} 
		else { throw new RecordNotFoundException("Course record not exist for given id"); }
	}

	public Optional<Course> getCourseByEvaluationId(Long evaluationId) {
		
		List<Course> courses = csRepo.findAll();
		
		Optional<Course> optCourse = courses.stream().filter
			(cs -> cs.getEvaluations().stream().anyMatch
					(ev -> ev.getEvaluationId() == evaluationId)).findFirst();
		
		return optCourse;
	}

	@Transactional
	public Course createOrUpdateEvaluation(Integer courseId, Evaluation evaluation) throws RecordNotFoundException {
		
		Optional<Course> optCourse = csRepo.findById(courseId);
		
		Course course;
		
		// Update Evaluation
    	if (optCourse.isPresent() && evaluation.getEvaluationId() != null) {
    		
	    	Evaluation dbEvaluation = evService.getEvaluationById(evaluation.getEvaluationId());
			    		
    		dbEvaluation.setInstanceName(evaluation.getInstanceName());
	    	evService.updateCalifications(dbEvaluation, evaluation);
	    	
	    	course = optCourse.get();
    	}
    	else if (optCourse.isPresent()) {
			course = optCourse.get();
			
			course.addEvaluation(evaluation);
		}
    	else { throw new RecordNotFoundException("Course record not exist for given id"); }
    	
    	return csRepo.save(course);
	}
}
