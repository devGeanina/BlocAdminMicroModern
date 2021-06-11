package com.blocadminmicromodern.webservice.utils;

import java.util.HashMap;
import java.util.Map;

public enum UserType {

	OWNER((short) 1, "Owner"), ASSOCIATE((short) 2, "Associate"), EMPLOYEE((short) 3, "Employee"),
	OTHER((short) 4, "Other");

	private short type;
	private String name;
	private static final Map<String, UserType> labelMap = new HashMap<String, UserType>();

	static {
		for (UserType u : values()) {
			labelMap.put(u.name, u);
		}
	}

	private UserType(short type, String name) {
		this.type = type;
		this.name = name;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static UserType valueOfLabel(String label) {
		return labelMap.get(label);
	}

	public static UserType getNameByCode(short code) {
		for (UserType u : UserType.values()) {
			if (code == u.type) {
				return u;
			}
		}
		return null;
	}
}
