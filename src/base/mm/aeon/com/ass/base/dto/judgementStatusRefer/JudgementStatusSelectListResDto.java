/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.judgementStatusRefer;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class JudgementStatusSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -2446688021021106861L;

    private Timestamp firstApplyDate;

    private Timestamp judgementDate;

    private String agencyName;

    private String outletName;

    private String customerName;

    private String applicationNo;

    private int status;

    public Timestamp getFirstApplyDate() {
        return firstApplyDate;
    }

    public void setFirstApplyDate(Timestamp firstApplyDate) {
        this.firstApplyDate = firstApplyDate;
    }

    public Timestamp getJudgementDate() {
        return judgementDate;
    }

    public void setJudgementDate(Timestamp judgementDate) {
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
