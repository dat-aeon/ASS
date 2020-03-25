/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserList;

import mm.aeon.com.ass.base.common.constants.ASSServiceStatusCommon;
import mm.aeon.com.ass.base.service.agencyUserDeleteService.AgencyUserDeleteServiceReqBean;
import mm.aeon.com.ass.base.service.agencyUserDeleteService.AgencyUserDeleteServiceResBean;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserDeleteController extends AbstractController
        implements IControllerAccessor<AgencyUserListFormBean, AgencyUserListFormBean> {

    // private SMSLogger logger = new SMSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyUserListFormBean process(AgencyUserListFormBean formBean) {

        applicationLogger.log("Check Status Process started.", LogLevel.INFO);

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);

        AgencyUserDeleteServiceReqBean reqBean = new AgencyUserDeleteServiceReqBean();
        reqBean.setId(formBean.getLineBean().getId());

        this.getServiceInvoker().addRequest(reqBean);
        ResponseMessage responseMessage = this.getServiceInvoker().invoke();
        AgencyUserDeleteServiceResBean resBean = responseMessage.getMessageBean(0);

        if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
            msgBean = new MessageBean(MessageId.MI0003);
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

        } else if (resBean.getServiceStatus().equals(ASSServiceStatusCommon.FOREIGN_KEY_CONSTRAINT_ERROR)) {
            msgBean = new MessageBean(MessageId.ME1008);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);

            applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
            applicationLogger.log("Deleted user data is used by some data.", LogLevel.ERROR);

        } else if (resBean.getServiceStatus().equals(ServiceStatusCode.SQL_ERROR)) {
            throw new BaseException();
        }

        return formBean;

    }

}
