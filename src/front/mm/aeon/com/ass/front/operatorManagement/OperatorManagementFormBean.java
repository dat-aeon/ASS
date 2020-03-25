/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorManagement;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.authenticate.LoginInfo;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("operatorManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class OperatorManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1583457870206052674L;

    @In(create = true)
    private LoginInfo loginInfo;

    private boolean init = true;

    private ArrayList<SelectItem> teamSelectItemList;

    @In(required = false, value = "operatorUpdateParam")
    @Out(required = false, value = "operatorUpdateParam")
    private OperatorManagementHeaderBean managementHeaderBean;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(join = true)
    @Action
    public String init() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.BACK;
        }
        init = false;
        this.getMessageContainer().clearAllMessages(true);
        if (null == this.managementHeaderBean) {
            this.managementHeaderBean = new OperatorManagementHeaderBean();
        }

        this.doReload = new Boolean(false);
        return LinkTarget.INIT;
    }

    @Action
    public String register() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.managementHeaderBean = new OperatorManagementHeaderBean();
        this.doReload = new Boolean(true);
        return LinkTarget.OK;
    }

    @Action
    public String update() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.init = true;
        this.managementHeaderBean = null;
        this.doReload = new Boolean(true);

        return LinkTarget.SEARCH;
    }

    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        this.init = true;
        this.managementHeaderBean = null;

        return LinkTarget.BACK;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public ArrayList<SelectItem> getTeamSelectItemList() {
        return teamSelectItemList;
    }

    public void setTeamSelectItemList(ArrayList<SelectItem> teamSelectItemList) {
        this.teamSelectItemList = teamSelectItemList;
    }

    public OperatorManagementHeaderBean getManagementHeaderBean() {
        return managementHeaderBean;
    }

    public void setManagementHeaderBean(OperatorManagementHeaderBean managementHeaderBean) {
        this.managementHeaderBean = managementHeaderBean;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

}
