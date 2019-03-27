package persistence01;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App
 *
 */
public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) throws Exception
    {
        int entityElementsNumber = 100;
        int batchsize = 30;
        
        @SuppressWarnings("rawtypes")
        Map<String, Comparable> properties = new HashMap<String, Comparable>();
        properties.put("eclipselink.jdbc.batch-writing", "JDBC");
        properties.put("eclipselink.jdbc.cache-statements", "true");
        properties.put("eclipselink.jdbc.batch-writing.size", String.valueOf(batchsize));
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence01", properties);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

 
        
        try {
            long startTime = System.currentTimeMillis();
            tx.begin();
            for (int i = 0; i < entityElementsNumber; i++) {
                if(i > 0 && i % batchsize == 0) {
                	logger.info("the batch size limit has been reached.");
                    tx.commit();
                    tx.begin();
                    em.clear();
                }
                em.persist(new Book("Title " + i, 12.5F, "Description " + i, "ISBN" + i, 100 + i*5, (i%3==0)?false:true));
            }
            tx.commit();
            System.out.printf("Operations took %s ms", String.valueOf(System.currentTimeMillis() - startTime));
            
        } catch (RuntimeException e) {
            if(tx.isActive()) {
                tx.rollback();
                logger.info("Rollback operation");
            }
            throw e;
        } finally {

            logger.info("Closing entity manager and entity manager factory");
            em.close();
            emf.close();
        }
    }
}
