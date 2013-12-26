package com.pms.app.entity.reference;

/**
 * 审核状态
 * @author wangzz
 */
public enum AuditState {
	
	/**
	 * 通过
	 */
	Pass {
		@Override
		public String getTitle() {
			return "通过";
		}
	},
	
	
	/**
	 * 拒绝
	 */
	Refuse {
		@Override
		public String getTitle() {
			return "拒绝";
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
