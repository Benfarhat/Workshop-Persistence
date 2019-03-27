package persistence01;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;

public class HistoryCustomizer implements DescriptorCustomizer {
	
    @SuppressWarnings("unused")
	public void customize(ClassDescriptor descriptor) {
    	
    	String primaryTable = descriptor.getTableName();
    	String tableName = descriptor.getTableName();
    	
        HistoryPolicy policy = new HistoryPolicy();
        //policy.addHistoryTableName(tableName.toUpperCase() + "_HISTORY");
        policy.addHistoryTableName("BOOK", "BOOK_HISTORY");
        policy.addStartFieldName("START");
        policy.addEndFieldName("END");
        
        descriptor.setHistoryPolicy(policy);
    }
}