/**************************************************************************
 * $Date : 2018-06-27 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationStatusCheck;

import java.io.Serializable;

public class ApplicationStatusCheckLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3002213596232890612L;

    /**
     * for apply list
     */

    private String date;

    private String agencyName;

    private String outletName;

    private String staffName;

    private String customerName;

    private String type;

    /**
     * for summary list
     */
    private int sumNo;

    private String sumAgentName;

    private String sumOutletName;

    private int noOfApp;

    private int first;

    private int followUp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSumAgentName() {
        return sumAgentName;
    }

    public void setSumAgentName(String sumAgentName) {
        this.sumAgentName = sumAgentName;
    }

    public String getSumOutletName() {
        return sumOutletName;
    }

    public void setSumOutletName(String sumOutletName) {
        this.sumOutletName = sumOutletName;
    }

    public int getNoOfApp() {
        return noOfApp;
    }

    public void setNoOfApp(int noOfApp) {
        this.noOfApp = noOfApp;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getFollowUp() {
        return followUp;
    }

    public void setFollowUp(int followUp) {
        this.followUp = followUp;
    }

    public int getSumNo() {
        return sumNo;
    }

    public void setSumNo(int sumNo) {
        this.sumNo = sumNo;
    }
}
