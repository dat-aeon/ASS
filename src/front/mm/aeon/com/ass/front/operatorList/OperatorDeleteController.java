/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import mm.aeon.com.ass.base.service.operatorDeleteService.OperatorDeleteServiceReqBean;
import mm.aeon.com.ass.base.service.operatorTeamDeleteService.OperatorTeamDeleteServiceReqBean;
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

public class OperatorDeleteController extends AbstractController
        implements IControllerAccessor<OperatorListFormBean, OperatorListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();
    
    @Override
    public OperatorListFormBean process(OperatorListFormBean formBean) {

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);
        
        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        applicationLogger.log("Operator delete start.", LogLevel.INFO);

        OperatorTeamDeleteServiceReqBean operatorTeamReqBean = new OperatorTeamDeleteServiceReqBean();
        operatorTeamReqBean.setTeamOperatorId(formBean.getLineBean().getTeamOperatorId());
        operatorTeamReqBean.setUserId(formBean.getLineBean().getUserId());

        this.getServiceInvoker().addRequest(operatorTeamReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);

        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {

            OperatorDeleteServiceReqBean operatorReqBean = new OperatorDeleteServiceReqBean();
            operatorReqBean.setUserId(formBean.getLineBean().getUserId());

            this.getServiceInvoker().addRequest(operatorReqBean);
            responseMessage = this.getServiceInvoker().invoke();
            resBean = responseMessage.getMessageBean(0);

            if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
                msgBean = new MessageBean(MessageId.MI0003);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);
                applicationLogger.log("Operator deleteing Process finished.", LogLevel.INFO);
            }
        }

        if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("Operator deleteing Process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }

        return formBean;
    }

}
