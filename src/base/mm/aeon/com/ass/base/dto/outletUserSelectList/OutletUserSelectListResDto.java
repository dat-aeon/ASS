/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.outletUserSelectList;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OutletUserSelectListResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 4479294076743617689L;

    private Integer userId;

    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
