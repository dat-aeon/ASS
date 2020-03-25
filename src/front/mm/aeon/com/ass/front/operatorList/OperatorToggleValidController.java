/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.operatorTeamUpdateService.OperatorTeamUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.operatorUpdateService.OperatorUpdateServiceReqBean;
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

public class OperatorToggleValidController extends AbstractController
        implements IControllerAccessor<OperatorListFormBean, OperatorListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public OperatorListFormBean process(OperatorListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        MessageBean msgBean;
        String serviceStatus;

        OperatorUpdateServiceReqBean updateServiceReqBean = new OperatorUpdateServiceReqBean();
        updateServiceReqBean.setUserId(formBean.getLineBean().getUserId());
        updateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserInfo().getUserName());
        updateServiceReqBean.setUpdateTime(formBean.getLineBean().getUpdatedTime());
        updateServiceReqBean.setDisabledBy(CommonUtil.getLoginUserInfo().getUserName());

        int valid = formBean.getLineBean().getUserValidStatus();
        valid ^= 1;
        updateServiceReqBean.setValidStatus(valid);
        updateServiceReqBean.setForToggleValid(true);

        this.getServiceInvoker().addRequest(updateServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {

            OperatorTeamUpdateServiceReqBean operatorTeamReqBean = new OperatorTeamUpdateServiceReqBean();
            operatorTeamReqBean.setTeamOperatorId(formBean.getLineBean().getTeamOperatorId());
            operatorTeamReqBean.setValidStatus(updateServiceReqBean.getValidStatus());
            operatorTeamReqBean.setUpdateTime(updateServiceReqBean.getUpdateTime());

            this.getServiceInvoker().addRequest(operatorTeamReqBean);
            responseMessage = this.getServiceInvoker().invoke();
            AbstractServiceResBean abstractResBean = responseMessage.getMessageBean(0);
            serviceStatus = abstractResBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                formBean.setDoReload(true);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Operator valid status update finished.", LogLevel.INFO);
            }
        }

        if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update operator data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            formBean.setDoReload(true);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updateing operator data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            formBean.setDoReload(true);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updateing operator data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

}
