/**************************************************************************
 * $Date: 2018-08-06$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.initializePassword;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("initializePasswordFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class InitializePasswordFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 8509574334720669310L;

    private InitializePasswordHeaderBean headerBean;

    private boolean isInit = true;

    @Begin(nested = true)
    public String init() {
        isInit = false;
        headerBean = new InitializePasswordHeaderBean();
        this.clearErrorMessage();
        return LinkTarget.INIT;
    }

    @Action
    public String updatePassword() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        headerBean = new InitializePasswordHeaderBean();
        return LinkTarget.OK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
    }

    public InitializePasswordHeaderBean getHeaderBean() {
        return headerBean;
    }

    public void setHeaderBean(InitializePasswordHeaderBean headerBean) {
        this.headerBean = headerBean;
    }

    public boolean getIsInit() {
        return isInit;
    }

    public void setIsInit(boolean init) {
        this.isInit = init;
    }
}
