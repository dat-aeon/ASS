/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.primefaces.model.LazyDataModel;

import mm.aeon.com.ass.front.agencyListManagement.AgencyListManagementHeaderBean;
import mm.aeon.com.ass.front.agencyUserManagement.AgencyUserRegisterHeaderBean;
import mm.aeon.com.ass.front.common.constants.ASSCommon;
import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.aeon.com.ass.front.common.util.CommonUtil;
import mm.aeon.com.ass.front.common.util.FileHandler;
import mm.aeon.com.ass.front.outletManagement.OutletManagementHeaderBean;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;
import mm.com.dat.presto.main.front.message.MessageType;

@Name("outletListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class OutletListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -532801949885579872L;

    private OutletListHeaderBean searchHeaderBean;

    private OutletListLineBean lineBean;

    @Out(required = false, value = "outletUpdateParam")
    private OutletManagementHeaderBean updateParam;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    @In(required = false, value = "outletDetailParam")
    @Out(required = false, value = "outletDetailParam")
    private OutletDetailHeaderBean detailHeaderBean;

    @In(required = false, value = "detailParam")
    @Out(required = false, value = "detailParam")
    private AgencyUserRegisterHeaderBean userDetailParam;

    @In(required = false, value = "agencyDetailParam")
    @Out(required = false, value = "agencyDetailParam")
    private AgencyListManagementHeaderBean agencyDetailParam;

    private LazyDataModel<OutletListLineBean> lazyModel;

    private OutletDetailLineBean detailLineBean;

    private List<OutletListLineBean> lineBeanList;

    private ArrayList<SelectItem> statusList;

    private Map<Integer, String> locationMap;

    private boolean init = true;

    private String tempUploadImageFilePath;

    private int pageFirst;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new OutletListHeaderBean();
        locationMap = CommonUtil.getLocationMap();
        doReload = new Boolean(true);
        init = false;
    }

    @Action
    public String search() {
        this.detailHeaderBean = new OutletDetailHeaderBean();
        this.doReload = new Boolean(false);
        this.updateParam = null;
        this.lazyModel = null;

        if (!this.getMessageContainer().checkMessage(MessageType.ERROR) && lineBeanList.size() != 0) {
            lazyModel = new OutletListPaginationController(this.lineBeanList.size(), this.lineBeanList);
        }

        return LinkTarget.OK;
    }

    public String prepareDetail(Integer outletId) {
        this.detailHeaderBean.setOutletId(outletId);
        this.detailHeaderBean.getPriorLinkStack().push(LinkTarget.SEARCH);
        return LinkTarget.DETAIL;
    }

    @Action
    public String detail() {
        if (this.getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.setDoReload(true);
        }

        if (!StringUtils.isEmpty(detailLineBean.getImagePath())) {
            String uploadPath = CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getOutletImageFolder();
            File sourceFile = new File(uploadPath + detailLineBean.getImagePath());
            File destinationFile = new File(FileHandler.getSystemPath() + "/temp" + CommonUtil.getOutletImageFolder()
                    + detailLineBean.getImagePath());

            try {
                System.gc();// Added this part
                Thread.sleep(1000);// This part gives the Bufferedreaders and the InputStreams time to close
                                   // Completely
                FileHandler.copyFile(sourceFile, destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tempUploadImageFilePath = "/temp" + CommonUtil.getOutletImageFolder() + detailLineBean.getImagePath();
        }
        return LinkTarget.OK;
    }

    public String detailBack() {
        this.detailLineBean = null;
        tempUploadImageFilePath = null;
        return detailHeaderBean.getPriorLinkStack().pop();
    }

    public String agencyDetail(Integer agencyId) {
        this.agencyDetailParam = new AgencyListManagementHeaderBean();
        this.agencyDetailParam.setAgencyId(agencyId);
        this.detailHeaderBean.getPriorLinkStack().push(LinkTarget.DETAIL_OUTLET);
        this.agencyDetailParam.setPriorLinkStack(this.detailHeaderBean.getPriorLinkStack());

        return LinkTarget.DETAIL_AGENCY;
    }

    public String userDetail(int userId) {
        this.userDetailParam = new AgencyUserRegisterHeaderBean();
        this.userDetailParam.setId(userId);
        this.detailHeaderBean.getPriorLinkStack().push(LinkTarget.DETAIL_OUTLET);
        this.userDetailParam.setPriorLinkStack(this.detailHeaderBean.getPriorLinkStack());

        return LinkTarget.DETAIL_AGENCY_USER;
    }

    @Action
    public String toggleValidStatus(OutletListLineBean lineBean) {
        return LinkTarget.OK;
    }

    public String prepareRegister() {
        this.updateParam = null;
        return LinkTarget.REGISTER;
    }

    @Action
    public String delete() {
        this.doReload = false;
        this.lineBean = null;
        if (!getMessageContainer().checkMessage(MessageType.ERROR)) {
            this.doReload = true;
            return LinkTarget.OK;
        }

        return LinkTarget.OK;
    }

    public String back() {
        return LinkTarget.BACK;
    }

    public String prepareUpdate(OutletListLineBean lineBean) {
        this.updateParam = new OutletManagementHeaderBean();

        updateParam.setAddress(lineBean.getAddress());
        updateParam.setAgencyId(lineBean.getAgencyId());
        updateParam.setOldAgencyId(lineBean.getAgencyId());
        updateParam.setAgencyOutletId(lineBean.getAgencyOutletId());
        updateParam.setAgnecyOutletUpdatedTime(lineBean.getAgnecyOutletUpdatedTime());
        updateParam.setOutletId(lineBean.getOutletId());
        updateParam.setOutletName(lineBean.getOutletName());
        updateParam.setRemark(lineBean.getRemark());
        updateParam.setPhoneNo(lineBean.getPhoneNo());
        updateParam.setUploadedImageFilePath(lineBean.getImagePath());
        updateParam.setLongitude(lineBean.getLongitude());
        updateParam.setLatitude(lineBean.getLatitude());
        updateParam.setUpdatedBy(lineBean.getUpdatedBy());
        updateParam.setUpdatedTime(lineBean.getUpdatedTime());
        updateParam.setForUpdate(true);

        if (!StringUtils.isEmpty(lineBean.getImagePath())) {
            String uploadPath = CommonUtil.getUploadImageBaseFilePath() + CommonUtil.getOutletImageFolder();
            File sourceFile = new File(uploadPath + lineBean.getImagePath());
            File destinationFile = new File(FileHandler.getSystemPath() + "/temp" + CommonUtil.getOutletImageFolder()
                    + lineBean.getImagePath());

            try {
                System.gc();// Added this part
                Thread.sleep(1000);// This part gives the Bufferedreaders and the InputStreams time to close
                                   // Completely
                FileHandler.copyFile(sourceFile, destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return LinkTarget.UPDATE_INIT;
    }

    public OutletListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(OutletListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public OutletListLineBean getLineBean() {
        return lineBean;
    }

    public void setLineBean(OutletListLineBean lineBean) {
        this.lineBean = lineBean;
    }

    public OutletManagementHeaderBean getUpdateParam() {
        return updateParam;
    }

    public void setUpdateParam(OutletManagementHeaderBean updateParam) {
        this.updateParam = updateParam;
    }

    public OutletDetailHeaderBean getDetailHeaderBean() {
        return detailHeaderBean;
    }

    public void setDetailHeaderBean(OutletDetailHeaderBean detailHeaderBean) {
        this.detailHeaderBean = detailHeaderBean;
    }

    public AgencyUserRegisterHeaderBean getUserDetailParam() {
        return userDetailParam;
    }

    public void setUserDetailParam(AgencyUserRegisterHeaderBean userDetailParam) {
        this.userDetailParam = userDetailParam;
    }

    public AgencyListManagementHeaderBean getAgencyDetailParam() {
        return agencyDetailParam;
    }

    public void setAgencyDetailParam(AgencyListManagementHeaderBean agencyDetailParam) {
        this.agencyDetailParam = agencyDetailParam;
    }

    public LazyDataModel<OutletListLineBean> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<OutletListLineBean> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public OutletDetailLineBean getDetailLineBean() {
        return detailLineBean;
    }

    public void setDetailLineBean(OutletDetailLineBean detailLineBean) {
        this.detailLineBean = detailLineBean;
    }

    public List<OutletListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<OutletListLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

    public ArrayList<SelectItem> getStatusList() {

        statusList = new ArrayList<SelectItem>();

        SelectItem item = new SelectItem();
        item.setLabel(ASSCommon.SPACE);
        item.setValue(null);
        statusList.add(item);

        item = new SelectItem();
        item.setLabel(ASSCommon.DISABLED);
        item.setValue(ASSCommon.ZERO);
        statusList.add(item);

        item = new SelectItem();
        item.setLabel(ASSCommon.ENABLED);
        item.setValue(ASSCommon.ONE);
        statusList.add(item);

        return statusList;
    }

    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public Map<Integer, String> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(Map<Integer, String> locationMap) {
        this.locationMap = locationMap;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public int getPageFirst() {
        return pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public String getTempUploadImageFilePath() {
        return tempUploadImageFilePath;
    }

    public void setTempUploadImageFilePath(String tempUploadImageFilePath) {
        this.tempUploadImageFilePath = tempUploadImageFilePath;
    }

}
