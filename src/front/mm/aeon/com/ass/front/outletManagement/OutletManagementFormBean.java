/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletManagement;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

import org.drools.core.util.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.event.FileUploadEvent;

import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.com.dat.presto.main.core.authenticate.LoginInfo;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("outletManagementFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class OutletManagementFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1583457870206052674L;

    @In(create = true)
    private LoginInfo loginInfo;

    private boolean init = true;

    private ArrayList<SelectItem> agencySelectItemList;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @In(required = false, value = "outletUpdateParam")
    @Out(required = false, value = "outletUpdateParam")
    private OutletManagementHeaderBean managementHeaderBean;

    private OutletManagementImageBean outletImageBean;

    private String tempUploadImageFilePath;

    private String oldUploadImageFilePath;

    private String uploadImageFileName;

    private boolean isHeadOffice;

    @Begin(join = true)
    @Action
    public String init() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.SEARCH;
        }
        this.init = false;
        this.doReload = new Boolean(false);
        this.getMessageContainer().clearAllMessages(true);
        if (null == this.managementHeaderBean) {
            this.managementHeaderBean = new OutletManagementHeaderBean();
            isHeadOffice = false;

        } else {
            if (!StringUtils.isEmpty(managementHeaderBean.getUploadedImageFilePath())) {
                tempUploadImageFilePath =
                        "/temp" + CommonUtil.getOutletImageFolder() + managementHeaderBean.getUploadedImageFilePath();
            }
            if (this.managementHeaderBean.getOutletName().contains(ASSCommon.OUT_HEAD_OFFICE)) {
                isHeadOffice = true;
            } else {
                isHeadOffice = false;
            }
        }

        return LinkTarget.INIT;
    }

    @Action
    public String register() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }

        this.doReload = new Boolean(true);
        this.managementHeaderBean = new OutletManagementHeaderBean();
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        return LinkTarget.OK;
    }

    @Action
    public String importImage() {
        if (getMessageContainer().checkMessage(MessageType.INFO)) {
            outletImageBean = new OutletManagementImageBean();
            return LinkTarget.OK;
        } else {
            return LinkTarget.ERROR;
        }
    }

    public void uploadImage(FileUploadEvent event) {
        if (outletImageBean == null) {
            outletImageBean = new OutletManagementImageBean();
        }
        if (event != null) {
            outletImageBean.setOutletImage(event.getFile());
        }
    }

    @Action
    public String update() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            return LinkTarget.ERROR;
        }
        if (!StringUtils.isEmpty(oldUploadImageFilePath)) {
            String oldUploadImageFolderPath = CommonUtil.getUploadImageBaseFilePath()
                    + CommonUtil.getOutletImageFolder() + oldUploadImageFilePath;
            try {
                System.gc();// Added this part
                Thread.sleep(1000);// This part gives the Bufferedreaders and the InputStreams time to close
                                   // Completely
                FileHandler.forceDelete(oldUploadImageFolderPath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        init = true;
        this.doReload = new Boolean(true);
        this.managementHeaderBean = null;
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        this.agencySelectItemList = null;
        this.isHeadOffice = false;
        return LinkTarget.SEARCH;
    }

    public String back() {
        this.getMessageContainer().clearAllMessages(true);
        this.init = true;
        this.managementHeaderBean = null;
        this.agencySelectItemList = null;
        tempUploadImageFilePath = null;
        oldUploadImageFilePath = null;
        uploadImageFileName = null;
        return LinkTarget.BACK;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public ArrayList<SelectItem> getAgencySelectItemList() {
        return agencySelectItemList;
    }

    public void setAgencySelectItemList(ArrayList<SelectItem> agencySelectItemList) {
        this.agencySelectItemList = agencySelectItemList;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public OutletManagementHeaderBean getManagementHeaderBean() {
        return managementHeaderBean;
    }

    public void setManagementHeaderBean(OutletManagementHeaderBean managementHeaderBean) {
        this.managementHeaderBean = managementHeaderBean;
    }

    public boolean getIsHeadOffice() {
        return isHeadOffice;
    }

    public void setIsHeadOffice(boolean isHeadOffice) {
        this.isHeadOffice = isHeadOffice;
    }

    public OutletManagementImageBean getOutletImageBean() {
        return outletImageBean;
    }

    public void setOutletImageBean(OutletManagementImageBean outletImageBean) {
        this.outletImageBean = outletImageBean;
    }

    public String getTempUploadImageFilePath() {
        return tempUploadImageFilePath;
    }

    public void setTempUploadImageFilePath(String tempUploadImageFilePath) {
        this.tempUploadImageFilePath = tempUploadImageFilePath;
    }

    public String getOldUploadImageFilePath() {
        return oldUploadImageFilePath;
    }

    public void setOldUploadImageFilePath(String oldUploadImageFilePath) {
        this.oldUploadImageFilePath = oldUploadImageFilePath;
    }

    public String getUploadImageFileName() {
        return uploadImageFileName;
    }

    public void setUploadImageFileName(String uploadImageFileName) {
        this.uploadImageFileName = uploadImageFileName;
    }

}
