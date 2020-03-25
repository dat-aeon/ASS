/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.receivingFileList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.log.ASSLogger;
import mm.aeon.com.ass.base.dto.receivingFileList.ReceivingFileListReqDto;
import mm.aeon.com.ass.base.dto.receivingFileList.ReceivingFileListResDto;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.exception.DaoSqlException;
import mm.com.dat.presto.main.exception.PrestoDBException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class ReceivingFileListController extends AbstractController
        implements IControllerAccessor<ReceivingFileListFormBean, ReceivingFileListFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    private ASSLogger logger = new ASSLogger();

    @Override
    public ReceivingFileListFormBean process(ReceivingFileListFormBean formBean) {
        applicationLogger.log("Receiving File List Searching Process started.", LogLevel.INFO);

        formBean.getMessageContainer().clearAllMessages(true);

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Receiving File List]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        MessageBean msgBean;

        List<ReceivingFileListLineBean> lineBeans = new ArrayList<ReceivingFileListLineBean>();

        if (formBean.getSearchHeaderBean().getFromDate() != null
                && formBean.getSearchHeaderBean().getToDate() != null) {
            if (formBean.getSearchHeaderBean().getFromDate()
                    .compareTo(formBean.getSearchHeaderBean().getToDate()) > 0) {
                msgBean = new MessageBean(MessageId.ME1018);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                formBean.setLine(lineBeans);
                formBean.getSearchHeaderBean().setRecord("");
                return formBean;
            }
        }

        ReceivingFileListReqDto reqDto = new ReceivingFileListReqDto();
        reqDto.setFromDate(CommonUtil.changeDateToString(formBean.getSearchHeaderBean().getFromDate()));
        reqDto.setToDate(CommonUtil.changeDateToString(formBean.getSearchHeaderBean().getToDate()));
        reqDto.setStatus(formBean.getSearchHeaderBean().getStatus());
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        try {
            List<ReceivingFileListResDto> resUserList =
                    (List<ReceivingFileListResDto>) this.getDaoServiceInvoker().execute(reqDto);

            for (ReceivingFileListResDto resDto : resUserList) {
                ReceivingFileListLineBean lineBean = new ReceivingFileListLineBean();

                lineBean.setReceivedTime(format.format(resDto.getReceivedTime()));
                lineBean.setFileName(resDto.getFileName());

                switch (resDto.getStatus().toString()) {
                    case "3":
                        lineBean.setBackup("O");
                        lineBean.setPhoto("O");
                        lineBean.setDoing("X");
                        lineBean.setDone("X");
                        lineBean.setStatus("Received");
                        break;
                    case "4":
                        lineBean.setBackup("O");
                        lineBean.setPhoto("X");
                        lineBean.setDoing("O");
                        lineBean.setDone("X");
                        lineBean.setStatus("Registering");
                        break;
                    case "5":
                        lineBean.setBackup("O");
                        lineBean.setPhoto("X");
                        lineBean.setDoing("X");
                        lineBean.setDone("O");
                        lineBean.setStatus("Registered");
                        break;
                    case "6":
                        lineBean.setBackup("O");
                        lineBean.setPhoto("X");
                        lineBean.setDoing("X");
                        lineBean.setDone("X");
                        lineBean.setStatus("Not Found");
                        break;

                }

                lineBeans.add(lineBean);
            }
            formBean.setLine(lineBeans);

            if (lineBeans.size() == 0) {
                formBean.setShowExport(false);
            } else {
                formBean.setShowExport(true);
            }
            formBean.getMessageContainer().clearAllMessages();
            formBean.getSearchHeaderBean().setRecord(CommonUtil.showRecord(lineBeans.size()));
            applicationLogger.log("Receiving File List Searching Process finished.", LogLevel.INFO);
        } catch (PrestoDBException e) {
            if (e instanceof DaoSqlException) {
                logger.log(e.getCause().getMessage(), e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }
        }
        return formBean;
    }
}
