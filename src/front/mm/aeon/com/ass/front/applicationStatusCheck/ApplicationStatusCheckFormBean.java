/**************************************************************************
 * $Date : 2018-08-27 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationStatusCheck;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;

@Name("appStatusCheckFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class ApplicationStatusCheckFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8509574334720669310L;

    private ApplicationStatusCheckHeaderBean appStatusCheckHeaderBean;

    private ApplicationStatusCheckLineBean lineBean;

    private List<ApplicationStatusCheckLineBean> lineBeanList;

    private List<ApplicationStatusCheckLineBean> sumlineBeanList;

    private boolean stop;

    private boolean isSearch;

    private boolean init = true;

    @Begin(nested = true)
    public String init() {

        init = false;
        appStatusCheckHeaderBean = new ApplicationStatusCheckHeaderBean();
        appStatusCheckHeaderBean.setFromDate(CommonUtil.getFirstDayOfMonth());
        appStatusCheckHeaderBean.setToDate(CommonUtil.getCurrentTimeStamp());;              
        this.isSearch = true;
        return LinkTarget.INIT;
    }

    public String searchInit() {            
        
        this.isSearch = true;
        this.stop = true;
        return LinkTarget.SEARCH;
    }

    @Action
    public String search() {

        if (isSearch == true) {
            this.isSearch = false;
        }
        if (stop == true) {
            this.stop = false;
        }
        return LinkTarget.OK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);

    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public ApplicationStatusCheckLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(ApplicationStatusCheckLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public boolean getIsSearch() {
        return isSearch;
    }

    public void SetIsSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    public ApplicationStatusCheckHeaderBean getAppStatusCheckHeaderBean() {
        return appStatusCheckHeaderBean;
    }

    public void setAppStatusCheckHeaderBean(ApplicationStatusCheckHeaderBean appStatusCheckHeaderBean) {
        this.appStatusCheckHeaderBean = appStatusCheckHeaderBean;
    }

    public List<ApplicationStatusCheckLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<ApplicationStatusCheckLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public List<ApplicationStatusCheckLineBean> getSumlineBeanList() {
        return sumlineBeanList;
    }

    public void setSumlineBeanList(List<ApplicationStatusCheckLineBean> sumlineBeanList) {
        this.sumlineBeanList = sumlineBeanList;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
