/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusUpload;

import java.io.Serializable;

public class JudgementStatusUploadSuccessListBean implements Serializable {

    private static final long serialVersionUID = 3002213596232890612L;

    private String judgementDate;

    private String agencyName;

    private String outletName;
    
    private String nrc;

    private String userName;

    private String applicationNo;

    private String status;

    private String agreementNo;

    private String financeTerm;

    private String financeAmount;

    public String getJudgementDate() {
        return judgementDate;
    }

    public void setJudgementDate(String judgementDate) {
        this.judgementDate = judgementDate;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getFinanceTerm() {
        return financeTerm;
    }

    public void setFinanceTerm(String financeTerm) {
        this.financeTerm = financeTerm;
    }

    public String getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(String financeAmount) {
        this.financeAmount = financeAmount;
    }

}
