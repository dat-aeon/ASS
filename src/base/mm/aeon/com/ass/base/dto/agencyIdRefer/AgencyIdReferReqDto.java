/***********************************************************************
 * $Date: 2018-08-09 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.base.dto.agencyIdRefer;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

/**
 * 
 * AgencyIdReferReqDto Class.
 * <p>
 * 
 * <pre>
 *      AgencyIdReferReqDto Class.
 * </pre>
 * 
 * </p>
 */
@MyBatisMapper(namespace = "AgencyInfo")
public class AgencyIdReferReqDto implements IReqDto {
    /**
     * 
     */
    private static final long serialVersionUID = 7930856697424849917L;

    private int id;
    
    private String agencyName;
    
    private String insertReferFlag;
    
    private int isValid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getinsertReferFlag() {
        return insertReferFlag;
    }

    public void setinsertReferFlag(String insertReferFlag) {
        this.insertReferFlag = insertReferFlag;
    }

    @Override
    public DaoType getDaoType() {

        return DaoType.REFER;
    }
}
