/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserManagement;

import org.apache.commons.lang3.StringUtils;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.dto.adminRefer.AdminReferReqDto;
import mm.aeon.com.ass.base.dto.adminRefer.AdminReferResDto;
import mm.aeon.com.ass.base.dto.agencyIdRefer.AgencyIdReferReqDto;
import mm.aeon.com.ass.base.dto.agencyIdRefer.AgencyIdReferResDto;
import mm.aeon.com.ass.base.dto.agencyOutletRefer.AgencyOutletReferReqDto;
import mm.aeon.com.ass.base.dto.agencyOutletRefer.AgencyOutletReferResDto;
import mm.aeon.com.ass.base.service.agencyUserRegisterService.AgencyUserRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.agencyUserUpdateService.AgencyUserUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.authenticate.PasswordEncoder;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class AgencyUserRegisterController extends AbstractASSController
        implements IControllerAccessor<AgencyUserManagementFormBean, AgencyUserManagementFormBean> {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyUserManagementFormBean process(AgencyUserManagementFormBean formBean) {

        if (formBean.getUpdateParam() == null) {
            applicationLogger.log("Agency User Register Process started.", LogLevel.INFO);
        } else {
            applicationLogger.log("Agency User Update Process started.", LogLevel.INFO);
        }

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);

        if (!isValidData(formBean)) {
            return formBean;
        }
        try {
            // check login_info duplicate error for messaging.
            AdminReferReqDto adminReferReqDto = new AdminReferReqDto();
            adminReferReqDto.setLoginId(formBean.getRegisterHeaderBean().getLoginID());
            AdminReferResDto adminReferResDto =
                    (AdminReferResDto) CommonUtil.getDaoServiceInvoker().execute(adminReferReqDto);
            if (adminReferResDto != null) {
                msgBean = new MessageBean(MessageId.ME1012, ASSCommon.LOGIN_ID);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Registerd agency user data already exist on management_user_info.",
                        LogLevel.ERROR);
                return formBean;
            }

            AgencyIdReferReqDto agencyReqDto = new AgencyIdReferReqDto();
            agencyReqDto.setId(Integer.parseInt(formBean.getRegisterHeaderBean().getAgencyName()));

            AgencyIdReferResDto agencyResDto =
                    (AgencyIdReferResDto) CommonUtil.getDaoServiceInvoker().execute(agencyReqDto);

            if (agencyResDto == null) {
                msgBean =
                        new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
                msgBean.addColumnId(DisplayItem.AGENCY_NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                return formBean;
            }

            AgencyOutletReferReqDto reqDto = new AgencyOutletReferReqDto();
            reqDto.setOutletId(Integer.parseInt(formBean.getRegisterHeaderBean().getOutletName()));
            reqDto.setAgencyId(Integer.parseInt(formBean.getRegisterHeaderBean().getAgencyName()));

            AgencyOutletReferResDto resDto =
                    (AgencyOutletReferResDto) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            if (resDto == null) {
                msgBean =
                        new MessageBean(MessageId.ME1003, DisplayItemBean.getDisplayItemName(DisplayItem.OUTLET_NAME));
                msgBean.addColumnId(DisplayItem.OUTLET_NAME);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                return formBean;
            }

            String serviceStatus = null;

            // Register case
            if (formBean.getUpdateParam() == null) {
                AgencyUserRegisterServiceReqBean insertServiceReqBean = new AgencyUserRegisterServiceReqBean();
                insertServiceReqBean.setLoginID(formBean.getRegisterHeaderBean().getLoginID());
                insertServiceReqBean.setAgencyId(Integer.parseInt(formBean.getRegisterHeaderBean().getAgencyName()));
                insertServiceReqBean.setAgencyOutletId(resDto.getId());
                insertServiceReqBean
                        .setPassword(PasswordEncoder.encode(formBean.getRegisterHeaderBean().getPassword()));
                insertServiceReqBean.setName(formBean.getRegisterHeaderBean().getUserName());
                insertServiceReqBean.setStartDate(
                        CommonUtil.getChangeStringToTimeStamp(formBean.getRegisterHeaderBean().getStartDate()));
                insertServiceReqBean.setEndDate(
                        CommonUtil.getChangeStringToTimeStamp(formBean.getRegisterHeaderBean().getEndDate()));
                insertServiceReqBean.setPhoneNo(formBean.getRegisterHeaderBean().getPhoneNumber());
                insertServiceReqBean.setEmail(formBean.getRegisterHeaderBean().getEmail());
                insertServiceReqBean.setAddress(formBean.getRegisterHeaderBean().getAddress());
                insertServiceReqBean.setRemark(formBean.getRegisterHeaderBean().getRemark());
                insertServiceReqBean.setIsValid(Integer.parseInt(ASSCommon.ONE));
                insertServiceReqBean.setCreatedBy(CommonUtil.getLoginUserName());

                this.getServiceInvoker().addRequest(insertServiceReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
                serviceStatus = resBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {
                    msgBean = new MessageBean(MessageId.MI0001);
                    msgBean.setMessageType(MessageType.INFO);
                    formBean.getMessageContainer().addMessage(msgBean);
                    // set init data
                    formBean.setRegisterHeaderBean(super.getAgencyUserRegisterInitData());
                    formBean.getRegisterHeaderBean().setIsRegisterPage(true);

                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("Agency User Register Process finished.", LogLevel.INFO);

                }

                // Update case
            } else {
                AgencyUserUpdateServiceReqBean updateServiceReqBean = new AgencyUserUpdateServiceReqBean();
                updateServiceReqBean.setId(formBean.getRegisterHeaderBean().getId());
                updateServiceReqBean.setLoginID(formBean.getRegisterHeaderBean().getLoginID());
                updateServiceReqBean.setAgencyId(Integer.parseInt(formBean.getRegisterHeaderBean().getAgencyName()));
                updateServiceReqBean.setAgencyOutletId(resDto.getId());
                if (!StringUtils.isEmpty(formBean.getRegisterHeaderBean().getPassword())) {
                    updateServiceReqBean
                            .setPassword(PasswordEncoder.encode(formBean.getRegisterHeaderBean().getPassword()));
                }
                updateServiceReqBean.setName(formBean.getRegisterHeaderBean().getUserName());
                updateServiceReqBean.setStartDate(
                        CommonUtil.getChangeStringToTimeStamp(formBean.getRegisterHeaderBean().getStartDate()));
                updateServiceReqBean.setEndDate(
                        CommonUtil.getChangeStringToTimeStamp(formBean.getRegisterHeaderBean().getEndDate()));
                updateServiceReqBean.setPhoneNo(formBean.getRegisterHeaderBean().getPhoneNumber());
                updateServiceReqBean.setEmail(formBean.getRegisterHeaderBean().getEmail());
                updateServiceReqBean.setAddress(formBean.getRegisterHeaderBean().getAddress());
                updateServiceReqBean.setRemark(formBean.getRegisterHeaderBean().getRemark());
                updateServiceReqBean.setIsValid(Integer.parseInt(ASSCommon.ONE));
                updateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName());
                updateServiceReqBean.setUpdatedTime(formBean.getRegisterHeaderBean().getUpdatedTime());
                updateServiceReqBean.setIsUpdate(true);

                this.getServiceInvoker().addRequest(updateServiceReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
                serviceStatus = resBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
                    msgBean = new MessageBean(MessageId.MI0002);
                    msgBean.setMessageType(MessageType.INFO);
                    formBean.getMessageContainer().addMessage(msgBean);

                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("Agency User Update Process finished.", LogLevel.INFO);

                }
            }

            if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1012, ASSCommon.LOGIN_ID);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Registerd agency user data already exist.", LogLevel.ERROR);

            } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1009);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Updated agency user data is locked.", LogLevel.ERROR);

            } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1010);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Updated agency user data is locked.", LogLevel.ERROR);

            } else if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1011);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Updated agency user data already updated.", LogLevel.ERROR);

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

    public static void main(String[] args) {
        System.out.println(PasswordEncoder.encode(null));
    }

    private boolean isValidData(AgencyUserManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getUserName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.USER_NAME));
            msgBean.addColumnId(DisplayItem.USER_NAME);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getLoginID())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.LOGIN_ID));
            msgBean.addColumnId(DisplayItem.LOGIN_ID);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (formBean.getUpdateParam() == null) {
            isValid = isValidPassword(formBean);

        } else {
            if (!InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getPassword())
                    || !InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getConfirmPassword())) {
                isValid = isValidPassword(formBean);
            }
        }

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getAgencyName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.AGENCY_NAME));
            msgBean.addColumnId(DisplayItem.AGENCY_NAME + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getOutletName())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.OUTLET_NAME));
            msgBean.addColumnId(DisplayItem.OUTLET_NAME + ASSCommon.LABEL);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getStartDate())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.START_DATE));
            msgBean.addColumnId(DisplayItem.START_DATE + ASSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getEndDate())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.END_DATE));
            msgBean.addColumnId(DisplayItem.END_DATE + ASSCommon.INPUT);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (!InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getEmail())) {
            if (!formBean.getRegisterHeaderBean().getEmail().contains(ASSCommon.AMPERSAND)) {
                msgBean = new MessageBean(MessageId.ME1007, DisplayItemBean.getDisplayItemName(DisplayItem.EMAIL));
                msgBean.addColumnId(DisplayItem.EMAIL);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                isValid = false;
            }
        }

        if (InputChecker.isBlankOrNull(formBean.getRegisterHeaderBean().getPhoneNumber())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
            msgBean.addColumnId(DisplayItem.PHONE_NO);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        } else if (!CommonUtil.isValidPhoneNo(formBean.getRegisterHeaderBean().getPhoneNumber())) {
            msgBean = new MessageBean(MessageId.ME1007, DisplayItemBean.getDisplayItemName(DisplayItem.PHONE_NO));
            msgBean.addColumnId(DisplayItem.PHONE_NO);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        return isValid;

    }

    private boolean isValidPassword(AgencyUserManagementFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;
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
            msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.CONFIRM_PASSWORD));
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
        return isValid;
    }
}
