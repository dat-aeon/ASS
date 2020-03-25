/**************************************************************************
 * $Date : 2018-08-17$
 * $Author : Thar Pyae $
 * $Rev : 0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.receivedList;

import mm.aeon.com.ass.base.dto.receivedListCount.ReceivedListCountReqDto;
import mm.aeon.com.ass.base.dto.receivedListSelectList.ReceivedListSelectListReqDto;
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

public class ReceivedListController extends AbstractASSController
        implements IControllerAccessor<ReceivedListFormBean, ReceivedListFormBean> {

    private ASSLogger logger = new ASSLogger();
    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public ReceivedListFormBean process(ReceivedListFormBean formBean) {

        applicationLogger.log("Received list Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Received List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        ReceivedListCountReqDto reqDto = new ReceivedListCountReqDto();
        reqDto.setUserName(formBean.getReceivedListHeaderBean().getUserName());
        reqDto.setAgencyName(formBean.getReceivedListHeaderBean().getAgencyName());

        try {
            int count = (Integer) CommonUtil.getDaoServiceInvoker().execute(reqDto);
            formBean.setTotalCount(count);
            formBean.getReceivedListHeaderBean().setRecord(CommonUtil.showRecord(count));
            ReceivedListSelectListReqDto selectListReqDto = new ReceivedListSelectListReqDto();
            selectListReqDto.setUserName(formBean.getReceivedListHeaderBean().getUserName());
            selectListReqDto.setAgencyName(formBean.getReceivedListHeaderBean().getAgencyName());
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

        applicationLogger.log("Received List Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
