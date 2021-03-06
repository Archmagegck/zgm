package com.pms.app.entity.reference;

/**
 * 贷款（融资）方式
 * @author wangzz
 */
public enum Loans {
	
	/**
	 * 租赁方式
	 */
	Rent {
		@Override
		public String getTitle() {
			return "租赁方式";
		}
	},
	
	
	/**
	 * 现金方式
	 */
	Cash {
		@Override
		public String getTitle() {
			return "现金方式";
		}
	};
	
	@Override
	public String toString() {
		return this.getTitle();
	}

	public abstract String getTitle();   
	
    public int getValue(){   
        return this.ordinal();   
    }
}
