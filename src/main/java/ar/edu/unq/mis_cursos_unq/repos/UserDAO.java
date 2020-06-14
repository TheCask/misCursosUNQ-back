package ar.edu.unq.mis_cursos_unq.repos;

import java.util.List;
import javax.persistence.*;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.*;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import ar.edu.unq.mis_cursos_unq.User;

@Repository
public class UserDAO {
		   
	@PersistenceContext
	private EntityManager entityManager;

	public List<User> searchUsers(String searchText, Integer pageNo, int userPerPage) {
		
		FullTextQuery jpaQuery = this.searchUsersQuery(searchText);
		
		jpaQuery.setMaxResults(userPerPage);
		jpaQuery.setFirstResult((pageNo-1) * userPerPage);
		
		@SuppressWarnings("unchecked")
		List<User> userList = jpaQuery.getResultList();
		
		return userList;
	}

	public Integer searchUsersTotalCount(String searchText) {
		FullTextQuery jpaQuery = this.searchUsersQuery(searchText);

		return (Integer) jpaQuery.getResultSize();
	}
	
	private FullTextQuery searchUsersQuery(String searchText) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(User.class).get();
				
		Query usersQuery = queryBuilder
				.keyword()
				.wildcard() //it is necessary if use wildcards
				.onFields("personalData.firstName", "personalData.lastName", "personalData.email", 
						"personalData.cellPhone", "personalData.dni", "jobDetail.cuitNumber", 
						"jobDetail.category", "jobDetail.dedication", "jobDetail.contractRelation")
					.boostedTo(5f) // change relevancy 5X fileNumber vs 1X careers
				.andField("jobDetail.gradeTitles")
				.andField("jobDetail.posGradeTitles")
				.matching("*" + searchText + "*")
				.createQuery();
//		
//		Sort sort = queryBuilder
//				.sort()
//					.byField("release_date")
//					.andByField("title").desc()
//				.createSort();
		
		return fullTextEntityManager.createFullTextQuery(usersQuery, User.class);
	}
}