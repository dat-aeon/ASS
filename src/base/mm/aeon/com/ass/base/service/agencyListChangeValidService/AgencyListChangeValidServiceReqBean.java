/***********************************************************************
 * $Date: 2018-08-10 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListChangeValidService;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AgencyListChangeValidServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6473762488720461939L;
    
    private int agencyId;

    private String disabledBy;

    private Timestamp disableddDate;
    
    private Timestamp oldDisableDate;
    
    private Timestamp agencyUptDate;
    
    private boolean isEnable;
    
    public String getDisabledBy() {
        return disabledBy;
    }

    public void setDisabledBy(String disabledBy) {
        this.disabledBy = disabledBy;
    }

    public Timestamp getDisableddDate() {
        return disableddDate;
    }

    public void setDisableddDate(Timestamp disableddDate) {
        this.disableddDate = disableddDate;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
    
    public Timestamp getAgencyUptDate() {
        return agencyUptDate;
    }

    public void setAgencyUptDate(Timestamp agencyUptDate) {
        this.agencyUptDate = agencyUptDate;
    }

    public Timestamp getOldDisableDate() {
        return oldDisableDate;
    }

    public void setOldDisableDate(Timestamp oldDisableDate) {
        this.oldDisableDate = oldDisableDate;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "AGENCYDIS";
    }

}
