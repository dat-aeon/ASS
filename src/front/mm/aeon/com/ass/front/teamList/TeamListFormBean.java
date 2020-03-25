/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.teamManagement.TeamManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("teamListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class TeamListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 7335660742615197006L;

    private boolean init = true;

    private TeamListHeaderBean searchHeaderBean;

    private TeamListLineBean lineBean;

    private TeamDetailLineBean detailLineBean;

    private List<TeamListLineBean> lineBeanList;

    private LazyDataModel<TeamListLineBean> lazyModel;
    
    private int pageFirst;

    @Out(required = false, value = "teamUpdateParam")
    private TeamManagementHeaderBean updateHeaderBean;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @In(required = false, value = "teamDetailParam")
    @Out(required = false, value = "teamDetailParam")
    private TeamDetailHeaderBean detailHeaderBean;

    private Map<Integer, String> targetMap;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        this.searchHeaderBean = new TeamListHeaderBean();
        this.targetMap = CommonUtil.getTargetMap();

        this.doReload = new Boolean(true);
        init = false;
        doReload = true;
    }

    @Action
    public String search() {
        this.doReload = new Boolean(false);
        //this.detailHeaderBean = new TeamDetailHeaderBean();
        this.updateHeaderBean = null;
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeanList.size() != 0) {
            lazyModel = new TeamListPaginationController(this.lineBeanList.size(), this.lineBeanList);
        }

        return LinkTarget.OK;
    }

    public String prepareRegister() {
        this.updateHeaderBean = null;
        return LinkTarget.REGISTER;
    }

    public String prepareUpdate(TeamListLineBean lineBean) {

        this.updateHeaderBean = new TeamManagementHeaderBean();

        this.updateHeaderBean.setTeamId(lineBean.getTeamId());
        this.updateHeaderBean.setTarget(lineBean.getTarget());
        this.updateHeaderBean.setTeamName(lineBean.getTeamName());
        this.updateHeaderBean.setUpdatedTime(lineBean.getUpdatedTime());
        this.updateHeaderBean.setForUpdate(true);

        return LinkTarget.UPDATE;
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
    
    public String prepareDetail(Integer teamId) {
        this.detailHeaderBean = new TeamDetailHeaderBean();
        this.detailHeaderBean.setTeamId(teamId);
        this.detailHeaderBean.setPriorLinkStack(new Stack<String>());
        this.detailHeaderBean.getPriorLinkStack().push(LinkTarget.SEARCH);
        
        return LinkTarget.DETAIL;
    }

    @Action
    public String detail() {
        if(this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.setDoReload(true);
        }        
        return LinkTarget.DETAIL;
    }
    
    public String detailBack() {
        this.detailLineBean = null;
        
        return this.detailHeaderBean.getPriorLinkStack().pop();
    }

    public String back() {
        return LinkTarget.BACK;
    }

    public boolean graterThanZero(Integer val) {
        return (null != val && val > 0) ? true : false;
    }

    public boolean deletable(Integer a, Integer b) {
        return !(graterThanZero(a) || graterThanZero(b));
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isDoReload() {
        return doReload;
    }

    public void setDoReload(boolean doReload) {
        this.doReload = doReload;
    }

    public TeamListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(TeamListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public TeamListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(TeamListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public TeamDetailLineBean getDetailLineBean() {
        return detailLineBean;
    }

    public void setDetailLineBean(TeamDetailLineBean detailLineBean) {
        this.detailLineBean = detailLineBean;
    }

    public TeamManagementHeaderBean getUpdateHeaderBean() {
        return updateHeaderBean;
    }

    public void setUpdateHeaderBean(TeamManagementHeaderBean updateHeaderBean) {
        this.updateHeaderBean = updateHeaderBean;
    }

    public List<TeamListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<TeamListLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public LazyDataModel<TeamListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<TeamListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public TeamDetailHeaderBean getDetailHeaderBean() {
        return detailHeaderBean;
    }

    public void setDetailHeaderBean(TeamDetailHeaderBean detailHeaderBean) {
        this.detailHeaderBean = detailHeaderBean;
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

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

}
