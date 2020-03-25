/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.adminList;

import mm.aeon.com.ass.base.service.adminDeleteService.AdminDeleteServiceReqBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AdminDeleteController extends AbstractController
        implements IControllerAccessor<AdminListFormBean, AdminListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();
    
    @Override
    public AdminListFormBean process(AdminListFormBean formBean) {
        
        formBean.getMessageContainer().clearAllMessages(true);
        
        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Admin List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        applicationLogger.log("Admin deleting Process started.", LogLevel.INFO);
        MessageBean msgBean;
        
        AdminDeleteServiceReqBean serviceReqBean = new AdminDeleteServiceReqBean();
        serviceReqBean.setId(formBean.getLineBean().getAdminId());
        
        this.getServiceInvoker().addRequest(serviceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        
        if(ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("Admin deleting Process finished.", LogLevel.INFO);
        } else if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("Admin deleting Process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }
        
        return formBean;
    }

}
