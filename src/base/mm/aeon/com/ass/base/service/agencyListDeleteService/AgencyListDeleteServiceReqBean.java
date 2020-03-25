/***********************************************************************
 * $Date: 2018-08-13 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.service.agencyListDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class AgencyListDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = -6473762488720461939L;
    
    private int agencyId;   
    
    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "AGENCYDLE";
    }

}
