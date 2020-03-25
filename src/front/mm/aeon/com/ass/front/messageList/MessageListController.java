/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.messageList;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mm.aeon.com.ass.base.dto.messageSearch.MessageSearchReqDto;
import mm.aeon.com.ass.base.dto.messageSearch.MessageSearchResDto;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class MessageListController extends AbstractController
        implements IControllerAccessor<MessageListFormBean, MessageListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public MessageListFormBean process(MessageListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(true);

        applicationLogger.log("Message list searching process stared.", LogLevel.INFO);
        MessageBean messageBean;

        MessageSearchReqDto reqDto = new MessageSearchReqDto();
        reqDto.setAgencyUserName(formBean.getSearchHeaderBean().getAgencyUserName());
        reqDto.setFromDateTime(convertDateType(formBean.getSearchHeaderBean().getFromDateTime()));
        reqDto.setToDateTime(convertDateType(formBean.getSearchHeaderBean().getToDateTime()));

        try {
            List<MessageSearchResDto> resDtoList =
                    (List<MessageSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<MessageListLineBean> lineBeanList = new ArrayList<MessageListLineBean>();

            for (MessageSearchResDto resDto : resDtoList) {
                MessageListLineBean lineBean = new MessageListLineBean();

                lineBean.setAgencyUserName(resDto.getAgencyUserName());
                lineBean.setMessage(resDto.getMessage());
                lineBean.setSendBy(resDto.getSendBy());
                lineBean.setSentTime(resDto.getSentTime());

                lineBeanList.add(lineBean);
            }

            formBean.setLineBeanList(lineBeanList);

            if (lineBeanList.size() == 0) {
                messageBean = new MessageBean(MessageId.MI0008);
            } else {
                messageBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            messageBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(messageBean);
            applicationLogger.log(messageBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Message history searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }
    
    private Timestamp convertDateType(Date utilDate) {
        if (null != utilDate) {
            Timestamp sqlDate = new Timestamp(utilDate.getTime());
            return sqlDate;
        }
        return null;
    }

}
