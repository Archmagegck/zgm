package com.pms.app.entity.reference;

/**
 * 检测状态
 * @author wangzz
 */
public enum CheckState {
	
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
	 * 通过
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
