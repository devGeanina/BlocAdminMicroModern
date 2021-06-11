package com.blocadminmicromodern.webservice.utils;

import java.util.HashMap;
import java.util.Map;

public enum BudgetType {

	MONTHLY_ALLOCATED((short) 1, "Monthly"), YEARLY_ALLOCATED((short) 2, "Yearly"),
	BUILDING_MAINTAINANCE((short) 3, "Maintainance"), COMMON_FOND((short) 4, "Common"),
	EMPLOYEE_SALARY((short) 5, "Employee"), OTHER((short) 6, "Other");

	private short type;
	private String name;
	private static final Map<String, BudgetType> labelMap = new HashMap<String, BudgetType>();

	static {
		for (BudgetType b : values()) {
			labelMap.put(b.name, b);
		}
	}

	private BudgetType(short type, String name) {
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

	public static BudgetType valueOfLabel(String label) {
		return labelMap.get(label);
	}

	public static BudgetType getNameByCode(short code) {
		for (BudgetType b : BudgetType.values()) {
			if (code == b.type) {
				return b;
			}
		}
		return null;
	}
}
