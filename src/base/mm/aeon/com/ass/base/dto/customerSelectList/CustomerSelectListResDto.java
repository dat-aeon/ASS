/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerSelectList;

import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class CustomerSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 839224506276806413L;

    private int updCount;
    
    private String agreementCode;

    private String customerName;

    private String customerPhoneNo;

    private String customerPaymentAmount;

    private String customerPaymentTimer;

    private String customerCallStatus;

    private String staffId;

    private String customerReplyTypeName;
    
    private String customerReplyTypeId;

    private Date customerReplyDate;
    
    private String comment;

    private Date createdDate;
    
    private String confirmStatus;
    
    private Date actualPaymentDate;
    
    private String actualPaymentAmount;
    
    public int getUpdCount() {
        return updCount;
    }

    public void setUpdCount(int updCount) {
        this.updCount = updCount;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

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

    public String getCustomerPaymentAmount() {
        return customerPaymentAmount;
    }

    public void setCustomerPaymentAmount(String customerPaymentAmount) {
        this.customerPaymentAmount = customerPaymentAmount;
    }

    public String getCustomerPaymentTimer() {
        return customerPaymentTimer;
    }

    public void setCustomerPaymentTimer(String customerPaymentTimer) {
        this.customerPaymentTimer = customerPaymentTimer;
    }

    public String getCustomerCallStatus() {
        return customerCallStatus;
    }

    public void setCustomerCallStatus(String customerCallStatus) {
        this.customerCallStatus = customerCallStatus;
    }


    public Date getCustomerReplyDate() {
        return customerReplyDate;
    }

    public void setCustomerReplyDate(Date customerReplyDate) {
        this.customerReplyDate = customerReplyDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCustomerReplyTypeName() {
        return customerReplyTypeName;
    }

    public void setCustomerReplyTypeName(String customerReplyTypeName) {
        this.customerReplyTypeName = customerReplyTypeName;
    }

    public String getCustomerReplyTypeId() {
        return customerReplyTypeId;
    }

    public void setCustomerReplyTypeId(String customerReplyTypeId) {
        this.customerReplyTypeId = customerReplyTypeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Date getActualPaymentDate() {
        return actualPaymentDate;
    }

    public void setActualPaymentDate(Date actualPaymentDate) {
        this.actualPaymentDate = actualPaymentDate;
    }

    public String getActualPaymentAmount() {
        return actualPaymentAmount;
    }

    public void setActualPaymentAmount(String actualPaymentAmount) {
        this.actualPaymentAmount = actualPaymentAmount;
    }

}
