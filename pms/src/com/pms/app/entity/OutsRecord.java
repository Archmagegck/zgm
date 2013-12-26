package com.pms.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.pms.app.entity.reference.AuditState;
import com.pms.app.entity.reference.PickType;

/**
 * 出库单
 * @author wangzz
 */
@Entity
@Table(name = "t_outsRecord")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OutsRecord {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "out_id")
	private String id;
	
	/**
	 * 提货通知书
	 */
	@Column(name = "out_pickNoticeUrl")
	private String pickNoticeUrl;
	
	
	/**
	 * 提货类型
	 */
	@Column(name = "out_pickType")
	private PickType pickType;
	
	
	/**
	 * 出库单号
	 */
	@Column(name = "out_code")
	private String code;
	
	
	/**
	 * 总重量
	 */
	@Column(name = "out_sumWeight")
	private String sumWeight;
	
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	
	/**
	 * 提货人姓名
	 */
	@Column(name = "out_picker")
	private String picker;
	
	
	/**
	 * 提货人身份证
	 */
	@Column(name = "out_pickerIdCard")
	private String pickerIdCard;
	
	
	/**
	 * 提货人身份证扫描件
	 */
	@Column(name = "out_pickerIdCardPic")
	private String pickerIdCardPic;
	
	
	/**
	 * 存储地点
	 */
	@Column(name = "out_storage")
	private String storage;
	
	
	/**
	 * 操作监管员员姓名
	 */
	@Column(name = "out_supName")
	private String supName;
	
	
	/**
	 * 出库时间
	 */
	@Column(name = "out_date")
	private Date outDate = new Date();
	
	
	/**
	 * 审核状态
	 */
	@Column(name = "out_auditState")
	private AuditState auditState;
	
	
	/**
	 * 扫描件状态<br>
	 * 0:未上传<br>
	 * 1:已上传
	 */
	@Column(name = "out_attachState")
	private int attachState = 0;
	
	
	/**
	 * 出库单URL
	 */
	@Column(name = "out_inRecordUrl")
	private String outRecordUrl;
	
	
	/**
	 * 提货通知书（回执）URL
	 */
	@Column(name = "out_pickFeedbackUrl")
	private String pickFeedbackUrl;
	
	
	/**
	 * 质物清单URL
	 */
	@Column(name = "out_pledgeUrl")
	private String pledgeUrl;
	
	
	/**
	 * 明细
	 */
	@OneToMany(mappedBy = "outsRecord")
	private List<OutsRecordDetail> outsRecordDetails = new ArrayList<OutsRecordDetail>();


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPickNoticeUrl() {
		return pickNoticeUrl;
	}


	public void setPickNoticeUrl(String pickNoticeUrl) {
		this.pickNoticeUrl = pickNoticeUrl;
	}


	public PickType getPickType() {
		return pickType;
	}


	public void setPickType(PickType pickType) {
		this.pickType = pickType;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getSumWeight() {
		return sumWeight;
	}


	public void setSumWeight(String sumWeight) {
		this.sumWeight = sumWeight;
	}


	public Warehouse getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}


	public String getPicker() {
		return picker;
	}


	public void setPicker(String picker) {
		this.picker = picker;
	}


	public String getPickerIdCard() {
		return pickerIdCard;
	}


	public void setPickerIdCard(String pickerIdCard) {
		this.pickerIdCard = pickerIdCard;
	}


	public String getPickerIdCardPic() {
		return pickerIdCardPic;
	}


	public void setPickerIdCardPic(String pickerIdCardPic) {
		this.pickerIdCardPic = pickerIdCardPic;
	}


	public String getStorage() {
		return storage;
	}


	public void setStorage(String storage) {
		this.storage = storage;
	}


	public String getSupName() {
		return supName;
	}


	public void setSupName(String supName) {
		this.supName = supName;
	}


	public Date getOutDate() {
		return outDate;
	}


	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}


	public AuditState getAuditState() {
		return auditState;
	}


	public void setAuditState(AuditState auditState) {
		this.auditState = auditState;
	}


	public int getAttachState() {
		return attachState;
	}


	public void setAttachState(int attachState) {
		this.attachState = attachState;
	}


	public String getOutRecordUrl() {
		return outRecordUrl;
	}


	public void setOutRecordUrl(String outRecordUrl) {
		this.outRecordUrl = outRecordUrl;
	}


	public String getPickFeedbackUrl() {
		return pickFeedbackUrl;
	}


	public void setPickFeedbackUrl(String pickFeedbackUrl) {
		this.pickFeedbackUrl = pickFeedbackUrl;
	}


	public String getPledgeUrl() {
		return pledgeUrl;
	}


	public void setPledgeUrl(String pledgeUrl) {
		this.pledgeUrl = pledgeUrl;
	}


	public List<OutsRecordDetail> getOutsRecordDetails() {
		return outsRecordDetails;
	}


	public void setOutsRecordDetails(List<OutsRecordDetail> outsRecordDetails) {
		this.outsRecordDetails = outsRecordDetails;
	}
}
