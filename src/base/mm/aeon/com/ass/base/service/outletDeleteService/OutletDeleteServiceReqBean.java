/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.outletDeleteService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class OutletDeleteServiceReqBean extends AbstractServiceReqBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6935440360064394929L;

    private Integer outletId;

    @Override
    public String getServiceId() {
        return "OUTLETSD";
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

}
