/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.operatorSearch;

import java.sql.Timestamp;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class OperatorSearchResDto implements IResServiceDto {

    /**
     * 
     */
    private static final long serialVersionUID = 4356438275011458179L;

    private Integer userId;

    private Integer teamId;

    private Integer teamOperatorId;

    private String userName;

    private String userLoginId;

    private Integer userValidStatus;

    private String teamName;

    private Integer teamTarget;

    private String createdBy;

    private Timestamp createdTime;

    private String updatedBy;

    private Timestamp updatedTime;

    private Timestamp teamOperatorUpdateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamOperatorId() {
        return teamOperatorId;
    }

    public void setTeamOperatorId(Integer teamOperatorId) {
        this.teamOperatorId = teamOperatorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public Integer getUserValidStatus() {
        return userValidStatus;
    }

    public void setUserValidStatus(Integer userValidStatus) {
        this.userValidStatus = userValidStatus;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamTarget() {
        return teamTarget;
    }

    public void setTeamTarget(Integer teamTarget) {
        this.teamTarget = teamTarget;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Timestamp getTeamOperatorUpdateTime() {
        return teamOperatorUpdateTime;
    }

    public void setTeamOperatorUpdateTime(Timestamp teamOperatorUpdateTime) {
        this.teamOperatorUpdateTime = teamOperatorUpdateTime;
    }

}
