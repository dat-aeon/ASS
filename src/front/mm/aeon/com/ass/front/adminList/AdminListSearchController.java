/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.adminList;

import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.base.dto.adminSearch.AdminSearchReqDto;
import mm.aeon.com.ass.base.dto.adminSearch.AdminSearchResDto;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
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
import mm.com.dat.presto.utils.common.InputChecker;

public class AdminListSearchController extends AbstractController
        implements IControllerAccessor<AdminListFormBean, AdminListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public AdminListFormBean process(AdminListFormBean formBean) {

        formBean.getMessageContainer().clearAllMessages(!formBean.getDoReload());

        if (!ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())) {
            logger.log("Invalid URL Access.[Admin List]", new InvalidScreenTransitionException(), LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Admin Searching Process started.", LogLevel.INFO);

        MessageBean msgBean;

        AdminSearchReqDto reqDto = new AdminSearchReqDto();
        if (!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getAdminLoginId())) {
            reqDto.setAdminLoginId(formBean.getSearchHeaderBean().getAdminLoginId().toLowerCase());
        }
        if(!InputChecker.isBlankOrNull(formBean.getSearchHeaderBean().getAdminName())) {
            reqDto.setAdminName(formBean.getSearchHeaderBean().getAdminName().toLowerCase());
        }
        int tmp = (null == formBean.getSearchHeaderBean().getValidStatus()) ? 2
                : Integer.valueOf(formBean.getSearchHeaderBean().getValidStatus());
        reqDto.setValidStatus(tmp);
        reqDto.setRole(ASSCommon.ADMIN_ROLE);

        try {
            List<AdminSearchResDto> resAdminList =
                    (List<AdminSearchResDto>) this.getDaoServiceInvoker().execute(reqDto);
            List<AdminListLineBean> lineBeanList = new ArrayList<AdminListLineBean>();

            for (AdminSearchResDto resdto : resAdminList) {
                AdminListLineBean lineBean = new AdminListLineBean();

                lineBean.setAdminId(resdto.getAdminId());
                lineBean.setAdminLoginId(resdto.getAdminLoginId());
                lineBean.setAdminName(resdto.getAdminName());
                lineBean.setValidStatus(resdto.getValidStatus());
                lineBean.setUpdatedDate(resdto.getUpdatedDate());

                lineBeanList.add(lineBean);
            }

            formBean.setLineBeans(lineBeanList);

            if (lineBeanList.size() == 0) {
                msgBean = new MessageBean(MessageId.MI0008);
            } else {
                msgBean = new MessageBean(MessageId.MI0007, String.valueOf(lineBeanList.size()));
            }
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Admin searching finished.", LogLevel.INFO);

        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }

        return formBean;
    }

}
