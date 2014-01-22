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
import org.joda.time.DateTime;

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
	 * 提货通知书编号
	 */
	@Column(name = "out_pickNoticeCode")
	private String pickNoticeCode;
	
	
	/**
	 * 通知状态<br>
	 * 1:通知<br>
	 * 0:不通知
	 */
	@Column(name = "out_notice")
	private Integer notice = 0;
	
	
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
	private Double sumWeight;
	
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	
	/**
	 * 委托方
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "d_id")
	private Delegator delegator;
	
	
	/**
	 * 监管客户
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sc_id")
	private SupervisionCustomer supervisionCustomer;
	
	
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
	private Date date = new Date();
	
	
	/**
	 * 审核状态
	 */
	@Column(name = "out_auditState")
	private AuditState auditState = AuditState.Wait;
	
	
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
	@Column(name = "out_outRecordUrl")
	private String outRecordUrl;
	
	
	/**
	 * 提货通知书（回执）URL
	 */
	@Column(name = "out_pickFeedbackUrl")
	private String pickFeedbackUrl;
	
	
	/**
	 * 质物清单
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pr_id")
	private PledgeRecord pledgeRecord;
	
	
	/**
	 * 明细
	 */
	@OneToMany(mappedBy = "outsRecord")
	private List<OutsRecordDetail> outsRecordDetails = new ArrayList<OutsRecordDetail>();
	
	
	public String getDateStr() {
		return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
	}


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


	public Double getSumWeight() {
		return sumWeight;
	}


	public void setSumWeight(Double sumWeight) {
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


	public List<OutsRecordDetail> getOutsRecordDetails() {
		return outsRecordDetails;
	}


	public void setOutsRecordDetails(List<OutsRecordDetail> outsRecordDetails) {
		this.outsRecordDetails = outsRecordDetails;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public PledgeRecord getPledgeRecord() {
		return pledgeRecord;
	}


	public void setPledgeRecord(PledgeRecord pledgeRecord) {
		this.pledgeRecord = pledgeRecord;
	}


	public Delegator getDelegator() {
		return delegator;
	}


	public void setDelegator(Delegator delegator) {
		this.delegator = delegator;
	}


	public SupervisionCustomer getSupervisionCustomer() {
		return supervisionCustomer;
	}


	public void setSupervisionCustomer(SupervisionCustomer supervisionCustomer) {
		this.supervisionCustomer = supervisionCustomer;
	}


	public String getPickNoticeCode() {
		return pickNoticeCode;
	}


	public void setPickNoticeCode(String pickNoticeCode) {
		this.pickNoticeCode = pickNoticeCode;
	}


	public Integer getNotice() {
		return notice;
	}


	public void setNotice(Integer notice) {
		this.notice = notice;
	}
}
