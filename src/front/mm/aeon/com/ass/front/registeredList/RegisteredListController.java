/**************************************************************************
 * $Date : 2018-08-23$
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.registeredList;

import mm.aeon.com.ass.base.dto.registeredListCount.RegisteredListCountReqDto;
import mm.aeon.com.ass.base.dto.registeredListSelectList.RegisteredListSelectListReqDto;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.ASSBusinessException;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class RegisteredListController extends AbstractASSController
        implements IControllerAccessor<RegisteredListFormBean, RegisteredListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public RegisteredListFormBean process(RegisteredListFormBean formBean) {

        applicationLogger.log("Registered list Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Registered List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }
        
        RegisteredListCountReqDto reqDto = new RegisteredListCountReqDto();
        reqDto.setUserName(formBean.getRegisteredListHeaderBean().getUserName());
        reqDto.setAgencyName(formBean.getRegisteredListHeaderBean().getAgencyName());

        try {
            int count = (Integer) CommonUtil.getDaoServiceInvoker().execute(reqDto);
            formBean.setTotalCount(count);
            formBean.getRegisteredListHeaderBean().setRecord(CommonUtil.showRecord(count));
            RegisteredListSelectListReqDto selectListReqDto = new RegisteredListSelectListReqDto();
            selectListReqDto.setUserName(formBean.getRegisteredListHeaderBean().getUserName());
            selectListReqDto.setAgencyName(formBean.getRegisteredListHeaderBean().getAgencyName());
            formBean.setSelectListReqDto(selectListReqDto);
            
            msgBean = new MessageBean(MessageId.MI0007, Integer.toString(count));
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            } else {
                throw new ASSBusinessException(e.getCause());
            }
        }

        applicationLogger.log("Registered List Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
