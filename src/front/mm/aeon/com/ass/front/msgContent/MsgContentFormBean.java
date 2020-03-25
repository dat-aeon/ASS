/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.msgContent;

import java.io.Serializable;
import java.net.InetAddress;

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
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.log.LogLevel;

@Name("msgContentFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class MsgContentFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    private static final long serialVersionUID = 8509574334720669310L;

    private boolean isInit = true;
    
    private ASSLogger logger = new ASSLogger();

    // private boolean isUpload = false;
    InetAddress address;

    @Begin(nested = true)
    public String init() {

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Message Content]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        isInit = false;
        return LinkTarget.SEARCH;
    }

    public String back() {
        isInit = true;
        return LinkTarget.BACK;
    }

    public void clearErrorMessage() {
        getMessageContainer().clearAllMessages(true);
    }

    public boolean getIsInit() {
        return isInit;
    }

    public void setIsInit(boolean init) {
        this.isInit = init;
    }
}
