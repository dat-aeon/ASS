/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.customerSelectList;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "Customer")
public class CustomerSelectListReqDto implements IReqServiceDto {

    private static final long serialVersionUID = 2672207836115099916L;

    private String agreementCode;

    private String customerName;

    private String customerPhoneNo;

    private int customerPaymentAmount;

    private int customerPaymentTimer;

    private String status;

    private String staffId;

    private String replyTypeId;

    private Timestamp customerReplyDate;
    
    private boolean isDelete;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}
