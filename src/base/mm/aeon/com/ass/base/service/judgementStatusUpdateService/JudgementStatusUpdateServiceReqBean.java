/**************************************************************************
 * $Date: 2018-09-21$
 * $Author: SHOON LATT WINNE $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.judgementStatusUpdateService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class JudgementStatusUpdateServiceReqBean extends AbstractServiceReqBean {

    private static final long serialVersionUID = -6473762488720461939L;

    private int status;
    
    private String applicationNo;
    
    private String customerName;
    
    private Timestamp judgementDate;
    
    private String agreementNo;
    
    private Integer financeTerm;
    
    private Double financeAmount;
    
    private Timestamp uploadDate;
    
    private String updatedBy;
   
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Timestamp getJudgementDate() {
        return judgementDate;
    }

    public void setJudgementDate(Timestamp judgementDate) {
        this.judgementDate = judgementDate;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public Integer getFinanceTerm() {
        return financeTerm;
    }

    public void setFinanceTerm(Integer financeTerm) {
        this.financeTerm = financeTerm;
    }

    public Double getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(Double financeAmount) {
        this.financeAmount = financeAmount;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "JUDGEMENTUI";
    }

}
