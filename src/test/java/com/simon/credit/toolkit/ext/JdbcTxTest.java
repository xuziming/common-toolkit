package com.simon.credit.toolkit.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTxTest {

	public static void main(String[] args) {
		Connection connection = getConnection();
		try {
			/**
			 * Newly created Connection objects are in auto-commit mode by default, 
			 * which means that individual SQL statements are committed automatically when the statement iscompleted. 
			 * To be able to group SQL statements into transactions and commit them or roll them back as a unit, 
			 * auto-commit must be disabled by calling the method setAutoCommit with false as its argument. 
			 * When auto-commit is disabled, the user must call either the commit or rollback method explicitly to end a transaction.
			 * 翻译过来就是：
			 * 假设连接处于自动提交模式下，则其全部的SQL语句将作为一个个单独的事务执行并提交.
			 * 否则，其SQL语句将作为事务组，直到调用commit方法或rollback方法为止.
			 */
			connection.setAutoCommit(false);
			insertUser(connection);
			insertAddress(connection);

			connection.commit();// 事务提交
		} catch (SQLException sqle) {
			System.out.println("************事务处理出现异常***********");
			sqle.printStackTrace();
			try {
				connection.rollback();
				System.out.println("**********事务回滚成功************");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			closeConnection(connection);
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String jdbcUrl = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
			connection = (Connection) DriverManager.getConnection(jdbcUrl, "root", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void insertUser(Connection connection) throws SQLException {
		String sql = "insert into tbl_user(id,name,password,email) values(10,'xiongda','123','xiongda@qq.com')";
		Statement statement = connection.createStatement();
		int rowCount = statement.executeUpdate(sql);
		System.out.println("向用户表插入了" + rowCount + "条记录！");
	}

	public static void insertAddress(Connection connection) throws SQLException {
		String sql = "insert into tbl_address(id,city,country,user_id) values(1,'hangzhou','china',10)";
		Statement statement = connection.createStatement();
		int rowCount = statement.executeUpdate(sql);
		System.out.println("向地址表插入了" + rowCount + "条记录！");
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}