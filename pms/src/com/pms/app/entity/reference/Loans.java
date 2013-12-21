package com.pms.app.entity.reference;

/**
 * 贷款方式
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
	 * 融资方式
	 */
	Finance {
		@Override
		public String getTitle() {
			return "融资方式";
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
