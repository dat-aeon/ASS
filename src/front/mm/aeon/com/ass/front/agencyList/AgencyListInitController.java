/***********************************************************************
 * $Date: 2018-07-31 $
 * $Author: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/
/***********************************************************************
 * $Modified Date: 2018-08-30 $
 * $Modified By: Thar Pyae $
 * $Rev: 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 ***********************************************************************/

package mm.aeon.com.ass.front.agencyList;

import mm.aeon.com.ass.base.dto.agencyListCount.AgencyListCountReqDto;
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

public class AgencyListInitController extends AbstractASSController
        implements IControllerAccessor<AgencyListFormBean, AgencyListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyListFormBean process(AgencyListFormBean formBean) {

        applicationLogger.log("Agency list Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);
        
        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency List Init]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        AgencyListHeaderBean listHeaderBean = new AgencyListHeaderBean();

        AgencyListCountReqDto reqDto = new AgencyListCountReqDto();
        reqDto.setIsValid(ASSCommon.IS_VALID_FLAG);
        
        int count = super.getAgencyListCount(reqDto);
        listHeaderBean.setCount(count);

        msgBean = new MessageBean(MessageId.MI0007, Integer.toString(count));
        msgBean.setMessageType(MessageType.INFO);
        formBean.getMessageContainer().addMessage(msgBean);
        applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

        formBean.setAgencyLisHeaderBean(listHeaderBean);
        applicationLogger.log("Agency list Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
