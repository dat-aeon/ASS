/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamManagement;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.teamRegisterService.TeamRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.teamRegisterService.TeamRegisterServiceResBean;
import mm.aeon.com.ass.base.service.teamUpdateService.TeamUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.teamUpdateService.TeamUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class TeamManagementController extends AbstractController
        implements IControllerAccessor<TeamManagementFormBean, TeamManagementFormBean> {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public TeamManagementFormBean process(TeamManagementFormBean formBean) {

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Team List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        String functionName = "";
        if (formBean.getManagementHeaderBean().getForUpdate()) {
            functionName = "Update";
        } else {
            functionName = "Register";
        }

        if (!isValidData(formBean)) {
            return formBean;
        }

        applicationLogger.log("Team " + functionName + " Process started.", LogLevel.INFO);

        String serviceStatus = null;

        if (!formBean.getManagementHeaderBean().getForUpdate()) {

            TeamRegisterServiceReqBean reqBean = new TeamRegisterServiceReqBean();
            TeamRegisterServiceResBean resBean;

            reqBean.setCreatedBy(CommonUtil.getLoginUserName());
            reqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
            reqBean.setTarget(formBean.getManagementHeaderBean().getTarget());
            reqBean.setTeamName(formBean.getManagementHeaderBean().getTeamName());
            reqBean.setValidStatus(ASSCommon.IS_VALID);

            this.getServiceInvoker().addRequest(reqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.MI0001);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Team Register Process finished.", LogLevel.INFO);
            }
        } else {
            TeamUpdateServiceReqBean reqBean = new TeamUpdateServiceReqBean();
            TeamUpdateServiceResBean resBean;

            reqBean.setUpdatedBy(CommonUtil.getLoginUserName());
            reqBean.setUpdatedTime(formBean.getManagementHeaderBean().getUpdatedTime());
            reqBean.setTarget(formBean.getManagementHeaderBean().getTarget());
            reqBean.setTeamId(formBean.getManagementHeaderBean().getTeamId());

            this.getServiceInvoker().addRequest(reqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Team Update Process finished.", LogLevel.INFO);
            }
        }

        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, ASSCommon.NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log(functionName + " Team data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update team data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update team data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updateing team data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(TeamManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (!formBean.getManagementHeaderBean().getForUpdate()) {

            if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getTeamName())) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NAME));
                msgBean.addColumnId(DisplayItem.NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            } else if (!CommonUtil.isValidTeamName(formBean.getManagementHeaderBean().getTeamName())) {
                msgBean = new MessageBean(MessageId.ME1007, DisplayItemBean.getDisplayItemName(DisplayItem.NAME));
                msgBean.addColumnId(DisplayItem.NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        if (null == formBean.getManagementHeaderBean().getTarget()) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.TARGET));
            msgBean.addColumnId(DisplayItem.TARGET);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

}
