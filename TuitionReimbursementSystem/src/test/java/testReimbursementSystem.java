import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import com.revature.dao.EmployeeTypeDAO;
import com.revature.dao.EventTypeDAO;

class testReimbursementSystem {

//	@Test
//	void testGetRecentID() {
//		GradeDAO gd = new GradeDAO();
//		//this test was created with the assumption of already knowing the most recent grade id in the database
//		assertTrue(gd.getRecentID() == 30);
//	}
	
	@Test
	void testGetCost() {
		EventTypeDAO etd = new EventTypeDAO();
		//In the database, the event type of 4 is a certification which is a 100% coverage, so the projected reimbursement should be 
		//the same number inputed.
		assertTrue(etd.getCost(4, 300) == 300);
	}
	
	@Test
	void testDate() {
		String date = "1/20/18";
		Date d = null;
		try {
			d = (Date) new SimpleDateFormat("dd/MMM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(d.toString().equals("20-jan-2018"));
	}
	@Test
	void testGetEmployeeType() {
		EmployeeTypeDAO emptd = new EmployeeTypeDAO();
		//In the database 1 = Employee, 2 = Supervisor, 3 = Department Head
		assertTrue(emptd.getEmployeeType(1).equals("Employee"));
	}
	
	

}
