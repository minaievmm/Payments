package ua.nure.minaev.SummaryTask4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.exception.DBException;
import ua.nure.minaev.SummaryTask4.exception.Messages;

/**
 * 
 * @author M.Minaiev
 * 
 */
public class DAOFactory {

	private static DataSource ds;

	private static final Logger LOG = Logger.getLogger(DAOFactory.class);

	/**
	 * Data source obtaining
	 * 
	 * @throws DBException
	 */
	protected DAOFactory() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/st4db");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	/**
	 * Obtains connection from the pool
	 * 
	 * @return connection
	 * @throws DBException
	 */
	protected static Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	/**
	 * Making rollback after commit, if exception throws
	 * 
	 * @param con
	 */
	protected static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

	/**
	 * Closes connection, statement and result set
	 * 
	 * @param con
	 * @param stmt
	 * @param rs
	 */
	protected static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			LOG.error("Cannot close connection: " + con + ", statement: "
					+ stmt + ", resultset: " + rs);
		}
	}

	/**
	 * Closes connection, prepared statement and result set
	 * 
	 * @param con
	 * @param pstmt
	 * @param rs
	 */
	protected static void close(Connection con, PreparedStatement pstmt,
			ResultSet rs) {
		try {
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			LOG.error("Cannot close connection: " + con
					+ ", prepared statement: " + pstmt + ", resultset: " + rs);
		}
	}

	/**
	 * Closes connection and prepared statement
	 * 
	 * @param con
	 * @param pstmt
	 */
	protected static void close(Connection con, PreparedStatement pstmt) {
		try {
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			LOG.error("Cannot close connection: " + con + ", statement: "
					+ pstmt);
		}
	}

	/**
	 * Closes connection
	 * 
	 * @param con
	 */
	protected static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			LOG.error("Cannot close connection: " + con);
		}
	}

	/**
	 * Closes PreparedStatement
	 * 
	 * @param pstmt
	 */
	protected static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			LOG.error("Cannot close PreparedStatement: " + pstmt);
		}
	}
}
