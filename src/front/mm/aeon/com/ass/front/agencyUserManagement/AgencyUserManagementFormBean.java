/**************************************************************************
 * $Date: 2018-08-06$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserManagement;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.event.SelectEvent;

import mm.aeon.com.ass.base.dto.agencyOutletSelectList.AgencyOutletSelectListResDto;
import mm.aeon.com.ass.front.agencyListManagement.AgencyListManagementHeaderBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("agencyUserManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class AgencyUserManagementFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8509574334720669310L;

    private AgencyUserRegisterHeaderBean registerHeaderBean;

    @In(required = false, value = "agencyUserUpdateParam")
    @Out(required = false, value = "agencyUserUpdateParam")
    private AgencyUserRegisterHeaderBean updateParam;

    @In(required = false, value = "detailParam")
    @Out(required = false, value = "detailParam")
    private AgencyUserRegisterHeaderBean detailParam;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @In(required = false, value = "agencyDetailParam")
    @Out(required = false, value = "agencyDetailParam")
    private AgencyListManagementHeaderBean agencyDetailParam;

    private boolean isInit = true;

    private boolean initError = false;

    private boolean isError = false;

    private boolean isRegister = false;

    @Begin(join = true)
    @Action
    public String init() {

        isInit = false;
        doReload = new Boolean(false);
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        registerHeaderBean.setIsRegisterPage(true);
        return LinkTarget.INIT;
    }

    @Begin(join = true)
    @Action
    public String updateInit() {
        isInit = false;
        doReload = new Boolean(false);
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        return LinkTarget.INIT;
    }

    @Action
    public String registerUser() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            isError = true;
            return LinkTarget.ERROR;
        }
        if (updateParam != null) {
            isInit = true;
            this.doReload = true;
            this.updateParam.setIsUpdate(true);
            return LinkTarget.SEARCH;

        } else {
            isRegister = true;
            this.doReload = true;
        }
        return LinkTarget.OK;
    }

    @Begin(join = true)
    @Action
    public String showDetail() {
        isInit = false;
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;

        }
        return LinkTarget.OK;
    }

    public String cancel() {

        this.getMessageContainer().clearAllMessages(true);
        if (isRegister) {
            this.doReload = true;
        }
        updateParam = null;
        isInit = true;
        return LinkTarget.BACK;
    }

    public String agencyDetail(int agencyId) {
        this.agencyDetailParam = new AgencyListManagementHeaderBean();
        this.agencyDetailParam.setAgencyId(agencyId);
        this.detailParam.getPriorLinkStack().push(LinkTarget.DETAIL_AGENCY_USER);
        this.agencyDetailParam.setPriorLinkStack(this.detailParam.getPriorLinkStack());

        return LinkTarget.DETAIL_AGENCY;
    }

    public String back() {
        String returnStr = LinkTarget.BACK;

        if (null != detailParam) {
            returnStr = detailParam.getPriorLinkStack().pop();
        }

        isInit = true;
        detailParam = null;
        return returnStr;
    }

    public void onChangeDate(SelectEvent event) {
        this.getRegisterHeaderBean().setEndMinDate(this.getRegisterHeaderBean().getStartDate());
    }

    public void setOutletData() {
        String agencyId = this.getRegisterHeaderBean().getAgencyName();

        ArrayList<AgencyOutletSelectListResDto> outletList = registerHeaderBean.getAllOutletNameList().get(agencyId);

        ArrayList<SelectItem> outletNameList = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem();
        item.setLabel(ASSCommon.SPACE);
        item.setValue(ASSCommon.BLANK);
        outletNameList.add(item);

        for (AgencyOutletSelectListResDto resDto : outletList) {
            item = new SelectItem();
            if(resDto.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                item.setLabel(ASSCommon.OUT_HEAD_OFFICE);
            } else {
                item.setLabel(resDto.getOutletName());
            }
            item.setValue(resDto.getOutletId());

            outletNameList.add(item);
        }
        this.registerHeaderBean.setOutletNameList(outletNameList);
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
        isError = false;
    }

    public AgencyUserRegisterHeaderBean getRegisterHeaderBean() {
        return registerHeaderBean;
    }

    public void setRegisterHeaderBean(AgencyUserRegisterHeaderBean registerHeaderBean) {
        this.registerHeaderBean = registerHeaderBean;
    }

    public boolean getIsInit() {
        return isInit;
    }

    public void setIsInit(boolean init) {
        this.isInit = init;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean getIsInitError() {
        return initError;
    }

    public void setIsInitError(boolean initError) {
        this.initError = initError;
    }

    public boolean getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
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

    public AgencyListManagementHeaderBean getAgencyDetailParam() {
        return agencyDetailParam;
    }

    public void setAgencyDetailParam(AgencyListManagementHeaderBean agencyDetailParam) {
        this.agencyDetailParam = agencyDetailParam;
    }

}
