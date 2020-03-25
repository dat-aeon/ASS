/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamManagement;

import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.LogLevel;

@Name("teamManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class TeamManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -7946336691514585943L;

    private boolean init = true;

    private Map<Integer, String> targetMap;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @In(required = false, value = "teamUpdateParam")
    @Out(required = false, value = "teamUpdateParam")
    private TeamManagementHeaderBean managementHeaderBean;
    
    private ASSLogger logger = new ASSLogger();

    @Begin(join = true)
    public String init() {

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Team Management]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        init = false;
        this.getMessageContainer().clearAllMessages(true);
        if (null == this.managementHeaderBean) {
            this.managementHeaderBean = new TeamManagementHeaderBean();
        }
        this.targetMap = CommonUtil.getTargetMap();

        return LinkTarget.INIT;
    }

    @Action
    public String register() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.REGISTER;
        }
        this.doReload = new Boolean(true);
        this.managementHeaderBean = new TeamManagementHeaderBean();
        return LinkTarget.OK;
    }

    @Action
    public String update() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.UPDATE;
        }
        this.init = true;
        this.doReload = new Boolean(true);
        this.managementHeaderBean = null;

        return LinkTarget.SEARCH;
    }

    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        this.init = true;
        this.managementHeaderBean = null;

        return LinkTarget.BACK;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public TeamManagementHeaderBean getManagementHeaderBean() {
        return managementHeaderBean;
    }

    public void setManagementHeaderBean(TeamManagementHeaderBean managementHeaderBean) {
        this.managementHeaderBean = managementHeaderBean;
    }

    public Map<Integer, String> getTargetMap() {
        return targetMap;
    }

    public void setTargetMap(Map<Integer, String> targetMap) {
        this.targetMap = targetMap;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

}
