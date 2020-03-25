/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorUpdate;

import mm.com.dat.presto.main.common.dao.bean.IResDto;

public class OperatorUpdateResDto implements IResDto {

    /**
     * 
     */
    private static final long serialVersionUID = -4231278866212551428L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
