/**
 * 
 */
package jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jpa.model.Employee;

/**
 * @author PC
 *
 */
class EmployeeTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeTest.class);
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private EntityTransaction tx;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {		
			emf = Persistence.createEntityManagerFactory("jpa");
			em = emf.createEntityManager();
		} catch (Exception e) {
			System.err.println("Cannot create Entity Manager.\n" + e.getMessage());
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		if (em.isOpen())
			em.close();
		if (emf.isOpen())
			emf.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		tx = em.getTransaction();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		if (tx.isActive())
			tx.rollback();
		em.clear();
	}

	@Test
	@DisplayName("Testing simple insertion")
	void testSimplePersist() {
		Employee emp1 = new Employee((int)(Math.random() * 1000), "John Doe", (double)(Math.random() * 10000), "deg1");
		
		tx.begin();
		em.persist(emp1);
		tx.commit();
		
		assertNotNull(emp1.getEid());
	}

	@SuppressWarnings("rawtypes")
	@Test
	@DisplayName("Testing find element after insertion")
	void testSimplePersistAndFind() {
		Employee emp1 = new Employee((int)(Math.random() * 1000), "John Doe", (double)(Math.random() * 10000), "deg1");
		tx.begin();
		em.persist(emp1);
		try {
			tx.commit();
		} catch (RollbackException e) {
			Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)e.getCause()).getConstraintViolations();
			for (ConstraintViolation v : violations) {
				logger.info(String.valueOf(v));
			}
		}
		
		assertEquals("John Doe", em.find(Employee.class, emp1.getEid()).getEname());
	}
	
	@Test
	@DisplayName("Testing deletion")
	void testDeleteOfElement() {
		Employee emp1 = new Employee((int)(Math.random() * 1000), "a", (double)(Math.random() * 10000), "deg1");
		int employeeId;
		
		tx.begin();
		em.persist(emp1);
		tx.commit();
			
		employeeId = em.find(Employee.class, emp1.getEid()).getEid();
		
		em.remove(em.find(Employee.class, employeeId));

		assertNull(em.find(Employee.class, employeeId));
	}	

	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Testing NamedQuery finALL Result")
	void testNamedQueryFindAllResult() {

		List<Employee> listsEmployee1 = em.createQuery("SELECT e FROM Employee e").getResultList();	
		List<Employee> listsEmployee2 = em.createNamedQuery("Employee.findAll").getResultList();

		assertEquals(listsEmployee1.size(), listsEmployee2.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Testing NamedQuery findMinSalary Result")
	void testNamedQueryFindMinSalaryResult() {

		List<Employee> emp1 = em.createQuery("SELECT e FROM Employee e WHERE e.salary IN (SELECT MIN(ee.salary) FROM Employee ee)").getResultList();	
		List<Employee> emp2 = em.createNamedQuery("Employee.findMinSalary").getResultList();

		System.out.println(emp1);
		assertEquals(emp1, emp2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Testing NamedQuery findMaxSalary Result")
	void testNamedQueryFindMaxSalaryResult() {

		List<Employee> emp1 = em.createQuery("SELECT e FROM Employee e WHERE e.salary IN (SELECT MAX(ee.salary) FROM Employee ee)").getResultList();	
		List<Employee> emp2 = em.createNamedQuery("Employee.findMaxSalary").getResultList();

		System.out.println(emp1);
		assertEquals(emp1, emp2);
	}
	
	/*
	
	<named-query name="Employee.findMinSalary">
		<query>SELECT e FROM Employee e WHERE e.salary IN (SELECT MIN(ee.salary) FROM
			Employee ee)
		</query>
	</named-query>
	
		
	<named-query name="Employee.findMaxSalary">
		<query>SELECT e FROM Employee e WHERE e.salary IN (SELECT MAX(ee.salary) FROM
			Employee ee)
		</query>
*/
}
