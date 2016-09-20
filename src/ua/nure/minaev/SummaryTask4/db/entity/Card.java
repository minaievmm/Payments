package ua.nure.minaev.SummaryTask4.db.entity;
/**
 * 
 * @author M.Minaiev
 *
 */
public class Card {

	private static final long serialVersionUID = -658515219495388L;
	
	private int cardNumber;
	
	private int cardBalance;
	
	private int userCard;

	private boolean isBlocked;
	
	private boolean isRequested;
	
	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isRequested() {
		return isRequested;
	}

	public void setRequested(boolean isRequested) {
		this.isRequested = isRequested;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "Card [cardNumber=" + cardNumber + ", cardBalance="
				+ cardBalance + "]";
	}

	public int getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(int cardBalance) {
		this.cardBalance = cardBalance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getUserCard() {
		return userCard;
	}

	public void setUserCard(int userCard) {
		this.userCard = userCard;
	}
}
