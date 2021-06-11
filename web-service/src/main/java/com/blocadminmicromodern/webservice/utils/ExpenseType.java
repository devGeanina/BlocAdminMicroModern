package com.blocadminmicromodern.webservice.utils;

import java.util.HashMap;
import java.util.Map;

public enum ExpenseType {

	MONTHLY((short) 1, "Monthly"), YEARLY((short) 2, "Yearly"), BUILDING_MAINTAINANCE((short) 3, "Maintainance"),
	COMMON_FOND((short) 4, "Common"), EMPLOYEE_SALARY((short) 5, "Employee"), OTHER((short) 6, "Other");

	private short type;
	private String name;
	private static final Map<String, ExpenseType> labelMap = new HashMap<String, ExpenseType>();

	static {
		for (ExpenseType e : values()) {
			labelMap.put(e.name, e);
		}
	}

	private ExpenseType(short type, String name) {
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

	public static ExpenseType valueOfLabel(String label) {
		return labelMap.get(label);
	}

	public static ExpenseType getNameByCode(short code) {
		for (ExpenseType e : ExpenseType.values()) {
			if (code == e.type) {
				return e;
			}
		}
		return null;
	}
}
