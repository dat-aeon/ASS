/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.initializePassword;

import mm.aeon.com.ass.base.dto.appLoginInfoCount.AppLoginInfoCountReqDto;
import mm.aeon.com.ass.base.service.InitializePasswordRegisterService.InitializePasswordRegisterServiceReqBean;
import mm.aeon.com.ass.base.service.InitializePasswordUpdateService.InitializePasswordUpdateServiceReqBean;
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

public class InitializePasswordUpdateController extends AbstractASSController
        implements IControllerAccessor<InitializePasswordFormBean, InitializePasswordFormBean> {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public InitializePasswordFormBean process(InitializePasswordFormBean formBean) {

        applicationLogger.log("Initialize Password Update Process started.", LogLevel.INFO);

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);

        if (!isValidData(formBean)) {
            return formBean;
        }

        AppLoginInfoCountReqDto reqDto = new AppLoginInfoCountReqDto();

        try {
            int count = (Integer) CommonUtil.getDaoServiceInvoker().execute(reqDto);
            
            String serviceStatus;
            if (count == 0) {
                InitializePasswordRegisterServiceReqBean insertServiceReqBean = new InitializePasswordRegisterServiceReqBean();
                insertServiceReqBean
                        .setPassword(PasswordEncoder.encode(formBean.getHeaderBean().getPassword()));
                insertServiceReqBean.setCreatedBy(CommonUtil.getLoginUserName());

                this.getServiceInvoker().addRequest(insertServiceReqBean);
                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
                serviceStatus = resBean.getServiceStatus();

                if (ServiceStatusCode.OK.equals(serviceStatus)) {
                    msgBean = new MessageBean(MessageId.MI0002);
                    msgBean.setMessageType(MessageType.INFO);
                    formBean.getMessageContainer().addMessage(msgBean);

                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                    applicationLogger.log("Agency User Register Process finished.", LogLevel.INFO);

                }

            } else {
               InitializePasswordUpdateServiceReqBean updateServiceReqBean = new InitializePasswordUpdateServiceReqBean();
                updateServiceReqBean
                        .setPassword(PasswordEncoder.encode(formBean.getHeaderBean().getPassword()));
                updateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName());

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

            } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1010);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Updated agency user data is locked.", LogLevel.ERROR);

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

    private boolean isValidData(InitializePasswordFormBean formBean) {
        boolean isValid = true;
        MessageBean msgBean;
        if (InputChecker.isBlankOrNull(formBean.getHeaderBean().getPassword())) {
            msgBean = new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.PASSWORD));
            msgBean.addColumnId(DisplayItem.PASSWORD);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        } else if (formBean.getHeaderBean().getPassword().length() < 8) {
            msgBean = new MessageBean(MessageId.ME0005);
            msgBean.addColumnId(DisplayItem.PASSWORD);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        return isValid;
    }
}
