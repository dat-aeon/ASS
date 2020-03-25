/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserManagement;

import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserRegisterInitController extends AbstractASSController
        implements IControllerAccessor<AgencyUserManagementFormBean, AgencyUserManagementFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyUserManagementFormBean process(AgencyUserManagementFormBean formBean) {

        applicationLogger.log("Agency User Register Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency User Register]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        AgencyUserRegisterHeaderBean headerBean = new AgencyUserRegisterHeaderBean();

        headerBean = super.getAgencyUserRegisterInitData();

        if (headerBean.getAgencyNameList().size() == 0) {
            formBean.setIsInitError(true);
            msgBean = new MessageBean(MessageId.ME1002, ASSCommon.TBL_AGENCY_INFO);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
        }

        if (headerBean.getAllOutletNameList().size() == 0) {
            formBean.setIsInitError(true);
            msgBean = new MessageBean(MessageId.ME1002, ASSCommon.TBL_OUTLET_INFO);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
        }
        formBean.setRegisterHeaderBean(headerBean);

        applicationLogger.log("Agency User Register Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
