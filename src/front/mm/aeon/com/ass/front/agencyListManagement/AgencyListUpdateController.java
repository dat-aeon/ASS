/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyListManagement;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.agencyListUpdateService.AgencyListUpdateServiceReqBean;
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

public class AgencyListUpdateController extends AbstractController
        implements IControllerAccessor<AgencyListManagementFormBean, AgencyListManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyListManagementFormBean process(AgencyListManagementFormBean formBean) {

        applicationLogger.log("Agency Update Process started.", LogLevel.INFO);
        formBean.getMessageContainer().clearAllMessages(true);
        MessageBean msgBean;

        if (!isValidData(formBean)) {
            return formBean;
        }

        String serviceStatus = null;

        AgencyListUpdateServiceReqBean serviceReqBean = new AgencyListUpdateServiceReqBean();
        serviceReqBean.setAgencyId(formBean.getUpdateParam().getAgencyId());
        serviceReqBean.setAgencyName(formBean.getUpdateParam().getAgencyName());
        serviceReqBean.setLocation(Integer.parseInt(formBean.getUpdateParam().getLocation()));
        serviceReqBean.setAddress(formBean.getUpdateParam().getAddress());
        serviceReqBean.setMobileTeam(formBean.getUpdateParam().getMobileTeam());
        serviceReqBean.setOldMobileId(Integer.parseInt(formBean.getUpdateParam().getOldMobileId()));
        serviceReqBean.setNonMobileTeam(formBean.getUpdateParam().getNonMobileTeam());
        serviceReqBean.setOldNonMobileId(Integer.parseInt(formBean.getUpdateParam().getOldNonMobileId()));
        serviceReqBean.setRole(formBean.getUpdateParam().getSelectedRole());
        serviceReqBean.setOldRoleList(formBean.getUpdateParam().getOldRoleList());
        serviceReqBean.setIsAeon(formBean.getUpdateParam().getIsAeon());
        serviceReqBean.setRemark(formBean.getUpdateParam().getRemark());
        serviceReqBean.setUpdatetedBy(CommonUtil.getLoginUserInfo().getUserId());
        serviceReqBean.setUpdatedDate(CommonUtil.getCurrentTimeStamp());
        serviceReqBean.setOldUpdatedDate(formBean.getUpdateParam().getAgencyUptDate());
        this.getServiceInvoker().addRequest(serviceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Agency Updating finished.", LogLevel.INFO);

        }

        if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0008);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Agency Update data not found.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Agency Update data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Agency Update data is already updated.", LogLevel.ERROR);
        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        applicationLogger.log("Agency Update Process finished.", LogLevel.INFO);
        return formBean;

    }

    private boolean isValidData(AgencyListManagementFormBean formBean) {

        boolean isValid = true;
        MessageBean msgBean;
        if (InputChecker.isBlankOrNull(formBean.getUpdateParam().getAgencyName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
            msgBean.addColumnId(DisplayItem.AGENCY_NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        } else {
            /*
             * check format of regular expression at input name
             */
            if (!CommonUtil.checkFormatOfInputName(formBean.getUpdateParam().getAgencyName())) {
                msgBean =
                        new MessageBean(MessageId.ME1025, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
                msgBean.addColumnId(DisplayItem.AGENCY_NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

        }

        if (InputChecker.isBlankOrNull(formBean.getUpdateParam().getLocation())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LOCATION));
            msgBean.addColumnId(DisplayItem.LOCATION);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getUpdateParam().getAddress())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_ADDRESS));
            msgBean.addColumnId(DisplayItem.AGENCY_ADDRESS);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getUpdateParam().getMobileTeam())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.MOBILE_TEAM));
            msgBean.addColumnId(DisplayItem.MOBILE_TEAM + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getUpdateParam().getNonMobileTeam())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NONMOBILE_TEAM));
            msgBean.addColumnId(DisplayItem.NONMOBILE_TEAM + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (formBean.getUpdateParam().getSelectedRole().length < 1) {
            msgBean = new MessageBean(MessageId.ME0004, DisplayItemBean.getDisplayItemName(DisplayItem.ROLE_LIST));
            msgBean.addColumnId(DisplayItem.ROLE_LIST + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }
}
