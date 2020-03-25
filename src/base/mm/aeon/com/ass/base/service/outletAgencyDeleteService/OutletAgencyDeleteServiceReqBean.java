/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletAgencyDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class OutletAgencyDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -8350516919910993041L;

    private Integer agencyOutletId;

    private Integer outletId;

    @Override
    public String getServiceId() {
        return "OUTLETAGENCYSD";
    }

    public Integer getAgencyOutletId() {
        return agencyOutletId;
    }

    public void setAgencyOutletId(Integer agencyOutletId) {
        this.agencyOutletId = agencyOutletId;
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

}
