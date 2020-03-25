/**************************************************************************
 * $Date: 2018-07-30$
 * $Author: Thar Pyae $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyListManagement;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.agencyUserManagement.AgencyUserRegisterHeaderBean;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.outletList.OutletDetailHeaderBean;
import mm.aeon.com.ass.front.teamList.TeamDetailHeaderBean;
import mm.com.dat.presto.main.core.authenticate.LoginInfo;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

/**
 * 
 * AgencyListManagementFormBean Class.
 * <p>
 * 
 * <pre>
 *      AgencyListManagementFormBean Class.
 * </pre>
 * 
 * </p>
 */
@Name("agencyListManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class AgencyListManagementFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */

    private static final long serialVersionUID = 3584695935700863500L;

    private AgencyListManagementHeaderBean agencyListManagementHeaderBean;

    @In(required = false, value = "agencyUpdateParam")
    @Out(required = false, value = "agencyUpdateParam")
    private AgencyListManagementHeaderBean updateParam;

    @In(required = false, value = "agencyDetailParam")
    @Out(required = false, value = "agencyDetailParam")
    private AgencyListManagementHeaderBean detailParam;

    @In(required = false, value = "outletDetailParam")
    @Out(required = false, value = "outletDetailParam")
    private OutletDetailHeaderBean outletDetail;

    @In(required = false, value = "teamDetailParam")
    @Out(required = false, value = "teamDetailParam")
    private TeamDetailHeaderBean teamDetail;

    @In(required = false, value = "detailParam")
    @Out(required = false, value = "detailParam")
    private AgencyUserRegisterHeaderBean userDetail;

    private List<AgencyListSearchLineBean> lineBeanList;

    private List<AgencyOutletDetailLineBean> outletNameLineBeanList;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private LoginInfo loginInfo;

    @Begin(nested = true)
    @Action
    public String init() {
        init = false;
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.getMessageContainer().clearAllMessages(true);
        this.doReload = false;

        return LinkTarget.INIT;
    }

    @Action
    public String register() {
        if (getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }

        this.doReload = true;
        return LinkTarget.OK;
    }

    @Action
    public String update() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        this.doReload = true;
        this.init = true;
        return LinkTarget.OK;
    }

    @Action
    public String detailInit() {

        this.getMessageContainer().clearAllMessages(true);
        this.doReload = false;

        return LinkTarget.DETAIL;

    }

    public String outletDetail(Integer outletId) {
        this.outletDetail = new OutletDetailHeaderBean();
        this.outletDetail.setOutletId(outletId);
        this.detailParam.getPriorLinkStack().push(LinkTarget.DETAIL_AGENCY);
        this.outletDetail.setPriorLinkStack(this.getDetailParam().getPriorLinkStack());

        return LinkTarget.DETAIL_OUTLET;
    }

    public String userDetail(Integer userId) {
        this.userDetail = new AgencyUserRegisterHeaderBean();
        this.userDetail.setId(userId);
        this.detailParam.getPriorLinkStack().push(LinkTarget.DETAIL_AGENCY);
        this.userDetail.setPriorLinkStack(this.getDetailParam().getPriorLinkStack());

        return LinkTarget.DETAIL_AGENCY_USER;
    }

    public String teamDetail(String teamId) {
        this.teamDetail = new TeamDetailHeaderBean();
        this.teamDetail.setTeamId(Integer.valueOf(teamId));
        this.detailParam.getPriorLinkStack().push(LinkTarget.DETAIL_AGENCY);
        this.teamDetail.setPriorLinkStack(this.detailParam.getPriorLinkStack());

        return LinkTarget.DETAIL_TEAM;
    }

    public String back() {
        String returnStr = LinkTarget.SEARCH;

        if (null != this.detailParam) {
            returnStr = this.detailParam.getPriorLinkStack().pop();
        }

        this.init = true;
        this.detailParam = null;
        this.agencyListManagementHeaderBean = null;
        this.getMessageContainer().clearAllMessages(true);

        return returnStr;

    }

    public AgencyListManagementHeaderBean getAgencyListManagementHeaderBean() {
        return agencyListManagementHeaderBean;
    }

    public void setAgencyListManagementHeaderBean(AgencyListManagementHeaderBean agencyListManagementHeaderBean) {
        this.agencyListManagementHeaderBean = agencyListManagementHeaderBean;
    }

    public List<AgencyListSearchLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<AgencyListSearchLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public List<AgencyOutletDetailLineBean> getOutletNameLineBeanList() {
        return outletNameLineBeanList;
    }

    public void setOutletNameLineBeanList(List<AgencyOutletDetailLineBean> outletNameLineBeanList) {
        this.outletNameLineBeanList = outletNameLineBeanList;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public AgencyListManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(AgencyListManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public AgencyListManagementHeaderBean getDetailParam() {
        return detailParam;
    }

    public void setDetailParam(AgencyListManagementHeaderBean detailParam) {
        this.detailParam = detailParam;
    }

    public OutletDetailHeaderBean getOutletDetail() {
        return outletDetail;
    }

    public void setOutletDetail(OutletDetailHeaderBean outletDetail) {
        this.outletDetail = outletDetail;
    }

    public AgencyUserRegisterHeaderBean getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(AgencyUserRegisterHeaderBean userDetail) {
        this.userDetail = userDetail;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public TeamDetailHeaderBean getTeamDetail() {
        return teamDetail;
    }

    public void setTeamDetail(TeamDetailHeaderBean teamDetail) {
        this.teamDetail = teamDetail;
    }

}
