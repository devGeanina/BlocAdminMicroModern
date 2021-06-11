package com.blocadminmicromodern.webservice.utils;

import java.util.HashMap;
import java.util.Map;

public enum HouseholdRequestType {

	COMPLAINT((short) 1, "Complaint"), DOC_RELEASE((short) 2, "Document"), OTHER((short) 3, "Other");

	private short type;
	private String name;
	private static final Map<String, HouseholdRequestType> labelMap = new HashMap<String, HouseholdRequestType>();

	static {
		for (HouseholdRequestType h : values()) {
			labelMap.put(h.name, h);
		}
	}

	private HouseholdRequestType(short type, String name) {
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

	public static HouseholdRequestType valueOfLabel(String label) {
		return labelMap.get(label);
	}

	public static HouseholdRequestType getNameByCode(short code) {
		for (HouseholdRequestType h : HouseholdRequestType.values()) {
			if (code == h.type) {
				return h;
			}
		}
		return null;
	}
}
