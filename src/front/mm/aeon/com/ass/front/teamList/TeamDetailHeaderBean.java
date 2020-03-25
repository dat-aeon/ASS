/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import java.io.Serializable;
import java.util.Stack;

public class TeamDetailHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 138657209492912328L;

    private Integer teamId;

    private Stack<String> priorLinkStack;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Stack<String> getPriorLinkStack() {
        return priorLinkStack;
    }

    public void setPriorLinkStack(Stack<String> priorLinkStack) {
        this.priorLinkStack = priorLinkStack;
    }

}
