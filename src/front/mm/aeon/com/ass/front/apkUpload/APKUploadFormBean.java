/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.apkUpload;

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

@Name("apkUploadFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class APKUploadFormBean extends AbstractFormBean implements Serializable, IRequest, IResponse {

    private static final long serialVersionUID = 8509574334720669310L;

    private APKUploadHeaderBean headerBean;

    private boolean isInit = true;

    private boolean isUpload = false;
    
    private ASSLogger logger = new ASSLogger(); 

    @Begin(nested = true)
    public String init() {

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[APK upload]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        isInit = false;
        headerBean = new APKUploadHeaderBean();
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

    public APKUploadHeaderBean getHeaderBean() {
        return headerBean;
    }

    public void setHeaderBean(APKUploadHeaderBean headerBean) {
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

}
