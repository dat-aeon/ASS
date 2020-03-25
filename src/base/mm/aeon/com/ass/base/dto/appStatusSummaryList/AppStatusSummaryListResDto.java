/**************************************************************************
 * $Date : 2018-08-28 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.appStatusSummaryList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AppStatusSummaryListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -8085767478555013466L;
        
    private  String sumAgentName;
    
    private String sumOutletName;
    
    private int noOfApp;
    
    private int first;
    
    private int followUp;

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
}   
