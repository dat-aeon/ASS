/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.leadTimeReport;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class LeadTimeReportResDto implements IResServiceDto {

    private static final long serialVersionUID = -4014626560373692299L;

    private String customerName;

    private String nrc;

    private String agencyName;

    private String outletName;

    private String applicationNo;

    private String type;

    private String applyStarted;

    private String applyFinished;

    private String sent;

    private String received;

    private String registering;

    private String registered;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplyStarted() {
        return applyStarted;
    }

    public void setApplyStarted(String applyStarted) {
        this.applyStarted = applyStarted;
    }

    public String getApplyFinished() {
        return applyFinished;
    }

    public void setApplyFinished(String applyFinished) {
        this.applyFinished = applyFinished;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getRegistering() {
        return registering;
    }

    public void setRegistering(String registering) {
        this.registering = registering;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

}
