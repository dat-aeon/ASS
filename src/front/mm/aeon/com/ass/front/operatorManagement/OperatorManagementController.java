/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.operatorManagement;

import mm.aeon.com.ass.base.dto.operatorRefer.OperatorReferReqDto;
import mm.aeon.com.ass.base.dto.operatorRefer.OperatorReferResDto;
import mm.aeon.com.ass.base.dto.operatorTeamRefer.OperatorTeamReferReqDto;
import mm.aeon.com.ass.base.dto.operatorTeamRefer.OperatorTeamReferResDto;
import mm.aeon.com.ass.base.service.operatorRegisterService.OperatorRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.operatorRegisterService.OperatorRegisterServiceResBean;
import mm.aeon.com.ass.base.service.operatorTeamRegisterService.OperatorTeamRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.operatorTeamUpdateService.OperatorTeamUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.operatorUpdateService.OperatorUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.operatorUpdateService.OperatorUpdateServiceResBean;
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

public class OperatorManagementController extends AbstractController
        implements IControllerAccessor<OperatorManagementFormBean, OperatorManagementFormBean> {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public OperatorManagementFormBean process(OperatorManagementFormBean formBean) {

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Operator List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
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

        applicationLogger.log("Operator " + functionName + " Process started.", LogLevel.INFO);

        try {

            if (!formBean.getManagementHeaderBean().getForUpdate() || !formBean.getManagementHeaderBean().getOldTeamId()
                    .equals(formBean.getManagementHeaderBean().getTeamId())) {

                OperatorTeamReferReqDto reqDto = new OperatorTeamReferReqDto();
                reqDto.setTeamId(formBean.getManagementHeaderBean().getTeamId());

                OperatorTeamReferResDto resDto =
                        (OperatorTeamReferResDto) CommonUtil.getDaoServiceInvoker().execute(reqDto);

                if (null == resDto) {
                    msgBean = new MessageBean(MessageId.ME1002,
                            DisplayItemBean.getDisplayItemName(DisplayItem.TEAM_NAME));
                    msgBean.addColumnId(DisplayItem.TEAM_NAME);
                    msgBean.setMessageType(MessageType.ERROR);

                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("Agency User " + functionName + " Process unfinished.", LogLevel.INFO);

                    return formBean;

                } else if (resDto.getIsValid() == 0) {
                    msgBean = new MessageBean(MessageId.ME1019,
                            DisplayItemBean.getDisplayItemName(DisplayItem.TEAM_NAME));
                    msgBean.addColumnId(DisplayItem.TEAM_NAME);
                    msgBean.setMessageType(MessageType.ERROR);

                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("Agency User " + functionName + " Process unfinished.", LogLevel.INFO);

                    return formBean;
                }
            }

            String serviceStatus = null;

            if (!formBean.getManagementHeaderBean().getForUpdate()) {

                OperatorRegisterServiceReqBean operatorReqBean = new OperatorRegisterServiceReqBean();
                OperatorRegisterServiceResBean operatorResBean;

                operatorReqBean.setCreatedBy(CommonUtil.getLoginUserName());
                operatorReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
                operatorReqBean.setValidStatus(ASSCommon.IS_VALID);
                operatorReqBean.setRole(ASSCommon.OPERATOR_ROLE);
                operatorReqBean.setPassword(PasswordEncoder.encode(formBean.getManagementHeaderBean().getPassword()));
                operatorReqBean.setUserLoginId(formBean.getManagementHeaderBean().getUserLoginId());
                operatorReqBean.setUserName(formBean.getManagementHeaderBean().getUserName());

                this.getServiceInvoker().addRequest(operatorReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                operatorResBean = responseMessage.getMessageBean(0);
                serviceStatus = operatorResBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {

                    OperatorReferReqDto referReqDto = new OperatorReferReqDto();
                    OperatorReferResDto referResDto;
                    referReqDto.setUserLoginId(operatorReqBean.getUserLoginId());

                    referResDto = (OperatorReferResDto) CommonUtil.getDaoServiceInvoker().execute(referReqDto);

                    if (null != referResDto) {

                        OperatorTeamRegisterServiceReqBean operatorTeamReqBean =
                                new OperatorTeamRegisterServiceReqBean();
                        operatorTeamReqBean.setTeamId(formBean.getManagementHeaderBean().getTeamId());
                        operatorTeamReqBean.setUserId(referResDto.getUserId());
                        operatorTeamReqBean.setValidStatus(ASSCommon.IS_VALID);
                        operatorTeamReqBean.setUpdatedTime(operatorReqBean.getCreatedTime());

                        this.getServiceInvoker().addRequest(operatorTeamReqBean);
                        responseMessage = this.getServiceInvoker().invoke();
                        AbstractServiceResBean abstractResBean = responseMessage.getMessageBean(0);
                        serviceStatus = abstractResBean.getServiceStatus();

                        if (ServiceStatusCode.OK.equals(serviceStatus)) {
                            msgBean = new MessageBean(MessageId.MI0001);
                            msgBean.setMessageType(MessageType.INFO);
                            formBean.getMessageContainer().addMessage(msgBean);

                            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                            applicationLogger.log("Operator " + functionName + " Process finished.", LogLevel.INFO);
                        }
                    }
                }
            } else {
                OperatorUpdateServiceReqBean operatorReqBean = new OperatorUpdateServiceReqBean();
                OperatorUpdateServiceResBean operatorResBean;

                if (!InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getPassword())) {
                    operatorReqBean
                            .setPassword(PasswordEncoder.encode(formBean.getManagementHeaderBean().getPassword()));
                }

                operatorReqBean.setUserId(formBean.getManagementHeaderBean().getUserId());
                operatorReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
                operatorReqBean.setUpdateTime(formBean.getManagementHeaderBean().getUpdatedTime());
                operatorReqBean.setUserLoginId(formBean.getManagementHeaderBean().getUserLoginId());
                operatorReqBean.setUserName(formBean.getManagementHeaderBean().getUserName());

                this.getServiceInvoker().addRequest(operatorReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                operatorResBean = responseMessage.getMessageBean(0);
                serviceStatus = operatorResBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {

                    if (!formBean.getManagementHeaderBean().getTeamId()
                            .equals(formBean.getManagementHeaderBean().getOldTeamId())) {
                        OperatorTeamUpdateServiceReqBean operatorTeamReqBean = new OperatorTeamUpdateServiceReqBean();
                        operatorTeamReqBean.setTeamId(formBean.getManagementHeaderBean().getTeamId());
                        operatorTeamReqBean.setUserId(formBean.getManagementHeaderBean().getUserId());
                        operatorTeamReqBean.setTeamOperatorId(formBean.getManagementHeaderBean().getTeamOperatorId());
                        operatorTeamReqBean
                                .setUpdateTime(formBean.getManagementHeaderBean().getTeamOperatorUpdateTime());

                        this.getServiceInvoker().addRequest(operatorTeamReqBean);
                        responseMessage = this.getServiceInvoker().invoke();
                        AbstractServiceResBean abstractResBean = responseMessage.getMessageBean(0);
                        serviceStatus = abstractResBean.getServiceStatus();
                    }

                    if (ServiceStatusCode.OK.equals(serviceStatus)) {
                        msgBean = new MessageBean(MessageId.MI0002);
                        msgBean.setMessageType(MessageType.INFO);
                        formBean.getMessageContainer().addMessage(msgBean);

                        applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                        applicationLogger.log("Operator Update Process finished.", LogLevel.INFO);

                    }
                }
            }

            if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1012, ASSCommon.LOGIN_ID);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Registered Operater data already exist.", LogLevel.ERROR);

            } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1009);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Team data is deleted.", LogLevel.ERROR);

            } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
                throw new BaseException();
            }

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

    private boolean isValidData(OperatorManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getUserLoginId())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LOGIN_ID));
            msgBean.addColumnId(DisplayItem.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getUserName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.NAME));
            msgBean.addColumnId(DisplayItem.NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (null == formBean.getManagementHeaderBean().getTeamId() || 0 == formBean.getManagementHeaderBean().getTeamId()) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.TEAM_NAME));
            msgBean.addColumnId(DisplayItem.TEAM_NAME + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        isValid = isValid & isValidPassword(formBean);

        return isValid;
    }

    private boolean isValidPassword(OperatorManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (!formBean.getManagementHeaderBean().getForUpdate() || !InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getPassword())
                || !InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getConfirmPassword())) {

            if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getPassword())) {
                msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PASSWORD));
                msgBean.addColumnId(DisplayItem.PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            } else if (formBean.getManagementHeaderBean().getPassword().length() < 8) {
                msgBean = new MessageBean(MessageId.ME0005);
                msgBean.addColumnId(DisplayItem.PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }

            if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getConfirmPassword())) {
                msgBean = new MessageBean(MessageId.ME0003,
                        DisplayItemBean.getDisplayItemName(DisplayItem.CONFIRM_PASSWORD));
                msgBean.addColumnId(DisplayItem.CONFIRM_PASSWORD);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            } else if (!(formBean.getManagementHeaderBean().getConfirmPassword()
                    .equals(formBean.getManagementHeaderBean().getPassword()))) {
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
