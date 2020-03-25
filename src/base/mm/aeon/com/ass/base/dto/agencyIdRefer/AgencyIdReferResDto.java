/***********************************************************************
 * $Date: 2018-08-08 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyIdRefer;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyIdReferResDto implements IResServiceDto{

    /**
     * 
     */
    private static final long serialVersionUID = 7737136798473893947L;
    
    private int agencyId;

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }
      
}

