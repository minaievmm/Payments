package ua.nure.minaev.SummaryTask4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.DBException;
import ua.nure.minaev.SummaryTask4.db.Fields;
import ua.nure.minaev.SummaryTask4.exception.Messages;

/**
 * 
 * @author M.Minaiev
 * 
 */
public class UserDAO extends DAOFactory {

	protected UserDAO() throws DBException {
		super();
	}

	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";

	private static final String SQL_REGISTER_NEW_USER = "INSERT INTO users (user_id, login, password, first_name, last_name, role_id) "
			+ "VALUES (null, ?, ?, ?, ?, 0)";

	private static final Logger LOG = Logger.getLogger(UserDAO.class);

	private static UserDAO instance;

	public static synchronized UserDAO getInstance() throws DBException {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	/**
	 * Adds new user and puts it into DB
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public boolean addNewUser(User user) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_REGISTER_NEW_USER);
			pstmt.setString(1, user.getLogin());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot add new user: " + ex);
		} finally {
			close(con, pstmt);
		}
		return true;
	}

	/**
	 * Generates list of all users from DB
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<User> findAllUsers() throws DBException {
		List<User> users = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(Fields.USER_ID));
				user.setLogin(rs.getString(Fields.USER_LOGIN));
				user.setPassword(rs.getString(Fields.USER_PASSWORD));
				user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
				user.setLastName(rs.getString(Fields.USER_LAST_NAME));
				user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
				users.add(user);
			}
		} catch (SQLException ex) {
			LOG.error("Cannot extract all users: " + ex);
		} finally {
			close(con, stmt, rs);
		}
		return users;
	}

	/**
	 * Finds user by it's login
	 * 
	 * @param login
	 * @return
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user by login: " + ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Finds user by it's id
	 * 
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public User findUserById(int id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user by id: " + ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Extracts user from DB if all fields are needed
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.USER_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}

}
