/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerUpdate;

import java.sql.Date;
import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "Customer")
public class CustomerUpdateReqDto implements IReqDto {

    private static final long serialVersionUID = 2672207836115099916L;

    private String agreementCode;

    private String customerName;

    private String customerPhoneNo;

    private int customerPaymentAmount;

    private int customerPaymentTimer;
    
    private String comment;

    private String customerCallStatus;

    private String updatedBy;

    private String replyTypeId;

    private Timestamp customerReplyDate;

    private String paidStatus;

    private int updCount;

    private Date actualPaymentDate;
    
    private int actualPaymentAmount;
    
    public String getAgreementCode() {
        return agreementCode;
    }

    public void setAgreementCode(String agreementCode) {
        this.agreementCode = agreementCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public int getCustomerPaymentAmount() {
        return customerPaymentAmount;
    }

    public void setCustomerPaymentAmount(int customerPaymentAmount) {
        this.customerPaymentAmount = customerPaymentAmount;
    }

    public int getCustomerPaymentTimer() {
        return customerPaymentTimer;
    }

    public void setCustomerPaymentTimer(int customerPaymentTimer) {
        this.customerPaymentTimer = customerPaymentTimer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerCallStatus() {
        return customerCallStatus;
    }

    public void setCustomerCallStatus(String customerCallStatus) {
        this.customerCallStatus = customerCallStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getReplyTypeId() {
        return replyTypeId;
    }

    public void setReplyTypeId(String replyTypeId) {
        this.replyTypeId = replyTypeId;
    }

    public Timestamp getCustomerReplyDate() {
        return customerReplyDate;
    }

    public void setCustomerReplyDate(Timestamp customerReplyDate) {
        this.customerReplyDate = customerReplyDate;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public int getUpdCount() {
        return updCount;
    }

    public void setUpdCount(int updCount) {
        this.updCount = updCount;
    }

    public Date getActualPaymentDate() {
        return actualPaymentDate;
    }

    public void setActualPaymentDate(Date actualPaymentDate) {
        this.actualPaymentDate = actualPaymentDate;
    }

    public int getActualPaymentAmount() {
        return actualPaymentAmount;
    }

    public void setActualPaymentAmount(int actualPaymentAmount) {
        this.actualPaymentAmount = actualPaymentAmount;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.UPDATE;
    }

}
