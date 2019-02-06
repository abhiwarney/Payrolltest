package com.cg.payroll.test;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.exceptions.AssociateDetailsNotfoundException;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;
import com.cg.payroll.util.PayrollDBUtil;
public class PayrollServicesTest {
	private static PayrollServices services;
	@BeforeClass
	public static void setUpTestEnv() {
		services = new PayrollServicesImpl();
	}
	@Before
	public void setUpTestData() {
		Associate associate1 = new Associate(101,78000,"Satish","Mahajan","Training","Manager","DTDYF736","satish.mahajan@capgemini.com",new Salary(35000,1800,1800),new BankDetails(12345,"HDFC","1234SBI"));	
		Associate associate2 = new Associate(102,78000,"Abhi","Warney","CSE","Student","DTDYF666","abhinav@capgemini.com",new Salary(35000,1800,1800),new BankDetails(13245,"HDFC","123423SBI"));	
		PayrollDBUtil.associates.put(associate1.getAssociateId(),associate1);
		PayrollDBUtil.associates.put(associate2.getAssociateId(),associate1);
		PayrollDBUtil.ASSOCIATE_ID_COUNTER = 102;
	}
	@Test(expected=AssociateDetailsNotfoundException.class)
	public void testGetAssociateDetailsForInvalidAssociateId() throws AssociateDetailsNotfoundException{
		services.getAssociateDetails(12343);
	}
	@Test
	public void testGetAssociateDetailsForValidAssociateId() throws AssociateDetailsNotfoundException{
		Associate expectedAssociate = new Associate(102,78000,"Abhi","Warney","CSE","Student","DTDYF666","abhinav@capgemini.com",new Salary(35000,1800,1800),new BankDetails(13245,"HDFC","123423SBI"));
		Associate actualAssociate = services.getAssociateDetails(102);
		Assert.assertEquals(expectedAssociate, actualAssociate);
	}
	@Test
	public void testAcceptAssociateDetailsForValidData() {
		int expectedId = 103;
		int actualId=services.acceptAssociateDetails("ABC","PQR","abc@gmail.com","YTP","Con","ABCY736j",120000,3000,1000,1000,12345,"HDFC","jwidwg3");
		Assert.assertEquals(expectedId, actualId);
	}
	@Test(expected = AssociateDetailsNotfoundException.class)
	public void testCalculateNetMonSalaryForInvalidAssociateId()throws AssociateDetailsNotfoundException{
		services.calculateNetSalary(1434);
	}
	@Test
	public void testCalculateNetMonSalaryForValidAssociateId()throws AssociateDetailsNotfoundException{
		int expectedNetSalary=0;
		int actualNetSalary=services.calculateNetSalary(102);
		Assert.assertEquals(expectedNetSalary, actualNetSalary);
	}
	@Test
	public void testGetAllAssociatesDetails() {
		Associate associate1 = new Associate(101,78000,"Satish","Mahajan","Training","Manager","DTDYF736","satish.mahajan@capgemini.com",new Salary(35000,1800,1800),new BankDetails(12345,"HDFC","1234SBI"));	
		Associate associate2 = new Associate(102,78000,"Abhi","Warney","CSE","Student","DTDYF666","abhinav@capgemini.com",new Salary(35000,1800,1800),new BankDetails(13245,"HDFC","123423SBI"));	
		ArrayList<Associate> expectedAssociateList = new ArrayList<>();
		expectedAssociateList.add(associate1);
		expectedAssociateList.add(associate2);
		ArrayList<Associate>actualAssociateList=(ArrayList<Associate>)services.getAllAssociatesDetails();
		Assert.assertEquals(expectedAssociateList, actualAssociateList);
	}
	@After
	public void tearDownTestData() {
		PayrollDBUtil.associates.clear();
		PayrollDBUtil.ASSOCIATE_ID_COUNTER = 100;
	}
	@AfterClass
	public static void tearDownTestEnv() {
		services =null;
	}
}
