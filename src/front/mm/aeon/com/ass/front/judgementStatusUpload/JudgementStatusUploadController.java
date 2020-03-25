/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import com.opencsv.CSVReader;

import mm.aeon.com.ass.base.dto.judgementStatusSelectList.JudgementStatusSelectListReqDto;
import mm.aeon.com.ass.base.dto.judgementStatusSelectList.JudgementStatusSelectListResDto;
import mm.aeon.com.ass.base.service.judgementStatusUpdateService.JudgementStatusUpdateServiceReqBean;
import mm.aeon.com.ass.base.service.judgementStatusUpdateService.JudgementStatusUpdateServiceResBean;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.exception.InvalidScreenTransitionException;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.common.util.TransactionUtilty;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.main.utils.exception.PrestoRuntimeException;
import mm.com.dat.presto.utils.common.InputChecker;

public class JudgementStatusUploadController extends AbstractASSController
        implements IControllerAccessor<JudgementStatusUploadFormBean, JudgementStatusUploadFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";
    public static final int APPROVE_REPORT = 2;
    public static final int REJECT_REPORT = 3;
    public static final int CANCEL_REPORT = 4;

    @Override
    public JudgementStatusUploadFormBean process(JudgementStatusUploadFormBean formBean) {

        if (!(ASSCommon.ONE.equals(CommonUtil.getLoginUserInfo().getUserTypeName())
                || ASSCommon.ZERO.equals(CommonUtil.getLoginUserInfo().getUserTypeName()))) {
            logger.log("Invalid URL Access.[Judgement Status List]", new InvalidScreenTransitionException(),
                    LogLevel.ERROR);
            throw new InvalidScreenTransitionException();
        }

        applicationLogger.log("Judgement Status Upload Process started.", LogLevel.INFO);

        formBean.getMessageContainer().clearAllMessages(true);
        formBean.setLineBeanList(new ArrayList<JudgementStatusUploadSuccessListBean>());

        if (!isValidData(formBean)) {
            return formBean;
        }
        boolean isValidHeader = false;

        try {
            MessageBean msgBean;

            // boolean isUploadFile = false;

            InputStream inputStream;
            inputStream = formBean.getHeaderBean().getFile().getInputstream();
            File temp = streamTofile(inputStream);

            // SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            CSVReader reader = new CSVReader(new FileReader(temp), ASSCommon.COMMA_CHAR);
            String[] line;
            String pattern = "[0-9]+";
            String termPattern = "(6|9|12|18|24)";
            int rowIndex = 0;
            boolean isCheckHeader = false;

            int updCount = 0;
            ArrayList<JudgementStatusUploadSuccessListBean> lineBeanList =
                    new ArrayList<JudgementStatusUploadSuccessListBean>();

            String serviceStatus = null;
            JudgementStatusUpdateServiceReqBean updateServiceReqBean;
            String financeTerm = null;

            while ((line = reader.readNext()) != null) {
                rowIndex++;
                boolean isData = false;

                if (line.length > 0) {

                    String applicationNo = null;
                    String judgementDate = null;
                    String customerName = null;
                    String agreementNo = null;
                    String financeAmount = null;

                    // APPROVE APPLICATION REPORT
                    if (APPROVE_REPORT == formBean.getHeaderBean().getStatus()) {
                        if (rowIndex == 1) {
                            if (!line[0].contains(ASSCommon.JD_DAILY_APPROVED_APPLICATION_REPORT)) {
                                msgBean = new MessageBean(MessageId.ME1014, ASSCommon.FILE);
                                msgBean.setMessageType(MessageType.ERROR);
                                formBean.getMessageContainer().addMessage(msgBean);
                                return formBean;

                            }

                        } else if (rowIndex == 4 && line.length > 12 && (line[1].equalsIgnoreCase(ASSCommon.HEADER_NO))
                                && isCheckHeader == false) {
                            isValidHeader = validApproveHeader(line, CommonUtil.getApprovedHeaderList());
                            if (isValidHeader == false) {
                                msgBean = new MessageBean(MessageId.ME1004,
                                        DisplayItemBean.getDisplayItemName(DisplayItem.FILE));
                                msgBean.setMessageType(MessageType.ERROR);
                                formBean.getMessageContainer().addMessage(msgBean);

                                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                                return formBean;
                            }
                            isCheckHeader = true;

                        } else {
                            if (line[0].matches(pattern)) {
                                applicationNo = line[5];
                                judgementDate = line[1];
                                customerName = line[3];
                                agreementNo = line[6];
                                financeAmount = line[8];
                                isData = true;

                            } else if (line[1].matches(pattern)) {
                                applicationNo = line[7];
                                judgementDate = line[2];
                                customerName = line[4];
                                agreementNo = line[8];
                                financeAmount = line[10];
                                isData = true;

                            } else if (line[9].matches(termPattern)) {
                                financeTerm = line[9];

                            } else if (line[7].matches(termPattern)) {
                                financeTerm = line[7];
                            }
                        }
                    }
                    // REJECT APPLICATION REPORT
                    else if (REJECT_REPORT == formBean.getHeaderBean().getStatus()) {
                        if (rowIndex == 7) {
                            if (!line[0].equalsIgnoreCase(ASSCommon.JD_DAILY_REJECTED_APPLICATION_REPORT)) {

                                msgBean = new MessageBean(MessageId.ME1014, ASSCommon.FILE);
                                msgBean.setMessageType(MessageType.ERROR);
                                formBean.getMessageContainer().addMessage(msgBean);
                                return formBean;
                            }

                        } else if (rowIndex == 9 && line.length > 19
                                && (line[0].equalsIgnoreCase(ASSCommon.HEADER_REJECT_NO)) && isCheckHeader == false) {
                            isValidHeader = validRejectHeader(line, CommonUtil.getRejectedHeaderList());
                            if (isValidHeader == false) {
                                msgBean = new MessageBean(MessageId.ME1004,
                                        DisplayItemBean.getDisplayItemName(DisplayItem.FILE));
                                msgBean.setMessageType(MessageType.ERROR);
                                formBean.getMessageContainer().addMessage(msgBean);
                                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                                return formBean;
                            }
                            isCheckHeader = true;

                        } else {

                            if (line[0].matches(pattern)) {
                                // applicationNo = line[9].substring(3, 9);
                                applicationNo = line[9];
                                customerName = line[6];
                                isData = true;
                            }

                        }
                    }

                    // CANCEL APPLICATION REPORT
                    else if (CANCEL_REPORT == formBean.getHeaderBean().getStatus()) {
                        if (rowIndex == 7) {
                            if (!line[0].equalsIgnoreCase(ASSCommon.JD_DAILY_CANCELLED_APPLICATION_REPORT)) {

                                msgBean = new MessageBean(MessageId.ME1014, ASSCommon.FILE);
                                msgBean.setMessageType(MessageType.ERROR);
                                formBean.getMessageContainer().addMessage(msgBean);
                                return formBean;
                            }

                        } else if (rowIndex == 9 && line.length > 16 && (line[0].equalsIgnoreCase(ASSCommon.HEADER_NO))
                                && isCheckHeader == false) {
                            isValidHeader = validCancelHeader(line, CommonUtil.getCancelledHeaderList());
                            if (isValidHeader == false) {
                                msgBean = new MessageBean(MessageId.ME1004,
                                        DisplayItemBean.getDisplayItemName(DisplayItem.FILE));
                                msgBean.setMessageType(MessageType.ERROR);
                                formBean.getMessageContainer().addMessage(msgBean);
                                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                                return formBean;
                            }
                            isCheckHeader = true;

                        } else {
                            if (line[0].matches(pattern)) {
                                // applicationNo = line[8].substring(3, 9);
                                applicationNo = line[8];
                                customerName = line[5];
                                isData = true;
                            }
                        }
                    }

                    // Check it is data line or not
                    if (isData) {

                        JudgementStatusSelectListReqDto referReqDto = new JudgementStatusSelectListReqDto();
                        String[] splitApplicationNo = applicationNo.split("-");
                        applicationNo = splitApplicationNo[0];
                        referReqDto.setApplicationNo(applicationNo);
                        referReqDto.setCustomerName(customerName);
                        referReqDto.setIsUpload(true);

                        List<JudgementStatusSelectListResDto> resDtoList = (List<JudgementStatusSelectListResDto>) this
                                .getDaoServiceInvoker().execute(referReqDto);

                        // if applicationNo exists in application_info table, update data
                        if (resDtoList.size() > 0) {

                            // if record has more than one row.
                            if (resDtoList.size() > 1) {

                                formBean.getMessageContainer().clearAllMessages(true);
                                msgBean = new MessageBean(MessageId.ME1024, resDtoList.get(0).getApplicationNo(),
                                        Integer.toString(resDtoList.size()));
                                msgBean.setMessageType(MessageType.ERROR);
                                formBean.getMessageContainer().addMessage(msgBean);
                                applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);

                                TransactionUtilty.rollBackTransaction();
                                if (lineBeanList.size() > 0) {
                                    lineBeanList.clear();
                                }
                                formBean.setDateTitle(DisplayItemBean.getDisplayItemName(DisplayItem.APPLICATION_DATE));

                                for (JudgementStatusSelectListResDto resDto : resDtoList) {
                                    JudgementStatusUploadSuccessListBean lineBean =
                                            new JudgementStatusUploadSuccessListBean();
                                    lineBean.setJudgementDate(
                                            CommonUtil.getChangeTimestampToString(resDto.getFirstApplyDate()));
                                    lineBean.setAgencyName(resDto.getAgencyName());
                                    if (resDtoList.get(0).getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                                        lineBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
                                    } else {
                                        lineBean.setOutletName(resDto.getOutletName());
                                    }

                                    lineBean.setUserName(resDto.getCustomerName());
                                    lineBean.setApplicationNo(resDto.getApplicationNo());
                                    lineBean.setNrc(resDto.getNrc());

                                    lineBeanList.add(lineBean);
                                }
                                formBean.setLineBeanList(lineBeanList);
                                reader.close();
                                return formBean;

                            } else {

                                updateServiceReqBean = new JudgementStatusUpdateServiceReqBean();
                                updateServiceReqBean.setStatus(formBean.getHeaderBean().getStatus());
                                if (judgementDate != null) {
                                    updateServiceReqBean
                                            .setJudgementDate(CommonUtil.getChangeDMYStringToTimeStamp(judgementDate));
                                }
                                updateServiceReqBean.setApplicationNo(applicationNo);
                                updateServiceReqBean.setCustomerName(customerName);
                                updateServiceReqBean.setAgreementNo(agreementNo);

                                if (APPROVE_REPORT == formBean.getHeaderBean().getStatus()) {

                                    if (!InputChecker.isBlankOrNull(financeTerm)) {
                                        updateServiceReqBean.setFinanceTerm(Integer.parseInt(financeTerm));
                                    }
                                    if (!InputChecker.isBlankOrNull(financeAmount)) {
                                        updateServiceReqBean.setFinanceAmount(Double.parseDouble(financeAmount));
                                    }
                                } else {
                                    updateServiceReqBean.setFinanceTerm(null);
                                    updateServiceReqBean.setFinanceAmount(null);
                                }
                                updateServiceReqBean.setUpdatedBy(CommonUtil.getLoginUserName());

                                this.getServiceInvoker().addRequest(updateServiceReqBean);
                                ResponseMessage responseMessage = this.getServiceInvoker().invoke();
                                JudgementStatusUpdateServiceResBean resBean = responseMessage.getMessageBean(0);
                                serviceStatus = resBean.getServiceStatus();

                                if (ServiceStatusCode.OK.equals(serviceStatus)) {
                                    updCount++;
                                    JudgementStatusUploadSuccessListBean lineBean =
                                            new JudgementStatusUploadSuccessListBean();
                                    if (APPROVE_REPORT == formBean.getHeaderBean().getStatus()) {
                                        formBean.setDateTitle(
                                                DisplayItemBean.getDisplayItemName(DisplayItem.JUDGEMENT_DATE));
                                        lineBean.setJudgementDate(judgementDate);

                                    } else {
                                        formBean.setDateTitle(
                                                DisplayItemBean.getDisplayItemName(DisplayItem.APPLICATION_DATE));
                                        lineBean.setJudgementDate(CommonUtil
                                                .getChangeTimestampToString(resDtoList.get(0).getFirstApplyDate()));
                                    }

                                    if (resDtoList.get(0).getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                                        lineBean.setOutletName(ASSCommon.OUT_HEAD_OFFICE);
                                    } else {
                                        lineBean.setOutletName(resDtoList.get(0).getOutletName());
                                    }

                                    lineBean.setAgencyName(resDtoList.get(0).getAgencyName());
                                    lineBean.setUserName(resDtoList.get(0).getCustomerName());
                                    lineBean.setApplicationNo(applicationNo);
                                    lineBean.setNrc(resDtoList.get(0).getNrc());

                                    if (updateServiceReqBean.getStatus() == APPROVE_REPORT) {
                                        lineBean.setStatus(ASSCommon.STATUS_APPROVE);
                                        lineBean.setAgreementNo(agreementNo);
                                        lineBean.setFinanceTerm(financeTerm);
                                        lineBean.setFinanceAmount(financeAmount);

                                    } else if (updateServiceReqBean.getStatus() == REJECT_REPORT) {
                                        lineBean.setStatus(ASSCommon.STATUS_REJECT);

                                    } else if (updateServiceReqBean.getStatus() == CANCEL_REPORT) {
                                        lineBean.setStatus(ASSCommon.STATUS_CANCEL);
                                    }
                                    lineBeanList.add(lineBean);
                                    financeTerm = null;

                                    formBean.setLineBeanList(lineBeanList);

                                    formBean.getMessageContainer().clearAllMessages(true);
                                    msgBean = new MessageBean(MessageId.MI0011, Integer.toString(updCount));
                                    msgBean.setMessageType(MessageType.INFO);
                                    formBean.getMessageContainer().addMessage(msgBean);
                                    applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
                                }

                                else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
                                    setErrorMessage(serviceStatus, Integer.toString(rowIndex), formBean);
                                    return formBean;
                                }
                            }
                        }
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            logger.log(e.getCause().getMessage(), e, LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getCause());

        } catch (Exception e) {
            if (isValidHeader == false) {
                MessageBean msgBean;
                msgBean = new MessageBean(MessageId.ME1014, ASSCommon.FILE);
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);
                return formBean;
            }
            logger.log(e.getCause().getMessage(), e, LogLevel.ERROR);
            throw new PrestoRuntimeException(e.getCause());
        }

        return formBean;

    }

    private void setErrorMessage(String serviceStatus, String no, JudgementStatusUploadFormBean formBean) {
        // MessageBean msgBean;
        if (serviceStatus.equals(ServiceStatusCode.SQL_ERROR)) {
            TransactionUtilty.rollBackTransaction();
            throw new BaseException();
        }
    }

    private boolean isValidData(JudgementStatusUploadFormBean formBean) {
        boolean isValid = true;
        UploadedFile file = formBean.getHeaderBean().getFile();

        if (file == null) {
            MessageBean msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.FILE));
            msgBean.addColumnId(DisplayItem.CSV_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        } else if (!FilenameUtils.getExtension(file.getFileName()).equalsIgnoreCase(ASSCommon.CSV)) {
            MessageBean msgBean = new MessageBean(MessageId.ME1020, ASSCommon.CSV);
            msgBean.addColumnId(DisplayItem.CSV_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        } else if (file.getSize() > CommonUtil.getCSVFileUploadSize()) {
            MessageBean msgBean = new MessageBean(MessageId.ME1023);
            msgBean.addColumnId(DisplayItem.CSV_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }

    public static File streamTofile(InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    public boolean validApproveHeader(String[] line, List<String> headerNameList) {
        boolean valid = false;
        for (int i = 0; i < headerNameList.size(); i++) {
            if (i < 4) {
                if (headerNameList.get(i).equalsIgnoreCase(line[i + 1])) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            } else if (i > 4) {
                if (headerNameList.get(i - 1).equalsIgnoreCase(line[i + 1])) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    public boolean validRejectHeader(String[] line, List<String> headerNameList) {
        boolean valid = false;
        for (int i = 0; i < headerNameList.size(); i++) {
            if (i < 2) {
                if (headerNameList.get(i).equalsIgnoreCase(line[i])) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            } else if (i > 4) {
                if (headerNameList.get(i - 3).equalsIgnoreCase(line[i])) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    public boolean validCancelHeader(String[] line, List<String> headerNameList) {
        boolean valid = false;
        for (int i = 0; i < headerNameList.size(); i++) {
            if (i < 2) {
                if (headerNameList.get(i).equalsIgnoreCase(line[i])) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            } else if (i > 3) {
                if (headerNameList.get(i - 2).equalsIgnoreCase(line[i])) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

}
