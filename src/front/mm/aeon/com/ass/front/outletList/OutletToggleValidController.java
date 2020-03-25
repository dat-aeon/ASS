/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.outletAgencyUpdateService.OutletAgencyUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.outletUpdateService.OutletUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
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

public class OutletToggleValidController extends AbstractController
        implements IControllerAccessor<OutletListFormBean, OutletListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public OutletListFormBean process(OutletListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);
        MessageBean msgBean;
        String serviceStatus;

        OutletUpdateServiceReqBean reqBean = new OutletUpdateServiceReqBean();
        reqBean.setOutletId(formBean.getLineBean().getOutletId());
        reqBean.setUpdatedBy(CommonUtil.getLoginUserInfo().getUserName());
        reqBean.setUpdatedTime(formBean.getLineBean().getUpdatedTime());
        int valid = formBean.getLineBean().getValidStatus();
        valid ^= 1;
        reqBean.setValidStatus(valid);

        this.getServiceInvoker().addRequest(reqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {

            OutletAgencyUpdateServiceReqBean outletAgencyReqBean = new OutletAgencyUpdateServiceReqBean();
            outletAgencyReqBean.setAgencyOutletId(formBean.getLineBean().getAgencyOutletId());
            outletAgencyReqBean.setValidStatus(valid);
            outletAgencyReqBean.setUpdatedTime(formBean.getLineBean().getAgnecyOutletUpdatedTime());

            this.getServiceInvoker().addRequest(outletAgencyReqBean);
            responseMessage = this.getServiceInvoker().invoke();
            AbstractServiceResBean abstractResBean = responseMessage.getMessageBean(0);
            serviceStatus = abstractResBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);
                formBean.setDoReload(new Boolean(true));

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Outlet valid status update finished.", LogLevel.INFO);

            }

        }

        if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update outlet data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            formBean.setDoReload(new Boolean(true));

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updateing outlet data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            formBean.setDoReload(new Boolean(true));

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updateing outlet data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

}
