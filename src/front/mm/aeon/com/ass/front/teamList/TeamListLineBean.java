/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import java.io.Serializable;
import java.sql.Timestamp;

public class TeamListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3472693436618391461L;

    private Integer teamId;

    private String teamName;

    private Integer target;

    private Integer noOfAgent;

    private Integer noOfOperator;

    private String createdBy;

    private Timestamp createdTime;

    private String updatedBy;

    private Timestamp updatedTime;

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

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
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

}
