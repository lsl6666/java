//package dao;
//
//import static org.junit.Assert.assertEquals;
//
//import java.sql.SQLException;
//
//import javax.swing.JOptionPane;
//
//import org.junit.Test;
//
//public class daoTest {
//
//	private dao sqlInfo = new dao(MyConnection.driverName, MyConnection.uri);
//	
//	@Test
//	public void testupdateLoginInfo() {
//		String name="abc";
//		String password="123456";
//		int num=sqlInfo.updateLoginInfo(name, password);
//		assertEquals(1,num);
//	}
//
//	@Test
//	public void testQueryLoginInfo() throws SQLException {
//		String name="abc";
//		String password="123456";
//		int flag=sqlInfo.queryLoginInfo(name, password);
//		assertEquals(1,flag);
//	}
//	
//}
