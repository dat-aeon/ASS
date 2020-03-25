/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.adminManagement;

import java.util.ArrayList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.adminRefer.AdminReferReqDto;
import mm.aeon.com.ass.base.dto.adminRefer.AdminReferResDto;
import mm.aeon.com.ass.base.dto.agencyUserLoginIdRefer.AgencyUserLoginIdReferReqDto;
import mm.aeon.com.ass.base.dto.agencyUserLoginIdRefer.AgencyUserLoginIdReferResDto;
import mm.aeon.com.ass.base.dto.groupSelectList.GroupSelectListReqDto;
import mm.aeon.com.ass.base.dto.groupSelectList.GroupSelectListResDto;
import mm.aeon.com.ass.base.service.adminGroupRegisterService.AdminGroupRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.adminGroupRegisterService.AdminGroupRegisterServiceResBean;
import mm.aeon.com.ass.base.service.adminRegisterService.AdminRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.adminUpdateService.AdminUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.authenticate.PasswordEncoder;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class AdminManagementController extends AbstractController
        implements IControllerAccessor<AdminManagementFormBean, AdminManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public AdminManagementFormBean process(AdminManagementFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);
        MessageBean msgBean;

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Admin Management]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        if (!isValidData(formBean)) {
            return formBean;
        }

        // check login_info duplicate error for messaging.
        try {
            AgencyUserLoginIdReferReqDto userReferReqDto = new AgencyUserLoginIdReferReqDto();
            userReferReqDto.setLoginId(formBean.getRegisterHeaderBean().getLoginId());
            AgencyUserLoginIdReferResDto userReferResDto =
                    (AgencyUserLoginIdReferResDto) CommonUtil.getDaoServiceInvoker().execute(userReferReqDto);

            if (userReferResDto != null) {
                msgBean = new MessageBean(MessageId.ME1012, ASSCommon.LOGIN_ID);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Registerd admin data already exist on Login Info table.", LogLevel.ERROR);
                return formBean;
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        String serviceStatus = null;

        if (!formBean.getIsUpdate()) {

            applicationLogger.log("Admin Register Process started.", LogLevel.INFO);

            AdminRegisterServiceReqBean serviceReqBean = new AdminRegisterServiceReqBean();

            serviceReqBean.setCreatedBy(CommonUtil.getLoginUserInfo().getUserId());
            serviceReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
            serviceReqBean.setLoginId(formBean.getRegisterHeaderBean().getLoginId());
            serviceReqBean.setName(formBean.getRegisterHeaderBean().getName());
            String hashPassword = PasswordEncoder.encode(formBean.getRegisterHeaderBean().getPassword());
            serviceReqBean.setPassword(hashPassword);
            serviceReqBean.setRole(ASSCommon.ADMIN_ROLE);
            serviceReqBean.setValidStatus(ASSCommon.IS_VALID);

            this.getServiceInvoker().addRequest(serviceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();

            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                AdminReferReqDto adminReqDto = new AdminReferReqDto();
                adminReqDto.setLoginId(formBean.getRegisterHeaderBean().getLoginId());
                try {
                    AdminReferResDto adminResDto =
                            (AdminReferResDto) CommonUtil.getDaoServiceInvoker().execute(adminReqDto);

                    if (adminResDto != null) {
                        GroupSelectListReqDto selectListReqDto = new GroupSelectListReqDto();

                        ArrayList<GroupSelectListResDto> resDtoList = (ArrayList<GroupSelectListResDto>) CommonUtil
                                .getDaoServiceInvoker().execute(selectListReqDto);

                        for (GroupSelectListResDto resDto : resDtoList) {
                            AdminGroupRegisterServiceReqBean reqBean = new AdminGroupRegisterServiceReqBean();
                            reqBean.setAdminId(adminResDto.getId());
                            reqBean.setGroupId(resDto.getId());
                            reqBean.setFinishFlag(1);

                            this.getServiceInvoker().addRequest(reqBean);
                            responseMessage = this.getServiceInvoker().invoke();
                            AdminGroupRegisterServiceResBean adminResBean = responseMessage.getMessageBean(0);
                            serviceStatus = adminResBean.getServiceStatus();

                            if (!ServiceStatusCode.OK.equals(serviceStatus)) {
                                setErrorMessage(formBean, serviceStatus);
                            }
                        }
                    }
                } catch (PrestoDBException e) {
                    if (e instanceof DaoSqlException) {
                        logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                        throw new BaseException(e.getCause());
                    }
                }

                msgBean = new MessageBean(MessageId.MI0001);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Admin registration finished.", LogLevel.INFO);

            } else {
                setErrorMessage(formBean, serviceStatus);
            }

        } else {
            applicationLogger.log("Admin Update Process started.", LogLevel.INFO);

            AdminUpdateServiceReqBean updateServiceReqBean = new AdminUpdateServiceReqBean();
            updateServiceReqBean.setId(formBean.getRegisterHeaderBean().getId());
            updateServiceReqBean.setLoginId(formBean.getRegisterHeaderBean().getLoginId());
            updateServiceReqBean.setName(formBean.getRegisterHeaderBean().getName());
            updateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserInfo().getUserId());
            updateServiceReqBean.setValidStatus(formBean.getRegisterHeaderBean().getValidStatus());
            updateServiceReqBean.setUpdatedDate(formBean.getRegisterHeaderBean().getUpdatedDate());

            if (!InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getPassword())) {
                String hashPassword = PasswordEncoder.encode(formBean.getRegisterHeaderBean().getPassword());
                updateServiceReqBean.setPassword(hashPassword);
            }

            this.getServiceInvoker().addRequest(updateServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {

                formBean.getUpdateParam().setUpdate(true);
                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Admin update process finished.", LogLevel.INFO);
            } else {
                setErrorMessage(formBean, serviceStatus);
            }
        }

        return formBean;
    }

    private AdminManagementFormBean setErrorMessage(AdminManagementFormBean formBean, String serviceStatus) {

        MessageBean msgBean;

        if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1012, ASSCommon.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Registerd admin data already exist.", LogLevel.ERROR);

        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Update admin data is locked.", LogLevel.ERROR);

        } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            // formBean.getUpdateParam().setIsUpdate(true);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating admin data already updated.", LogLevel.ERROR);

        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1009);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Updating admin data already deleted by other.", LogLevel.ERROR);

        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        return formBean;
    }

    private boolean isValidData(AdminManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getLoginId())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LOGIN_ID));
            msgBean.addColumnId(DisplayItem.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NAME));
            msgBean.addColumnId(DisplayItem.NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getPassword())
                || !InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getConfirmPassword())
                || !formBean.getIsUpdate()) {
            if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getPassword())) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PASSWORD));
                msgBean.addColumnId(DisplayItem.PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            } else if (formBean.getRegisterHeaderBean().getPassword().length() < 8) {
                msgBean = new MessageBean(MessageId.ME0005);
                msgBean.addColumnId(DisplayItem.PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getConfirmPassword())) {
                msgBean = new MessageBean(MessageId.ME0003,
                        DisplayItemBean.getDisplayItemName(DisplayItem.CONFIRM_PASSWORD));
                msgBean.addColumnId(DisplayItem.CONFIRM_PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            } else if (!(formBean.getRegisterHeaderBean().getConfirmPassword()
                    .equals(formBean.getRegisterHeaderBean().getPassword()))) {
                msgBean = new MessageBean(MessageId.ME0006);
                msgBean.addColumnId(DisplayItem.CONFIRM_PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        return isValid;
    }

}
