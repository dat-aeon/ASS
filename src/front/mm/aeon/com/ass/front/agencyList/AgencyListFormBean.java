/**************************************************************************
 * $Date: 2018-07-30$
 * $Author: Thar Pyae $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyList;

import java.util.List;
import java.util.Stack;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.agencyListManagement.AgencyListManagementHeaderBean;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.core.authenticate.LoginInfo;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

/**
 * 
 * AgencyListFormBean Class.
 * <p>
 * 
 * <pre>
 *      AgencyListFormBean Class.
 * </pre>
 * 
 * </p>
 */
@Name("agencyListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class AgencyListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 3584695935700863500L;

    private AgencyListHeaderBean agencyLisHeaderBean;

    @In(required = false, value = "agencyUpdateParam")
    @Out(required = false, value = "agencyUpdateParam")
    private AgencyListManagementHeaderBean updateParam;

    @In(required = false, value = "agencyDetailParam")
    @Out(required = false, value = "agencyDetailParam")
    private AgencyListManagementHeaderBean detailParam;

    private List<AgencyListSearchLineBean> lineBeanList;

    private AgencyListSearchLineBean lineBean;

    private LazyDataModel<AgencyListSearchLineBean> lazyModel;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private LoginInfo loginInfo;

    private int pageFirst;

    @Begin(nested = true)
    @Action
    public String init() {
        init = false;
        lazyModel = null;
        this.getMessageContainer().clearAllMessages(true);
        this.doReload = true;

        return LinkTarget.INIT;
    }

    @Action
    public String search() {

        this.doReload = new Boolean(false);
        this.updateParam = null;
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeanList.size() != 0) {
            lazyModel = new AgencyListPaginationController(lineBeanList.size(), lineBeanList);
        }
        return LinkTarget.SEARCH;

    }

    @Action
    public String changeValid() {
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            lineBean = new AgencyListSearchLineBean();
            this.doReload = true;
        }
        return LinkTarget.OK;
    }

    @Action
    public String delete() {
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            lineBean = new AgencyListSearchLineBean();
            this.doReload = true;
        }
        return LinkTarget.OK;
    }

    public String createNewAgency() {

        return LinkTarget.REGISTER;
    }

    public String updateAgency(AgencyListSearchLineBean lineBean) {

        updateParam = new AgencyListManagementHeaderBean();
        this.updateParam.setAgencyId(lineBean.getAgencyId());
        this.updateParam.setAgencyName(lineBean.getAgencyName());
        this.updateParam.setLocation(CommonUtil.changeLocationValue(lineBean.getLocation()));
        this.updateParam.setAddress(lineBean.getAddress());
        this.updateParam.setIsAeon(lineBean.getIsAeon());
        this.updateParam.setMobileTeam(lineBean.getMobileTeamId());
        this.updateParam.setOldMobileId(lineBean.getMobileTeamId());
        this.updateParam.setNonMobileTeam(lineBean.getNonMobileTeamId());
        this.updateParam.setOldNonMobileId(lineBean.getNonMobileTeamId());
        this.updateParam.setAgencyUptDate(lineBean.getAgencyUptDate());
        this.updateParam.setRemark(lineBean.getRemark());

        this.doReload = false;
        return LinkTarget.UPDATE_INIT;
    }

    public String showDetail(AgencyListSearchLineBean lineBean) {
        detailParam = new AgencyListManagementHeaderBean();
        this.detailParam.setAgencyId(lineBean.getAgencyId());
        this.detailParam.setPriorLinkStack(new Stack<String>());
        this.detailParam.getPriorLinkStack().push(LinkTarget.SEARCH);

        this.doReload = true;
        return LinkTarget.DETAIL;
    }

    public AgencyListHeaderBean getAgencyLisHeaderBean() {
        return agencyLisHeaderBean;
    }

    public void setAgencyLisHeaderBean(AgencyListHeaderBean agencyLisHeaderBean) {
        this.agencyLisHeaderBean = agencyLisHeaderBean;
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

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public LazyDataModel<AgencyListSearchLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<AgencyListSearchLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public AgencyListSearchLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(AgencyListSearchLineBean lineBean) {
        this.lineBean = lineBean;
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
