package ua.nure.minaev.SummaryTask4.db.entity;

import ua.nure.minaev.SummaryTask4.db.entity.User;

/**
 * 
 * @author M.Minaiev
 *
 */

public enum Role {
	CLIENT, ADMIN;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
