/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.teamList;

import mm.aeon.com.ass.base.service.teamDeleteService.TeamDeleteServiceReqBean;
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

public class TeamDeleteController extends AbstractController
        implements IControllerAccessor<TeamListFormBean, TeamListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public TeamListFormBean process(TeamListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);
        
        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Team List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        applicationLogger.log("Team deleting process started.", LogLevel.INFO);
        MessageBean msgBean;

        TeamDeleteServiceReqBean reqBean = new TeamDeleteServiceReqBean();
        reqBean.setTeamId(formBean.getLineBean().getTeamId());

        this.getServiceInvoker().addRequest(reqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AbstractServiceResBean resBean = responseMessage.getMessageBean(0);

        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log("Team deleteing Process finished.", LogLevel.INFO);
        } else if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            applicationLogger.log("Team deleteing Process Failed.", LogLevel.ERROR);
            throw new BaseException();
        }

        return formBean;
    }

}
