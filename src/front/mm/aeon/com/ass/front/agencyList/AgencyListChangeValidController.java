/***********************************************************************
 * $Date: 2018-08-14 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
/***********************************************************************
 * $Modified Date: 2018-08-14 $
 * $Modified By: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
package mm.aeon.com.ass.front.agencyList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.agencyListChangeValidService.AgencyListChangeValidServiceReqBean;
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

public class AgencyListChangeValidController extends AbstractController
        implements IControllerAccessor<AgencyListFormBean, AgencyListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyListFormBean process(AgencyListFormBean formBean) {

        applicationLogger.log("Agency Change Valid Process started.", LogLevel.INFO);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency Change Valid]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        MessageBean msgBean;

        String serviceStatus = null;

        AgencyListChangeValidServiceReqBean serviceReqBean = new AgencyListChangeValidServiceReqBean();

        if (formBean.getLineBean().getIsValid() == 1) {
            serviceReqBean.setEnable(false);
        } else {
            serviceReqBean.setEnable(true);
        }

        serviceReqBean.setAgencyId(formBean.getLineBean().getAgencyId());
        serviceReqBean.setDisabledBy(CommonUtil.getLoginUserInfo().getUserId());
        serviceReqBean.setDisableddDate(CommonUtil.getCurrentTimeStamp());
        serviceReqBean.setOldDisableDate(formBean.getLineBean().getDisabledDate());
        
        this.getServiceInvoker().addRequest(serviceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0002);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
        }

        if (ASSServiceStatusCommon.RECORD_ALREADY_UPDATE.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1011);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Chang Valid  Process data already updated.", LogLevel.ERROR);
        } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1010);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Chang Valid  Process data is locked.", LogLevel.ERROR);
        } else if (ServiceStatusCode.RECORD_NOT_FOUND_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0008);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Chang Valid  Process data is not found.", LogLevel.ERROR);
        } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }

        applicationLogger.log("Agency Change Valid Process is finished.", LogLevel.INFO);
        return formBean;

    }
}
