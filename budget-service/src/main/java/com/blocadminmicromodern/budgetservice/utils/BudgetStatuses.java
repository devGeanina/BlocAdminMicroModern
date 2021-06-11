package com.blocadminmicromodern.budgetservice.utils;

public class BudgetStatuses {

	public enum BUDGET_STATUS {
		AVAILABLE((short) 1), UNAVAILABLE((short) 2), PENDING((short) 3);

		private short type;

		private BUDGET_STATUS(short type) {
			this.type = type;
		}

		public short getType() {
			return type;
		}

		public void setType(short type) {
			this.type = type;
		}
	}

	public enum BUDGET_TYPE {

		MONTHLY((short) 1), YEARLY((short) 2), BUILDING_MAINTAINANCE((short) 3), COMMON_FOND((short) 4),
		EMPLOYEE_SALARY((short) 5), OTHER((short) 6);

		private short type;

		private BUDGET_TYPE(short type) {
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
