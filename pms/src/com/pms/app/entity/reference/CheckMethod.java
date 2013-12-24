package com.pms.app.entity.reference;

/**
 * 检测方法
 * @author wangzz
 */
public enum CheckMethod {
	
	/**
	 * 光谱法
	 */
	Spectrum {
		@Override
		public String getTitle() {
			return "男";
		}
	},
	
	
	/**
	 * 溶金法
	 */
	Dissolve {
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
