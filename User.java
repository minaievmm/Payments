package ua.nure.minaev.SummaryTask4.db.entity;

/**
 * 
 * @author M.Minaiev
 *
 */
public class User {

	private static final long serialVersionUID = -6589036256149495388L;
	
	private int id;
	
	private String login;
	
	private String password;
	
	private String firstName;
	
	private String lastName;

	private int roleId;
	
	private int cardQuantity;
	
	private int paymentQuantity;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", roleId=" + roleId + "]";
	}

	public int getCardQuantity() {
		return cardQuantity;
	}

	public void setCardQuantity(int cardQuantity) {
		this.cardQuantity = cardQuantity;
	}

	public int getPaymentQuantity() {
		return paymentQuantity;
	}

	public void setPaymentQuantity(int paymentQuantity) {
		this.paymentQuantity = paymentQuantity;
	}
	
}
