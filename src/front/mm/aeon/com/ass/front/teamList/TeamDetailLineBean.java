/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class TeamDetailLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3252676570521842517L;

    private Integer teamId;

    private String teamName;

    private String targetName;

    private Integer noOfAgent;

    private Integer noOfOperator;

    private String createdBy;

    private Timestamp createdTime;

    private String updatedBy;

    private Timestamp updatedTime;

    private List<TeamDetailAgencyLineBean> agencyLineBeanList;

    private List<TeamDetailOperatorLineBean> operatorLineBeanList;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getNoOfAgent() {
        return noOfAgent;
    }

    public void setNoOfAgent(Integer noOfAgent) {
        this.noOfAgent = noOfAgent;
    }

    public Integer getNoOfOperator() {
        return noOfOperator;
    }

    public void setNoOfOperator(Integer noOfOperator) {
        this.noOfOperator = noOfOperator;
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

    public List<TeamDetailAgencyLineBean> getAgencyLineBeanList() {
        return agencyLineBeanList;
    }

    public void setAgencyLineBeanList(List<TeamDetailAgencyLineBean> agencyLineBeanList) {
        this.agencyLineBeanList = agencyLineBeanList;
    }

    public List<TeamDetailOperatorLineBean> getOperatorLineBeanList() {
        return operatorLineBeanList;
    }

    public void setOperatorLineBeanList(List<TeamDetailOperatorLineBean> operatorLineBeanList) {
        this.operatorLineBeanList = operatorLineBeanList;
    }

}
