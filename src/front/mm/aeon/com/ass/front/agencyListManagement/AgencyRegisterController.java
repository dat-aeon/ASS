/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyListManagement;

import java.sql.Timestamp;

import mm.aeon.com.ass.base.service.agencyListRegisterService.AgencyListRegisterServiceReqBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
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
import mm.com.dat.presto.utils.common.InputChecker;

public class AgencyRegisterController extends AbstractController
        implements IControllerAccessor<AgencyListManagementFormBean, AgencyListManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyListManagementFormBean process(AgencyListManagementFormBean formBean) {

        applicationLogger.log("Agency Register Process started.", LogLevel.INFO);
        formBean.getMessageContainer().clearAllMessages(true);
        MessageBean msgBean;

        if (!isValidData(formBean)) {
            return formBean;
        }

        String serviceStatus = null;
        Timestamp currentDate = CommonUtil.getCurrentTimeStamp();
        AgencyListRegisterServiceReqBean serviceReqBean = new AgencyListRegisterServiceReqBean();
        serviceReqBean.setAgencyName(formBean.getAgencyListManagementHeaderBean().getAgencyName());
        serviceReqBean.setLocation(Integer.parseInt(formBean.getAgencyListManagementHeaderBean().getLocation()));
        serviceReqBean.setAddress(formBean.getAgencyListManagementHeaderBean().getAddress());
        serviceReqBean.setMobileTeam(formBean.getAgencyListManagementHeaderBean().getMobileTeam());
        serviceReqBean.setNonMobileTeam(formBean.getAgencyListManagementHeaderBean().getNonMobileTeam());
        serviceReqBean.setRole(formBean.getAgencyListManagementHeaderBean().getSelectedRole());
        serviceReqBean.setIsAeon(formBean.getAgencyListManagementHeaderBean().getIsAeon());
        serviceReqBean.setRemark(formBean.getAgencyListManagementHeaderBean().getRemark());
        serviceReqBean.setCreatedBy(CommonUtil.getLoginUserInfo().getUserId());
        serviceReqBean.setCreatedDate(currentDate);
        serviceReqBean.setUpdatedDate(currentDate);

        this.getServiceInvoker().addRequest(serviceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0001);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            formBean.getAgencyListManagementHeaderBean().setAgencyName(null);
            formBean.getAgencyListManagementHeaderBean().setLocation(null);
            formBean.getAgencyListManagementHeaderBean().setAddress(null);
            formBean.getAgencyListManagementHeaderBean().setMobileTeam(null);
            formBean.getAgencyListManagementHeaderBean().setNonMobileTeam(null);
            formBean.getAgencyListManagementHeaderBean().setRemark(null);
            formBean.getAgencyListManagementHeaderBean()
                    .setSelectedRole(new String[formBean.getAgencyListManagementHeaderBean().getRoleList().size()]);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Agency Registeration finished.", LogLevel.INFO);
        }
        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, DisplayItem.AGENCY_NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registerd staff data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registerd staff data is locked.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }
        applicationLogger.log("Agency Register Process finished.", LogLevel.INFO);
        return formBean;

    }

    private boolean isValidData(AgencyListManagementFormBean formBean) {

        boolean isValid = true;
        MessageBean msgBean;
        if (InputChecker.isBlankOrNull(formBean.getAgencyListManagementHeaderBean().getAgencyName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
            msgBean.addColumnId(DisplayItem.AGENCY_NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        } else {
            /*
             * check format of regular expression at input name
             */
            if (!CommonUtil.checkFormatOfInputName(formBean.getAgencyListManagementHeaderBean().getAgencyName())) {
                msgBean =
                        new MessageBean(MessageId.ME1025, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
                msgBean.addColumnId(DisplayItem.AGENCY_NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        if (InputChecker.isBlankOrNull(formBean.getAgencyListManagementHeaderBean().getLocation())) {
            msgBean = new MessageBean(MessageId.ME0004, DisplayItemBean.getDisplayItemName(DisplayItem.LOCATION));
            msgBean.addColumnId(DisplayItem.LOCATION);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getAgencyListManagementHeaderBean().getAddress())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_ADDRESS));
            msgBean.addColumnId(DisplayItem.AGENCY_ADDRESS);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getAgencyListManagementHeaderBean().getMobileTeam())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.MOBILE_TEAM));
            msgBean.addColumnId(DisplayItem.MOBILE_TEAM + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getAgencyListManagementHeaderBean().getNonMobileTeam())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NONMOBILE_TEAM));
            msgBean.addColumnId(DisplayItem.NONMOBILE_TEAM + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (formBean.getAgencyListManagementHeaderBean().getSelectedRole().length < 1) {
            msgBean = new MessageBean(MessageId.ME0004, DisplayItemBean.getDisplayItemName(DisplayItem.ROLE_LIST));
            msgBean.addColumnId(DisplayItem.ROLE_LIST);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        return isValid;
    }
}
