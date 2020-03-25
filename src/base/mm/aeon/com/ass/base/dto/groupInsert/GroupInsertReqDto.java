/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.groupInsert;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.DaoType;
import mm.com.dat.presto.main.common.dao.bean.IReqDto;
import mm.com.dat.presto.main.core.dao.controller.MyBatisMapper;

@MyBatisMapper(namespace = "GroupInfo")
public class GroupInsertReqDto implements IReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = -5065244090893838989L;

    private Integer agencyUserId;

    private Integer finishFlag;
    
    private Timestamp startTime;

    private Timestamp lastSendTime;

    public Integer getAgencyUserId() {
        return agencyUserId;
    }

    public void setAgencyUserId(Integer agencyUserId) {
        this.agencyUserId = agencyUserId;
    }

    public Integer getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(Integer finishFlag) {
        this.finishFlag = finishFlag;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Timestamp lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.INSERT;
    }

}
