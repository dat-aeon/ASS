/***********************************************************************
 * $Date: 2018-08-15 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyListDetail;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class AgencyListDetailUserResDto implements IResServiceDto{

    /**
     * 
     */
    private static final long serialVersionUID = 7737136798473893947L;
    
    private String userName;
    
    private int userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
   
}

