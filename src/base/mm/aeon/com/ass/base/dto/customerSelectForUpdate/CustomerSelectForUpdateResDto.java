/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerSelectForUpdate;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResDto;

public class CustomerSelectForUpdateResDto implements IResDto {

    private static final long serialVersionUID = 2672207836115099916L;

    private int updCount;

    private String agreementCode;

    private String customerName;

    private String customerPhoneNo;

    private int customerPaymentAmount;

    private int customerPaymentTimer;
    
    private String customerPaymentPromiseDate;

    private String customerCallStatus;

    private String replyTypeId;

    private Timestamp customerReplyDate;

    private String paidStatus;
    
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

    public String getCustomerPaymentPromiseDate() {
        return customerPaymentPromiseDate;
    }

    public void setCustomerPaymentPromiseDate(String customerPaymentPromiseDate) {
        this.customerPaymentPromiseDate = customerPaymentPromiseDate;
    }

    public String getCustomerCallStatus() {
        return customerCallStatus;
    }

    public void setCustomerCallStatus(String customerCallStatus) {
        this.customerCallStatus = customerCallStatus;
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

}
