/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserManagement;

import mm.aeon.com.ass.base.dto.agencyUserRefer.AgencyUserReferReqDto;
import mm.aeon.com.ass.base.dto.agencyUserRefer.AgencyUserReferResDto;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
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

public class AgencyUserUpdateInitController extends AbstractASSController
        implements IControllerAccessor<AgencyUserManagementFormBean, AgencyUserManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public AgencyUserManagementFormBean process(AgencyUserManagementFormBean formBean) {

        applicationLogger.log("Agency User Update Initialization Process started.", LogLevel.INFO);
        MessageBean msgBean;

        formBean.getMessageContainer().clearAllMessages(true);

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Agency User Update]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        AgencyUserRegisterHeaderBean headerBean = new AgencyUserRegisterHeaderBean();

        try {
            AgencyUserReferReqDto reqDto = new AgencyUserReferReqDto();
            reqDto.setId(formBean.getUpdateParam().getId());
            AgencyUserReferResDto resDto = (AgencyUserReferResDto) CommonUtil.getDaoServiceInvoker().execute(reqDto);

            if (resDto == null) {
                formBean.setIsInitError(true);
                msgBean = new MessageBean(MessageId.ME1009);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                return formBean;

            } else {
                headerBean.setId(resDto.getId());
                headerBean.setLoginID(resDto.getLoginID());
                headerBean.setUserName(resDto.getName());
                headerBean.setAgencyNameList(super.getAgencyNameList(false));
                headerBean.setAgencyName(Integer.toString(resDto.getAgencyId()));
                headerBean.setAllOutletNameList(super.getAllOutletNameList(false));
                headerBean.setOutletNameList(super.getOutletNameListOnAgencyId(headerBean));
                headerBean.setOutletName(super.getOutletName(Integer.toString(resDto.getAgencyOutletId())));
                headerBean.setStartDate(CommonUtil.getChangeTimestampToString(resDto.getStartDate()));
                headerBean.setEndDate(CommonUtil.getChangeTimestampToString(resDto.getEndDate()));
                headerBean.setPhoneNumber(resDto.getPhoneNo());
                headerBean.setEmail(resDto.getEmail());
                headerBean.setAddress(resDto.getAddress());
                headerBean.setRemark(resDto.getRemark());
                headerBean.setUpdatedTime(resDto.getUpdatedTime());
                headerBean.setPageTitle(ASSCommon.AGENCY_USER_UPDATE);
            }
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

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        applicationLogger.log("Agency User Update Initialization Process finished.", LogLevel.INFO);
        return formBean;
    }

}
