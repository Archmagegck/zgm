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
	 * 出库单号
	 */
	@Column(name = "out_code")
	private String code;
	
	
	/**
	 * 出库单加密号
	 */
	@Column(name = "out_secretCode")
	private String secretCode;
	
	
	/**
	 * 总重量
	 */
	@Column(name = "out_sumWeight")
	private Double sumWeight;
	
	
	/**
	 * 总价值
	 */
	@Column(name = "out_sumValue")
	private Double sumValue = 0.0;
	
	
	/**
	 * 出货后库存总量
	 */
	@Column(name = "out_sumStock")
	private Double sumStock = 0.0;
	
	
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
	 * 出库单扫描件
	 */
	@Column(name = "out_attach")
	private String attach;
	
	
	/**
	 * 备注
	 */
	@Column(name = "out_desc")
	private String desc;
	
	
	/**
	 * 通知状态
	 * //1:监管经理助理,  2:监管经理
	 */
	@Column(name = "out_notice")
	private Integer notice = 0;
	
	
	/**
	 * 实时重量比
	 */
	private Double weightRate;
	
	
	/**
	 * 实时价格比
	 */
	private Double priceRate;
	
	
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


	public Warehouse getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getSecretCode() {
		return secretCode;
	}


	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}


	public String getSupName() {
		return supName;
	}


	public void setSupName(String supName) {
		this.supName = supName;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public AuditState getAuditState() {
		return auditState;
	}


	public void setAuditState(AuditState auditState) {
		this.auditState = auditState;
	}


	public String getAttach() {
		return attach;
	}


	public void setAttach(String attach) {
		this.attach = attach;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public List<OutsRecordDetail> getOutsRecordDetails() {
		return outsRecordDetails;
	}


	public void setOutsRecordDetails(List<OutsRecordDetail> outsRecordDetails) {
		this.outsRecordDetails = outsRecordDetails;
	}


	public Integer getNotice() {
		return notice;
	}


	public void setNotice(Integer notice) {
		this.notice = notice;
	}


	public Double getSumWeight() {
		return sumWeight;
	}


	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}


	public Double getSumValue() {
		return sumValue;
	}


	public void setSumValue(Double sumValue) {
		this.sumValue = sumValue;
	}


	public Double getSumStock() {
		return sumStock;
	}


	public void setSumStock(Double sumStock) {
		this.sumStock = sumStock;
	}


	public Double getWeightRate() {
		return weightRate;
	}


	public void setWeightRate(Double weightRate) {
		this.weightRate = weightRate;
	}


	public Double getPriceRate() {
		return priceRate;
	}


	public void setPriceRate(Double priceRate) {
		this.priceRate = priceRate;
	}


	
}
