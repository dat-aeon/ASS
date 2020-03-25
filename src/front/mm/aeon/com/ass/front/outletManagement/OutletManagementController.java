/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletManagement;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.outletAgencyRefer.OutletAgencyReferReqDto;
import mm.aeon.com.ass.base.dto.outletAgencyRefer.OutletAgencyReferResDto;
import mm.aeon.com.ass.base.dto.outletRefer.OutletReferReqDto;
import mm.aeon.com.ass.base.dto.outletRefer.OutletReferResDto;
import mm.aeon.com.ass.base.service.outletAgencyRegisterService.OutletAgencyRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.outletAgencyUpdateService.OutletAgencyUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.outletRegisterService.OutletRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.outletRegisterService.OutletRegisterServiceResBean;
import mm.aeon.com.ass.base.service.outletUpdateService.OutletUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.outletUpdateService.OutletUpdateServiceResBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
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

public class OutletManagementController extends AbstractController
        implements IControllerAccessor<OutletManagementFormBean, OutletManagementFormBean> {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public OutletManagementFormBean process(OutletManagementFormBean formBean) {

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Outlet List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
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

        applicationLogger.log("Outlet " + functionName + " Process started.", LogLevel.INFO);

        try {
            if (!formBean.getManagementHeaderBean().getForUpdate() || !formBean.getManagementHeaderBean()
                    .getOldAgencyId().equals(formBean.getManagementHeaderBean().getAgencyId())) {

                OutletAgencyReferReqDto reqDto = new OutletAgencyReferReqDto();
                reqDto.setAgencyId(formBean.getManagementHeaderBean().getAgencyId());
                OutletAgencyReferResDto resDto =
                        (OutletAgencyReferResDto) CommonUtil.getDaoServiceInvoker().execute(reqDto);

                if (null == resDto) {
                    msgBean = new MessageBean(MessageId.ME1002,
                            DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
                    msgBean.addColumnId(DisplayItem.AGENCY_NAME);
                    msgBean.setMessageType(MessageType.ERROR);
                    formBean.getMessageContainer().addMessage(msgBean);
                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("Outlet " + functionName + " Process unfinished.", LogLevel.INFO);

                    return formBean;

                } else if (resDto.getValidStatus() == 0) {
                    msgBean = new MessageBean(MessageId.ME1019,
                            DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
                    msgBean.addColumnId(DisplayItem.AGENCY_NAME);
                    msgBean.setMessageType(MessageType.ERROR);
                    formBean.getMessageContainer().addMessage(msgBean);
                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("Outlet " + functionName + " Process unfinished.", LogLevel.INFO);

                    return formBean;
                }
            }

            if (!formBean.getIsHeadOffice() && formBean.getManagementHeaderBean().getOutletName().toLowerCase()
                    .contains(ASSCommon.OUT_HEAD_OFFICE.toLowerCase())) {
                msgBean =
                        new MessageBean(MessageId.ME1012, DisplayItemBean.getDisplayItemName(DisplayItem.OUTLET_NAME));
                msgBean.addColumnId(DisplayItem.OUTLET_NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Outlet " + functionName + " Process unfinished.", LogLevel.INFO);

                return formBean;
            }

            String serviceStatus = null;

            if (!formBean.getManagementHeaderBean().getForUpdate()) {

                OutletRegisterServiceReqBean outletReqBean = new OutletRegisterServiceReqBean();
                OutletRegisterServiceResBean outletResBean;

                outletReqBean.setAddress(formBean.getManagementHeaderBean().getAddress());
                outletReqBean.setCreatedBy(CommonUtil.getLoginUserName());
                outletReqBean.setCreatedTime(CommonUtil.getCurrentTimeStamp());
                outletReqBean.setOutletName(formBean.getManagementHeaderBean().getOutletName());
                outletReqBean.setRemark(formBean.getManagementHeaderBean().getRemark());
                if (StringUtils.isEmpty(formBean.getManagementHeaderBean().getUploadedImageFilePath())) {
                    outletReqBean.setImagePath(new String());
                } else {
                    outletReqBean.setImagePath(formBean.getManagementHeaderBean().getUploadedImageFilePath());
                }
                outletReqBean.setPhoneNo(formBean.getManagementHeaderBean().getPhoneNo());
                outletReqBean.setLatitude(formBean.getManagementHeaderBean().getLatitude());
                outletReqBean.setLongitude(formBean.getManagementHeaderBean().getLongitude());
                outletReqBean.setValidStatus(ASSCommon.IS_VALID);

                this.getServiceInvoker().addRequest(outletReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                outletResBean = responseMessage.getMessageBean(0);
                serviceStatus = outletResBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {

                    serviceStatus = null;
                    OutletReferReqDto referReqDto = new OutletReferReqDto();
                    OutletReferResDto referResDto;

                    referReqDto.setOutletName(formBean.getManagementHeaderBean().getOutletName());
                    referResDto = (OutletReferResDto) CommonUtil.getDaoServiceInvoker().execute(referReqDto);

                    if (null != referResDto) {

                        OutletAgencyRegisterServiceReqBean outletAgencyReqBean =
                                new OutletAgencyRegisterServiceReqBean();
                        outletAgencyReqBean.setAgencyId(formBean.getManagementHeaderBean().getAgencyId());
                        outletAgencyReqBean.setOutletId(referResDto.getOutletId());
                        outletAgencyReqBean.setValidStatus(ASSCommon.IS_VALID);
                        outletAgencyReqBean.setUpdatedTime(CommonUtil.getCurrentTimeStamp());

                        this.getServiceInvoker().addRequest(outletAgencyReqBean);
                        responseMessage = this.getServiceInvoker().invoke();
                        AbstractServiceResBean abstractResBean = responseMessage.getMessageBean(0);
                        serviceStatus = abstractResBean.getServiceStatus();

                        if (ServiceStatusCode.OK.equals(serviceStatus)) {

                            if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {
                                File sourceFile =
                                        new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                                File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                                        + CommonUtil.getOutletImageFolder() + formBean.getUploadImageFileName());

                                try {
                                    FileHandler.copyFile(sourceFile, destFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            msgBean = new MessageBean(MessageId.MI0001);
                            msgBean.setMessageType(MessageType.INFO);
                            formBean.getMessageContainer().addMessage(msgBean);

                            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                            applicationLogger.log("Operator Register Process finished.", LogLevel.INFO);
                        }
                    }
                }
            } else {
                OutletUpdateServiceReqBean outletReqBean = new OutletUpdateServiceReqBean();
                OutletUpdateServiceResBean outletResBean;

                outletReqBean.setAddress(formBean.getManagementHeaderBean().getAddress());
                outletReqBean.setOutletId(formBean.getManagementHeaderBean().getOutletId());
                if (formBean.getIsHeadOffice()) {
                    outletReqBean.setOutletName(formBean.getManagementHeaderBean().getAgencyId() + ASSCommon.SPACE
                            + formBean.getManagementHeaderBean().getOutletName());
                } else {
                    outletReqBean.setOutletName(formBean.getManagementHeaderBean().getOutletName());
                }
                outletReqBean.setRemark(formBean.getManagementHeaderBean().getRemark());
                if (StringUtils.isEmpty(formBean.getManagementHeaderBean().getUploadedImageFilePath())) {
                    outletReqBean.setImagePath(new String());
                } else {
                    outletReqBean.setImagePath(formBean.getManagementHeaderBean().getUploadedImageFilePath());
                }
                outletReqBean.setPhoneNo(formBean.getManagementHeaderBean().getPhoneNo());
                if (StringUtils.isEmpty(formBean.getManagementHeaderBean().getLongitude())) {
                    outletReqBean.setLongitude(null);
                } else {
                    outletReqBean.setLongitude(formBean.getManagementHeaderBean().getLongitude());
                }

                if (StringUtils.isEmpty(formBean.getManagementHeaderBean().getLatitude())) {
                    outletReqBean.setLatitude(null);
                } else {
                    outletReqBean.setLatitude(formBean.getManagementHeaderBean().getLatitude());
                }

                outletReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
                outletReqBean.setUpdatedTime(formBean.getManagementHeaderBean().getUpdatedTime());

                this.getServiceInvoker().addRequest(outletReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                outletResBean = responseMessage.getMessageBean(0);
                serviceStatus = outletResBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {

                    if (!formBean.getManagementHeaderBean().getAgencyId()
                            .equals(formBean.getManagementHeaderBean().getOldAgencyId())) {

                        OutletAgencyUpdateServiceReqBean outletAgencyReqBean = new OutletAgencyUpdateServiceReqBean();
                        outletAgencyReqBean.setAgencyId(formBean.getManagementHeaderBean().getAgencyId());
                        outletAgencyReqBean.setAgencyOutletId(formBean.getManagementHeaderBean().getAgencyOutletId());
                        outletAgencyReqBean.setOutletId(formBean.getManagementHeaderBean().getOutletId());
                        outletAgencyReqBean
                                .setUpdatedTime(formBean.getManagementHeaderBean().getAgnecyOutletUpdatedTime());

                        this.getServiceInvoker().addRequest(outletAgencyReqBean);
                        responseMessage = this.getServiceInvoker().invoke();
                        AbstractServiceResBean abstractResBean = responseMessage.getMessageBean(0);
                        serviceStatus = abstractResBean.getServiceStatus();
                    }

                    if (ServiceStatusCode.OK.equals(serviceStatus)) {
                        if (formBean.getOldUploadImageFilePath() != null) {
                            if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {
                                File sourceFile =
                                        new File(FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                                File destFile = new File(
                                        CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getOutletImageFolder()
                                                + formBean.getManagementHeaderBean().getUploadedImageFilePath());

                                try {
                                    FileHandler.copyFile(sourceFile, destFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        } else {
                            if (formBean.getUploadImageFileName() != null) {
                                if (!StringUtils.isEmpty(formBean.getTempUploadImageFilePath())) {
                                    File sourceFile = new File(
                                            FileHandler.getSystemPath() + formBean.getTempUploadImageFilePath());
                                    File destFile = new File(CommonUtil.getUploadImageBaseFilePath()
                                            + CommonUtil.getOutletImageFolder() + formBean.getUploadImageFileName());

                                    try {
                                        FileHandler.copyFile(sourceFile, destFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
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
                applicationLogger.log(functionName + " Operater data already exist.", LogLevel.ERROR);

            } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1010);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Updating Operator data is locked.", LogLevel.ERROR);

            } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1011);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Operator data already updated.", LogLevel.ERROR);

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

    private boolean isValidData(OutletManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getOutletName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.OUTLET_NAME));
            msgBean.addColumnId(DisplayItem.OUTLET_NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        } else {
            /*
             * check format of regular expression at input name
             */
            if (!CommonUtil.checkFormatOfInputName(formBean.getManagementHeaderBean().getOutletName())) {
                msgBean =
                        new MessageBean(MessageId.ME1025, DisplayItemBean.getDisplayItemName(DisplayItem.OUTLET_NAME));
                msgBean.addColumnId(DisplayItem.OUTLET_NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        if (InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getAddress())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.ADDRESS));
            msgBean.addColumnId(DisplayItem.ADDRESS);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (null == formBean.getManagementHeaderBean().getAgencyId()
                || 0 == formBean.getManagementHeaderBean().getAgencyId()) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
            msgBean.addColumnId(DisplayItem.AGENCY_NAME + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!formBean.getManagementHeaderBean().getLongitude().matches(ASSCommon.NUMBER_PATTERN)
                && !InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getLongitude())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.LONGITUDE));
            msgBean.addColumnId(DisplayItem.LONGITUDE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!formBean.getManagementHeaderBean().getLatitude().matches(ASSCommon.NUMBER_PATTERN)
                && !InputChecker.isBlankOrNull(formBean.getManagementHeaderBean().getLatitude())) {
            msgBean = new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.LATITUDE));
            msgBean.addColumnId(DisplayItem.LATITUDE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        return isValid;
    }

}
