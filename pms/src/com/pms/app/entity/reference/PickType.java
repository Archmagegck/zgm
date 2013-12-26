package com.pms.app.entity.reference;

/**
 * 提货类型
 * @author wangzz
 */
public enum PickType {
	
	/**
	 * 部分解除
	 */
	Part {
		@Override
		public String getTitle() {
			return "部分解除";
		}
	},
	
	
	/**
	 * 全部解除
	 */
	All {
		@Override
		public String getTitle() {
			return "全部解除";
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
