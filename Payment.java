package ua.nure.minaev.SummaryTask4.db.entity;

import java.sql.Date;
/**
 * 
 * @author M.Minaiev
 *
 */
public class Payment {
	
	private static final long serialVersionUID = -6589036254149495388L;
	
	private int paymentId;
	
	private Date paymentDate;
	
	private int stateId;
	
	private int amount;
	
	private int userId;
	
	private int userRecipient;
	
	private int userCard;
	
	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", paymentDate="
				+ paymentDate + ", amount=" + amount + ", userId=" + userId
				+ ", userRecipient=" + userRecipient + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserRecipient() {
		return userRecipient;
	}

	public void setUserRecipient(int userRecipient) {
		this.userRecipient = userRecipient;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getUserCard() {
		return userCard;
	}

	public void setUserCard(int userCard) {
		this.userCard = userCard;
	}
}

