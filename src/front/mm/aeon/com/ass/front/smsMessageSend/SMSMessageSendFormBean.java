/**************************************************************************
 * $Date: 2018-11-16$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.smsMessageSend;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.LogLevel;

@Name("smsMessageSendFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class SMSMessageSendFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    private static final long serialVersionUID = 8509574334720669310L;

    private SMSMessageSendHeaderBean headerBean;

    private boolean isInit = true;

    private boolean isUpload = false;

    private String dateTitle;

    private ASSLogger logger = new ASSLogger();

    @Begin(nested = true)
    public String init() {

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Receiving File List]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        isInit = false;
        headerBean = new SMSMessageSendHeaderBean();
        this.clearErrorMessage();
        return LinkTarget.INIT;
    }

    @Action
    public String send() {

        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            isUpload = false;
            return LinkTarget.ERROR;
        }
        headerBean = new SMSMessageSendHeaderBean();
        return LinkTarget.OK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
    }

    public SMSMessageSendHeaderBean getHeaderBean() {
        return headerBean;
    }

    public void setHeaderBean(SMSMessageSendHeaderBean headerBean) {
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

    public String getDateTitle() {
        return dateTitle;
    }

    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }

}
