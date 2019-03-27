/**
 * 
 */
package jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jpa.model.Employee;

/**
 * @author PC
 *
 */
class EmployeeTest {
	
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
		Employee emp1 = new Employee((int)(Math.random() * 1000), "John Doe", 80000D, "deg1");
		
		tx.begin();
		em.persist(emp1);
		tx.commit();
		
		assertNotNull(emp1.getEid());
	}

	@Test
	@DisplayName("Testing find element after insertion")
	void testSimplePersistAndFind() {
		Employee emp1 = new Employee((int)(Math.random() * 1000), "John Doe", 80000D, "deg1");
		
		tx.begin();
		em.persist(emp1);
		tx.commit();
		
		Employee empResult = em.find(Employee.class, emp1.getEid());
		
		assertEquals("John Doe", empResult.getEname());
	}
	
	@Test
	@DisplayName("Testing deletion")
	void testDeleteOfElement() {
		Employee emp1 = new Employee((int)(Math.random() * 1000), "John Doe", 80000D, "deg1");
		int employeeId;
		
		tx.begin();
		em.persist(emp1);
		tx.commit();
		
	
		employeeId = em.find(Employee.class, emp1.getEid()).getEid();
		
		em.remove(em.find(Employee.class, employeeId));


		assertNull(em.find(Employee.class, employeeId));
	}

}
