/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletRefer;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OutletReferResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = -1811837470425558926L;

    private Integer outletId;

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

}
