/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.apkUpload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import mm.aeon.com.ass.base.service.APKUploadRegisterService.APKUploadRegisterServiceReqBean;
import mm.aeon.com.ass.front.common.abstractController.AbstractASSController;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.log.ASSLogger;
import mm.com.dat.presto.main.common.service.bean.AbstractServiceResBean;
import mm.com.dat.presto.main.common.service.bean.ResponseMessage;
import mm.com.dat.presto.main.common.service.bean.ServiceStatusCode;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.exception.BaseException;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;
import mm.com.dat.presto.utils.common.InputChecker;

public class APKUploadUpdateController extends AbstractASSController
        implements IControllerAccessor<APKUploadFormBean, APKUploadFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();
    private ASSLogger logger = new ASSLogger();

    @Override
    public APKUploadFormBean process(APKUploadFormBean formBean) {

        applicationLogger.log("APK Upload Update Process started.", LogLevel.INFO);

        formBean.getMessageContainer().clearAllMessages(true);

        if (!isValidData(formBean)) {
            return formBean;
        }

        Object context = FacesContext.getCurrentInstance().getExternalContext().getContext();
        String systemPath = ((ServletContext) context).getRealPath(ASSCommon.BACK_SLASH);
        String[] filePath = systemPath.split(CommonUtil.getFilePath());
        applicationLogger.log("apk file path " + systemPath, LogLevel.INFO);

        systemPath = filePath[0] + ASSCommon.APK + ASSCommon.BACK_SLASH + CommonUtil.getPlainCurrentDate()
                + ASSCommon.BACK_SLASH + CommonUtil.getPlainCurrentTimeStamp();

        File file = new File(systemPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        systemPath += ASSCommon.BACK_SLASH + formBean.getHeaderBean().getFile().getFileName();
        File apkFile = new File(systemPath);
        byte[] content = formBean.getHeaderBean().getFile().getContents();

        try {
            FileOutputStream outputStream = new FileOutputStream(apkFile);
            IOUtils.write(content, outputStream);
            outputStream.flush();
            outputStream.close();

            MessageBean msgBean = new MessageBean(MessageId.MI0012, formBean.getHeaderBean().getFile().getFileName());
            msgBean.setMessageType(MessageType.INFO);
            formBean.getMessageContainer().addMessage(msgBean);
            formBean.setUpload(false);

            String serviceStatus;

            APKUploadRegisterServiceReqBean insertServiceReqBean = new APKUploadRegisterServiceReqBean();
            insertServiceReqBean.setVersion(formBean.getHeaderBean().getVersion());
            insertServiceReqBean.setFileName(formBean.getHeaderBean().getFile().getFileName());
            insertServiceReqBean.setFilePath(systemPath);
            insertServiceReqBean.setDescription(formBean.getHeaderBean().getDescription());
            insertServiceReqBean.setCreatedBy(CommonUtil.getLoginUserName());

            this.getServiceInvoker().addRequest(insertServiceReqBean);
            ResponseMessage responseMessage = this.getServiceInvoker().invoke();
            AbstractServiceResBean resBean = responseMessage.getMessageBean(0);
            serviceStatus = resBean.getServiceStatus();

            if (ServiceStatusCode.OK.equals(serviceStatus)) {
                applicationLogger.log("APK Upload Register Process finished.", LogLevel.INFO);
                msgBean = new MessageBean(MessageId.MI0012, formBean.getHeaderBean().getFile().getFileName());
                msgBean.setMessageType(MessageType.INFO);
                formBean.getMessageContainer().addMessage(msgBean);
                formBean.setHeaderBean(new APKUploadHeaderBean());

            } else if (ServiceStatusCode.RECORD_DUPLICATED_ERROR.equals(serviceStatus)) {
                msgBean = new MessageBean(MessageId.ME1021, formBean.getHeaderBean().getVersion());
                msgBean.setMessageType(MessageType.ERROR);
                formBean.getMessageContainer().addMessage(msgBean);

                applicationLogger.log(msgBean.getMessage(), LogLevel.ERROR);
                applicationLogger.log("Registerd APK version already exist.", LogLevel.ERROR);

            } else if (ServiceStatusCode.SQL_ERROR.equals(serviceStatus)) {
                throw new BaseException();
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                logger.log("File Not Found Exception", e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());

            } else if (e instanceof AccessDeniedException) {
                logger.log("File Access Exception", e.getCause(), LogLevel.ERROR);
                throw new BaseException(e.getCause());
            }

            throw new BaseException(e.getCause());
        }

        return formBean;

    }

    private boolean isValidData(APKUploadFormBean formBean) {
        boolean isValid = true;
        UploadedFile file = formBean.getHeaderBean().getFile();

        if (file == null) {
            MessageBean msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.APK_FILE));
            msgBean.addColumnId(DisplayItem.APK_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        } else if (!FilenameUtils.getExtension(file.getFileName()).equalsIgnoreCase(ASSCommon.APK)) {
            MessageBean msgBean = new MessageBean(MessageId.ME1020, ASSCommon.APK);
            msgBean.addColumnId(DisplayItem.APK_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;

        } else if (file.getSize() > CommonUtil.getFileUploadSize()) {
            MessageBean msgBean = new MessageBean(MessageId.ME1023);
            msgBean.addColumnId(DisplayItem.APK_FILE);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        if (InputChecker.isBlankOrNull(formBean.getHeaderBean().getVersion())) {
            MessageBean msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.VERSION));
            msgBean.addColumnId(DisplayItem.VERSION);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }
        if (InputChecker.isBlankOrNull(formBean.getHeaderBean().getDescription())) {
            MessageBean msgBean =
                    new MessageBean(MessageId.ME0003, DisplayItemBean.getDisplayItemName(DisplayItem.DESCRIPTION));
            msgBean.addColumnId(DisplayItem.DESCRIPTION);
            msgBean.setMessageType(MessageType.ERROR);
            formBean.getMessageContainer().addMessage(msgBean);
            isValid = false;
        }

        return isValid;
    }
}
