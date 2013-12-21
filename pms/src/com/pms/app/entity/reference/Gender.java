package com.pms.app.entity.reference;

/**
 * 性别
 * @author wangzz
 */
public enum Gender {
	
	/**
	 * 男
	 */
	Man {
		@Override
		public String getTitle() {
			return "男";
		}
	},
	
	
	/**
	 * 女
	 */
	Woman {
		@Override
		public String getTitle() {
			return "女";
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
