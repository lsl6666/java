package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import dao.MyConnection;
//import org.junit.Test;
import javax.swing.JOptionPane;
// TODO: Auto-generated Javadoc


/**
 * The Class dao.
 *
 * @date 2020年7月3日
 * @author nieming
 * @version v1.0
 */

public class dao {

	/** The stmt. */
	private Statement stmt;

	/** The rs. */
	private ResultSet rs;

	/** The driver name. */
	private String driverName;

	/** The uri. */
	private String uri;

	/** The conn. */
	private Connection conn;

	/**
	 * Instantiates a new dao.
	 *
	 * @param driverName the driver name
	 * @param uri        the uri
	 */
	public dao(String driverName, String uri) {
		try {
			this.driverName = driverName;
			this.uri = uri;
			conn = getConn();
			this.stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the conn.
	 *
	 * @return the conn
	 */
	private Connection getConn() {
		return MyConnection.getConnection(driverName, uri);
	}

	/**
	 * Query login info.
	 *登录查找是否存在用户名和密码  存在就可以登录  不存在就登录不进去
	 * @param name     the name
	 * @param password the password
	 * @return the int
	 * @throws SQLException the SQL exception
	 */
	public int queryLoginInfo(String name, String password) throws SQLException {

		String sql = "select userName,userPassword from logininfo where userName = ('" + name + "') and userPassword=('"
				+ password + "')";

		int flag1 = 0;
		stmt = conn.createStatement();
		boolean flag = stmt.execute(sql);
		rs = stmt.getResultSet();
		while (rs.next()) {
			if (rs.getString("userName").equals(name) && rs.getString("userPassword").equals(password)) {
				flag1 = 1;
			} else {
				flag1 = 0;
			}

		}
		if (flag1 == 1) {
			JOptionPane.showMessageDialog(null, "登录成功");
		} else {
			JOptionPane.showMessageDialog(null, "用户名或者密码错误");
		}
		return flag1;
	}

	/**
	 * Query win rate.
	 *按照用户名查找胜率
	 * @param name the name
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String queryWinRate(String name) throws SQLException {
		String sql = "select total,win, (win/total) as percent from winrate where userName =  ('" + name + "') ";
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
//			System.out.print("total：" + rs.getInt("total") + " ");
//			System.out.print("\twin：" + rs.getInt("win") + " ");
//			System.out.print("\tpercent：" + rs.getFloat("percent") * 100 + "%");
			return (rs.getFloat("percent") * 100 + "%");
			// int a =
		}
		return null;
	}
	
	
	//在数据库中找到flag=1的用户名  并返回用户名   用与判断哪个用户名登录
	public String queryFlag() throws SQLException {
		String sql = "select userName from winrate where flag=1";
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			return rs.getString("userName");
		}
		return null;
	}

	/**
	 * Insert login info.
	 *
	 * @param sql the sql
	 */
//	public void insertLoginInfo(String sql) {
//		try {
//			this.stmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * Delete login info.
	 *
	 * @param sql the sql
	 */
//	public void deleteLoginInfo(String sql) {
//		try {
//			this.stmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * Update login info.
	 *注册写入用户名和密码
	 * @param name     the name
	 * @param password the password
	 */
	public int updateLoginInfo(String name, String password) {
		String sql = "insert logininfo (userName, userPassword) values ('" + name + "','" + password + "')";
		int num=0;
		try {
			num=this.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * Update win info.
	 *注册初始化用户的游戏输赢数据
	 * @param name  the name
	 * @param total the total
	 * @param win   the win
	 * @param lose  the lose
	 */
	public void updateWinInfo(String name, int total, int win, int lose, int flag) {
		String sql = "insert winrate (userName,total,win,lose,flag) values ('" + name + "','" + total + "','" + win
				+ "','" + lose + "','" + flag + "')";
		try {
			this.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//某一个用户名登录之后就将flag置为1  用于判断哪个用户登录了
	public void updateFlagInfo(String name) {
		String sql = "update winrate set flag=flag+1 where userName=('" + name + "')";
		try {
			this.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//将数据库中的flag都置为0  方便找到flag=1的用户
	public void updateFlagZero() {
		String sql = "update winrate set flag=0";
		try {
			this.stmt.executeUpdate(sql);
		//	this.stmt.executeUpdate(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Update win info.
	 *用户赢了之后将数据库中的总局数和胜场都加一
	 * @param name the name
	 */
	public void updateWinInfo(String name) {
		String sql = "update winrate set total=total+1 where userName=('" + name + "')";
		String sql1 = "update winrate set win=win+1 where userName=('" + name + "')";
		try {
			this.stmt.executeUpdate(sql);
			this.stmt.executeUpdate(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Update lose info.
	 *用户输了之后将总局数和输场加一
	 * @param name the name
	 */
	public void updateLoseInfo(String name) {
		String sql = "update winrate set total=total+1 where userName=('" + name + "')";
		String sql1 = "update winrate set lose=lose+1 where userName=('" + name + "')";
		try {
			this.stmt.executeUpdate(sql);
			this.stmt.executeUpdate(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Close all.
	 */
	public void closeAll() {
		try {
			if (rs != null)
				rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

//public class Mysql {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//	
//		
//	}
//
//}
