/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserList;

import mm.aeon.com.ass.base.dto.agencyUserCount.AgencyUserListCountReqDto;
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

public class AgencyUserListInitController extends AbstractASSController
        implements IControllerAccessor<AgencyUserListFormBean, AgencyUserListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyUserListFormBean process(AgencyUserListFormBean formBean) {

        applicationLogger.log("Agency User list Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);
        
        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency User List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        AgencyUserListHeaderBean listHeaderBean = new AgencyUserListHeaderBean();

        AgencyUserListCountReqDto reqDto = new AgencyUserListCountReqDto();
        int count = super.getAgencyUserCount(reqDto);
        listHeaderBean.setCount(count);

        msgBean = new MessageBean(MessageId.MI0007, Integer.toString(count));
        msgBean.setMessageType(MessageType.INFO);
        formBean.getMessageContainer().addMessage(msgBean);
        applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

        formBean.setAgencyUserListHeaderBean(listHeaderBean);
        applicationLogger.log("Agency User list Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
