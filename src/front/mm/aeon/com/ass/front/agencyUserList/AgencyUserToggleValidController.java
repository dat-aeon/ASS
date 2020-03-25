/**************************************************************************
 * $Date: 2018-08-10$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserList;

import mm.aeon.com.ass.base.dto.agencyUserRefer.AgencyUserReferReqDto;
import mm.aeon.com.ass.base.dto.agencyUserRefer.AgencyUserReferResDto;
import mm.aeon.com.ass.base.service.agencyUserUpdateService.AgencyUserUpdateServiceReqBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class AgencyUserToggleValidController extends AbstractController
        implements IControllerAccessor<AgencyUserListFormBean, AgencyUserListFormBean> {

    private ASSLogger logger = new ASSLogger();

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public AgencyUserListFormBean process(AgencyUserListFormBean formBean) {

        applicationLogger.log("Agency User Valid Flag Process started.", LogLevel.INFO);

        MessageBean msgBean;
        formBean.getMessageContainer().clearAllMessages(true);
        AgencyUserUpdateServiceReqBean updateServiceReqBean = new AgencyUserUpdateServiceReqBean();

        try {
            AgencyUserReferReqDto reqDto = new AgencyUserReferReqDto();
            reqDto.setId(formBean.getLineBean().getId());
            AgencyUserReferResDto resDto = (AgencyUserReferResDto) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            if (resDto == null) {
                msgBean = new MessageBean(MessageId.ME1009);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                return formBean;

            } else {
                updateServiceReqBean.setId(formBean.getLineBean().getId());

                String valid = Integer.toString(resDto.getIsValid());
                if (ASSCommon.ZERO.equals(valid)) {
                    updateServiceReqBean.setIsValid(Integer.parseInt(ASSCommon.ONE));
                } else {
                    updateServiceReqBean.setIsValid(Integer.parseInt(ASSCommon.ZERO));
                }
                updateServiceReqBean.setDisabledBy(CommonUtil.getLoginUserName());
                updateServiceReqBean.setUpdatedTime(resDto.getUpdatedTime());
            }

            this.getServiceInvoker().addRequest(updateServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);

            if (ServiceStatusCode.OK.equals(resBean.getServiceStatus())) {
                msgBean = new MessageBean(MessageId.MI0002);
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                applicationLogger.log("Agency User Valid Flag Process finished.", LogLevel.INFO);

            } else if (ServiceStatusCode.PHYSICAL_RECORD_LOCKED_ERROR.equals(resBean.getServiceStatus())) {
                msgBean = new MessageBean(MessageId.ME1010);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Changed agency user data is locked.", LogLevel.ERROR);

            } else if (ServiceStatusCode.SQL_ERROR.equals(resBean.getServiceStatus())) {
                throw new BaseException();
            }

        } catch (

        PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;

    }

}
