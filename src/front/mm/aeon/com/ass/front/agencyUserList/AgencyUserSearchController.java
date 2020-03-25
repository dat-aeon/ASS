/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserList;

import mm.aeon.com.ass.base.dto.agencyUserCount.AgencyUserListCountReqDto;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserSearchController extends AbstractASSController
        implements IControllerAccessor<AgencyUserListFormBean, AgencyUserListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyUserListFormBean process(AgencyUserListFormBean formBean) {

        applicationLogger.log("Check Status Process started.", LogLevel.INFO);

        if (formBean.getUpdateParam() != null && formBean.getUpdateParam().getIsUpdate()) {
            formBean.getMessageContainer().clearAllMessages(false);

        } else if (formBean.getIsUpdate()) {
            formBean.getMessageContainer().clearAllMessages(false);
            
        } else {
            formBean.getMessageContainer().clearAllMessages(true);
        }

        AgencyUserListCountReqDto reqDto = new AgencyUserListCountReqDto();
        reqDto.setUserName(formBean.getAgencyUserListHeaderBean().getUserName());
        reqDto.setAgencyName(formBean.getAgencyUserListHeaderBean().getAgencyName());
        reqDto.setOutletName(formBean.getAgencyUserListHeaderBean().getOutletName());

        int count = super.getAgencyUserCount(reqDto);
        formBean.getAgencyUserListHeaderBean().setCount(count);

        MessageBean msgBean = new MessageBean(MessageId.MI0007, Integer.toString(count));
        msgBean.setMessageType(MessageType.INFO);
        formBean.getMessageContainer().addMessage(msgBean);
        applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

        return formBean;

    }

}
