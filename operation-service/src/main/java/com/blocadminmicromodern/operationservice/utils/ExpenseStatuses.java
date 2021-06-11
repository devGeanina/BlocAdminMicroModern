package com.blocadminmicromodern.operationservice.utils;

public class ExpenseStatuses {

	public enum EXPENSE_TYPE {

		MONTHLY((short) 1), YEARLY((short) 2), BUILDING_MAINTAINANCE((short) 3), COMMON_FOND((short) 4),
		EMPLOYEE_SALARY((short) 5), OTHER((short) 6);

		private short type;

		private EXPENSE_TYPE(short type) {
			this.type = type;
		}

		public short getType() {
			return type;
		}

		public void setType(short type) {
			this.type = type;
		}
	}
}
