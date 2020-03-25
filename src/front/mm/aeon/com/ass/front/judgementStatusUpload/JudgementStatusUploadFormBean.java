/**************************************************************************
 * $Date: 2018-11-16$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusUpload;

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
import mm.com.dat.presto.main.front.message.MessageType;

@Name("judgementStatusUploadFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class JudgementStatusUploadFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    private static final long serialVersionUID = 8509574334720669310L;

    private JudgementStatusUploadHeaderBean headerBean;

    private List<JudgementStatusUploadSuccessListBean> lineBeanList;

    private boolean isInit = true;

    private boolean isUpload = false;

    private String dateTitle;

    @Begin(nested = true)
    public String init() {
        isInit = false;
        headerBean = new JudgementStatusUploadHeaderBean();
        headerBean.setStatusList(CommonUtil.getJudgementStatusList(false));
        this.clearErrorMessage();
        return LinkTarget.INIT;
    }

    @Action
    public String upload() {

        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            isUpload = false;
            return LinkTarget.ERROR;
        }

        return LinkTarget.OK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
    }

    public JudgementStatusUploadHeaderBean getHeaderBean() {
        return headerBean;
    }

    public void setHeaderBean(JudgementStatusUploadHeaderBean headerBean) {
        this.headerBean = headerBean;
    }

    public boolean getIsInit() {
        return isInit;
    }

    public void setIsInit(boolean init) {
        this.isInit = init;
    }

    public boolean getIsUpload() {
        return isUpload;
    }

    public void setUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public List<JudgementStatusUploadSuccessListBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<JudgementStatusUploadSuccessListBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public String getDateTitle() {
        return dateTitle;
    }

    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }

}
