/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletList;

import mm.aeon.com.ass.base.service.outletAgencyDeleteService.OutletAgencyDeleteServiceReqBean;
import mm.aeon.com.ass.base.service.outletDeleteService.OutletDeleteServiceReqBean;
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

public class OutletDeleteController extends AbstractController
        implements IControllerAccessor<OutletListFormBean, OutletListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public OutletListFormBean process(OutletListFormBean formBean) {

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Received List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Outlet delete start.", LogLevel.INFO);

        OutletDeleteServiceReqBean outletReqBean = new OutletDeleteServiceReqBean();
        outletReqBean.setOutletId(formBean.getLineBean().getOutletId());

        this.getServiceInvoker().addRequest(outletReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);

        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("Outlet deleteing Process finished.", LogLevel.INFO);
        }

        if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("Outlet deleteing Process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }
        
        if (resBean.getServiceStatus().equals(ServiceStatusCode.RECORD_NOT_FOUND_ERROR)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("Outlet data is already deleted by other user", LogLevel.INFO);
        }

        return formBean;
    }

}
