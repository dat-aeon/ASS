/***********************************************************************
 * $Date: 2018-08-14 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListDetail;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyListDetailOutletResDto implements IResServiceDto{

    /**
     * 
     */
    private static final long serialVersionUID = 7737136798473893947L;
    
    private String outletName;
    
    private int outletId;

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }
        
}

