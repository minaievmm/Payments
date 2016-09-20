package ua.nure.minaev.SummaryTask4.db.entity;

import ua.nure.minaev.SummaryTask4.db.entity.Payment;
/**
 * 
 * @author M.Minaiev
 *
 */
public enum State {
	
	PREPARED, SENT;
	
	public static State getState(Payment payment) {
		int stateId = payment.getStateId();
		return State.values()[stateId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
