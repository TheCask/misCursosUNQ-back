package ar.edu.unq.mis_cursos_unq.repos;

import java.util.List;
import javax.persistence.*;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.*;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import ar.edu.unq.mis_cursos_unq.Student;

@Repository
public class StudentDAO {
		   
	@PersistenceContext
	private EntityManager entityManager;

	public List<Student> searchStudents(String searchText, Integer pageNo, int studentPerPage) {
		
		FullTextQuery jpaQuery = this.searchStudentsQuery(searchText);
		
		jpaQuery.setMaxResults(studentPerPage);
		jpaQuery.setFirstResult((pageNo-1) * studentPerPage);
		
		@SuppressWarnings("unchecked")
		List<Student> studentList = jpaQuery.getResultList();
		
		return studentList;
	}

	public Integer searchStudentsTotalCount(String searchText) {
		FullTextQuery jpaQuery = this.searchStudentsQuery(searchText);

		return (Integer) jpaQuery.getResultSize();
	}
	
	private FullTextQuery searchStudentsQuery(String searchText) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Student.class).get();
				
		Query fileNumberQuery = queryBuilder
				.keyword()
				.wildcard() //it is necessary if we want to make use of wildcards
				.onFields("careers") //for personal data fields: "personalData.lastName" | separates fields with , whe serch in more than one field
				//.boostedTo(5f) // change relevancy 5X fileNumber vs 1X careers
				//.andField("careers")
				.matching(searchText + "*") // type depends on field type
				.createQuery();
//		
//		Sort sort = queryBuilder
//				.sort()
//					.byField("release_date")
//					.andByField("title").desc()
//				.createSort();
		
		return fullTextEntityManager.createFullTextQuery(fileNumberQuery, Student.class);
	}
}