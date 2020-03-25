/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletManagement;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.primefaces.model.UploadedFile;

import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.DisplayItem;
import mm.aeon.com.ass.front.common.constants.MessageId;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.DisplayItemBean;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.com.dat.presto.main.core.front.controller.AbstractController;
import mm.com.dat.presto.main.core.front.controller.IControllerAccessor;
import mm.com.dat.presto.main.front.message.MessageBean;
import mm.com.dat.presto.main.front.message.MessageType;
import mm.com.dat.presto.main.log.ApplicationLogger;
import mm.com.dat.presto.main.log.LogLevel;

public class OutletImageUploadController extends AbstractController
        implements IControllerAccessor<OutletManagementFormBean, OutletManagementFormBean> {

    private ApplicationLogger applicationLogger = new ApplicationLogger();

    @Override
    public OutletManagementFormBean process(OutletManagementFormBean outletManagementFormBean) {
        MessageBean msgBean;

        outletManagementFormBean.getMessageContainer().clearAllMessages(true);
        applicationLogger.log("Outlet image upload process started.", LogLevel.INFO);

        if (!checkImageDimension(outletManagementFormBean)) {
            msgBean = new MessageBean(MessageId.ME1026, DisplayItemBean.getDisplayItemName(DisplayItem.OUTLET_IMAGE));
            msgBean.addColumnId(DisplayItem.OUTLET_IMAGE);
            msgBean.setMessageType(MessageType.ERROR);
            outletManagementFormBean.getMessageContainer().addMessage(msgBean);
            return outletManagementFormBean;
        }

        try {
            UploadedFile newsInfoImageUploadFile = outletManagementFormBean.getOutletImageBean().getOutletImage();

            String fileName = CommonUtil.formatByPattern(CommonUtil.getCurrentTime(), "yyyyMMddhhmmssSSS") + ".png";
            String temp = "/temp";
            String outletImageFilePath = CommonUtil.getOutletImageFolder();
            String tempOutletImageFilePath = temp + outletImageFilePath + fileName;

            applicationLogger.log("Image file path " + tempOutletImageFilePath, LogLevel.INFO);

            applicationLogger.log("Outlet image upload process started." + tempOutletImageFilePath, LogLevel.INFO);

            applicationLogger.log("Created file." + tempOutletImageFilePath, LogLevel.INFO);

            FileHandler.createFile(new File(FileHandler.getSystemPath() + tempOutletImageFilePath),
                    newsInfoImageUploadFile.getContents());

            outletManagementFormBean.setOldUploadImageFilePath(
                    outletManagementFormBean.getManagementHeaderBean().getUploadedImageFilePath());

            outletManagementFormBean.setTempUploadImageFilePath(tempOutletImageFilePath);
            outletManagementFormBean.setUploadImageFileName(fileName);
            outletManagementFormBean.getManagementHeaderBean().setImagePath(fileName);
            outletManagementFormBean.getManagementHeaderBean().setUploadedImageFilePath(fileName);

            msgBean = new MessageBean(MessageId.MI0012, DisplayItem.OUTLET_IMAGE);
            msgBean.setMessageType(MessageType.INFO);
            outletManagementFormBean.getMessageContainer().addMessage(msgBean);
            applicationLogger.log(msgBean.getMessage(), LogLevel.INFO);
            applicationLogger.log("Outlet image upload process finished.", LogLevel.INFO);

        } catch (IOException e) {
            e.printStackTrace();
            applicationLogger.log("ERROR------" + e.getMessage(), LogLevel.INFO);
        }
        return outletManagementFormBean;
    }

    public static boolean checkImageDimension(OutletManagementFormBean outletManagementFormBean) {
        boolean isValid = true;
        MessageBean msgBean;
        byte[] image = outletManagementFormBean.getOutletImageBean().getOutletImage().getContents();
        try {
            BufferedImage bimage;
            bimage = ImageIO.read(new ByteArrayInputStream(image));
            int width = bimage.getWidth();
            int height = bimage.getHeight();
            if (width != ASSCommon.OUTLETIMAGEWIDTH | height != ASSCommon.OUTLETIMAGEHEIGHT) {
                isValid = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValid;
    }

}
