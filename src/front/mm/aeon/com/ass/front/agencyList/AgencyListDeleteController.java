/***********************************************************************
 * $Date : 2018-08-13 $
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
/***********************************************************************
 * $Modified Date : 2018-09-03 $
 * $Modified By : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/

package mm.aeon.com.ass.front.agencyList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.agencyListDeleteService.AgencyListDeleteServiceReqBean;
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

public class AgencyListDeleteController extends AbstractController
        implements IControllerAccessor<AgencyListFormBean, AgencyListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyListFormBean process(AgencyListFormBean formBean) {

        applicationLogger.log("Agency Delete Process started.", LogLevel.INFO);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency Delete]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        MessageBean msgBean;

        String serviceStatus = null;

        AgencyListDeleteServiceReqBean deleteServiceReqBean = new AgencyListDeleteServiceReqBean();
        deleteServiceReqBean.setAgencyId(formBean.getLineBean().getAgencyId());
        
        this.getServiceInvoker().addRequest(deleteServiceReqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
        serviceStatus = resBean.getServiceStatus();

        if (ServiceStatusCode.OK.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Agency Delete finished.", LogLevel.INFO);
        }
        
        if(ASSServiceStatusCommon.FOREIGN_KEY_CONSTRAINT_ERROR.equals(serviceStatus)) {
            msgBean = new MessageBean(MessageId.ME1008,Integer.toString(formBean.getLineBean().getAgencyId()));
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Agency Delete is not completed as agecncy outlet is still used.", LogLevel.INFO);                       
        }
        
        if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
            throw new BaseException();
        }
        return formBean;
    }
}
