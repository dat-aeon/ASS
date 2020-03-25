/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.operatorManagement.OperatorManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("operatorListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class OperatorListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -532801949885579872L;

    private OperatorListHeaderBean searchHeaderBean;

    private OperatorListLineBean lineBean;

    @Out(required = false, value = "operatorUpdateParam")
    private OperatorManagementHeaderBean updateParam;

    private List<OperatorListLineBean> lineBeanList;

    private LazyDataModel<OperatorListLineBean> lazyModel;

    private ArrayList<SelectItem> statusList;

    private boolean init = true;

    private int pageFirst;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new OperatorListHeaderBean();
        this.doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        this.doReload = new Boolean(false);
        this.updateParam = null;
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeanList.size() != 0) {
            lazyModel = new OperatorListPaginationController(this.lineBeanList.size(), this.lineBeanList);
        }

        return LinkTarget.OK;
    }

    public String detail(OperatorListLineBean lineBean) {
        this.getMessageContainer().clearAllMessages(true);
        this.lineBean = lineBean;
        return LinkTarget.DETAIL;
    }

    @Action
    public String toggleValidStatus(OperatorListLineBean lineBean) {
        return LinkTarget.OK;
    }

    public String prepareRegister() {
        this.updateParam = null;
        return LinkTarget.REGISTER;
    }

    @Action
    public String delete() {
        this.doReload = false;
        this.lineBean = null;
        if (!getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.doReload = true;
        }

        return LinkTarget.OK;
    }

    public String back() {
        return LinkTarget.BACK;
    }

    public String prepareUpdate(OperatorListLineBean lineBean) {
        this.updateParam = new OperatorManagementHeaderBean();

        this.updateParam.setCreatedBy(lineBean.getCreatedBy());
        this.updateParam.setCreatedTime(lineBean.getCreatedTime());
        this.updateParam.setOldTeamId(lineBean.getTeamId());
        this.updateParam.setTeamId(lineBean.getTeamId());
        this.updateParam.setTeamName(lineBean.getTeamName());
        this.updateParam.setTeamOperatorId(lineBean.getTeamOperatorId());
        this.updateParam.setUpdatedBy(lineBean.getUpdatedBy());
        this.updateParam.setUpdatedTime(lineBean.getUpdatedTime());
        this.updateParam.setUserId(lineBean.getUserId());
        this.updateParam.setUserLoginId(lineBean.getUserLoginId());
        this.updateParam.setUserName(lineBean.getUserName());
        this.updateParam.setUserValidStatus(lineBean.getUserValidStatus());
        this.updateParam.setTeamOperatorUpdateTime(lineBean.getTeamOperatorUpdateTime());
        this.updateParam.setForUpdate(true);

        return LinkTarget.UPDATE_INIT;
    }

    public void reset() {
        searchHeaderBean = new OperatorListHeaderBean();
    }

    public OperatorListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(OperatorListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public OperatorListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(OperatorListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public OperatorManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(OperatorManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public List<OperatorListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<OperatorListLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public LazyDataModel<OperatorListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<OperatorListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public ArrayList<SelectItem> getStatusList() {

        statusList = new ArrayList<SelectItem>();

        SelectItem item = new SelectItem();
        item.setLabel(ASSCommon.SPACE);
        item.setValue(null);
        statusList.add(item);

        item = new SelectItem();
        item.setLabel(ASSCommon.DISABLED);
        item.setValue(ASSCommon.ZERO);
        statusList.add(item);

        item = new SelectItem();
        item.setLabel(ASSCommon.ENABLED);
        item.setValue(ASSCommon.ONE);
        statusList.add(item);

        return statusList;
    }

    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

}
