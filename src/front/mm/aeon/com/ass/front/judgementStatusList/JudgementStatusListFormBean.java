/**************************************************************************
 * $Date : 2018-11-23$
 * $Author : Khin Yadanar Thein $
 * $Rev : $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusList;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("judgementStatusListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class JudgementStatusListFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8509574334720669310L;

    private JudgementStatusListHeaderBean headerBean;

    private List<JudgementStatusListLineBean> lineBeanList;

    private LazyDataModel<JudgementStatusListLineBean> lazyModel;

    private boolean init = true;

    private boolean stop = false;

    private boolean isSearch;

    @Begin(nested = true)
    public String init() {

        init = false;
        headerBean = new JudgementStatusListHeaderBean();
        headerBean.setStatusList(CommonUtil.getJudgementStatusList(true));
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
        this.lazyModel = null;
        if (isSearch == true) {
            this.isSearch = false;
        }
        if (stop == true) {
            this.stop = false;
        }
        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeanList.size() != 0) {
            lazyModel = new JudgementStatusListPaginationController(this.lineBeanList.size(), this.lineBeanList);
        }
        return LinkTarget.OK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);

    }

    public JudgementStatusListHeaderBean getHeaderBean() {
        return headerBean;
    }

    public void setHeaderBean(JudgementStatusListHeaderBean headerBean) {
        this.headerBean = headerBean;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public List<JudgementStatusListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<JudgementStatusListLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public boolean getIsSearch() {
        return isSearch;
    }

    public void SetIsSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public LazyDataModel<JudgementStatusListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<JudgementStatusListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }
}
