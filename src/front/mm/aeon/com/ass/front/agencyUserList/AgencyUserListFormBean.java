/**************************************************************************
 * $Date: 2018-08-06$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserList;

import java.io.Serializable;
import java.util.Stack;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.agencyUserManagement.AgencyUserRegisterHeaderBean;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("agencyUserListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class AgencyUserListFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8509574334720669310L;

    private AgencyUserListHeaderBean agencyUserListHeaderBean;

    private AgencyUserLineBean lineBean;

    private LazyDataModel<AgencyUserLineBean> lazyModel;

    @In(required = false, value = "agencyUserUpdateParam")
    @Out(required = false, value = "agencyUserUpdateParam")
    private AgencyUserRegisterHeaderBean updateParam;

    @In(required = false, value = "detailParam")
    @Out(required = false, value = "detailParam")
    private AgencyUserRegisterHeaderBean detailParam;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private boolean init = true;

    private boolean initError = true;

    private boolean isError = false;

    private boolean isUpdate = false;

    private int pageFirst;

    @Begin(nested = true)
    public String init() {

        init = false;
        this.getMessageContainer().clearAllMessages(true);
        this.doReload = new Boolean(true);
        this.agencyUserListHeaderBean = new AgencyUserListHeaderBean();
        return LinkTarget.INIT;
    }

    @Action
    public String listingAgencyUser() {
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {

            // If search result count is 0
            if (agencyUserListHeaderBean.getCount() == 0) {
                lazyModel = null;

            } else {
                lazyModel = new AgencyUserPaginationController(agencyUserListHeaderBean);
            }
        } else {
            lazyModel = null;
        }

        updateParam = null;
        detailParam = null;
        this.isUpdate = false;
        this.doReload = false;
        return LinkTarget.OK;
    }

    public String createNewUser() {
        return LinkTarget.REGISTER;
    }

    public String updateUser(AgencyUserLineBean lineBean) {
        updateParam = new AgencyUserRegisterHeaderBean();
        this.updateParam.setId(lineBean.getId());
        this.updateParam.setLoginID(lineBean.getLoginID());

        return LinkTarget.UPDATE_INIT;
    }

    @Action
    public String toggleValid() {
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            lineBean = new AgencyUserLineBean();
            doReload = true;
            isUpdate = true;
        }
        return LinkTarget.OK;
    }

    @Action
    public String delete() {
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            lineBean = new AgencyUserLineBean();
            doReload = true;
            isUpdate = true;
        }
        return LinkTarget.OK;
    }

    public String showDetail(AgencyUserLineBean lineBean) {
        detailParam = new AgencyUserRegisterHeaderBean();
        this.detailParam.setId(lineBean.getId());
        this.detailParam.setLoginID(lineBean.getLoginID());
        this.detailParam.setPriorLinkStack(new Stack<String>());
        this.detailParam.getPriorLinkStack().push(LinkTarget.BACK);

        return LinkTarget.DETAIL;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
        isError = false;
    }

    public AgencyUserListHeaderBean getAgencyUserListHeaderBean() {
        return agencyUserListHeaderBean;
    }

    public void setAgencyUserListHeaderBean(AgencyUserListHeaderBean agencyUserListHeaderBean) {
        this.agencyUserListHeaderBean = agencyUserListHeaderBean;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public AgencyUserLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(AgencyUserLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public LazyDataModel<AgencyUserLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<AgencyUserLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public boolean getIsInitError() {
        return initError;
    }

    public void setIsInitError(boolean initError) {
        this.initError = initError;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public AgencyUserRegisterHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(AgencyUserRegisterHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public AgencyUserRegisterHeaderBean getDetailParam() {
        return detailParam;
    }

    public void setDetailParam(AgencyUserRegisterHeaderBean detailParam) {
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
