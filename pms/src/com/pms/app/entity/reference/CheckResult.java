package com.pms.app.entity.reference;

/**
 * 检测结果
 * @author wangzz
 */
public enum CheckResult {
	
	/**
	 * 合格
	 */
	Ok {
		@Override
		public String getTitle() {
			return "合格";
		}
	},
	
	/**
	 * 不合格
	 */
	Fail {
		@Override
		public String getTitle() {
			return "不合格";
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
