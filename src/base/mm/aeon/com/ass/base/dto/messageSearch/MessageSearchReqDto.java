/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.messageSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqServiceDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "Message")
public class MessageSearchReqDto implements IReqServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1429431085803841290L;

    private String agencyUserName;

    private Timestamp fromDateTime;

    private Timestamp toDateTime;

    public String getAgencyUserName() {
        return agencyUserName;
    }

    public void setAgencyUserName(String agencyUserName) {
        this.agencyUserName = agencyUserName;
    }

    public Timestamp getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Timestamp fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Timestamp getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Timestamp toDateTime) {
        this.toDateTime = toDateTime;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.SELECT_LIST;
    }

}
