import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.revature.dao.EventTypeDAO;
import com.revature.dao.GradeDAO;

class testReimbursementSystem {

	@Test
	void testGetRecentID() {
		GradeDAO gd = new GradeDAO();
		//this test was created with the assumption of already knowing the most recent grade id in the database
		assertTrue(gd.getRecentID() == 30);
	}
	
	@Test
	void testGetCost() {
		EventTypeDAO etd = new EventTypeDAO();
		//In the database, the event type of 4 is a certification which is a 100% coverage, so the projected reimbursement should be 
		//the same number inputed.
		assertTrue(etd.getCost(4, 300) == 300);
	}

}
