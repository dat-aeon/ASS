/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.agencyUserManagement;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import javax.faces.model.SelectItem;

import mm.aeon.com.ass.base.dto.agencyOutletSelectList.AgencyOutletSelectListResDto;

public class AgencyUserRegisterHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3002213596232890612L;

    private String pageTitle;

    private int id;

    private String userName;

    private String loginID;

    private String password;

    private String confirmPassword;

    private ArrayList<SelectItem> agencyNameList = new ArrayList<SelectItem>();

    private int agencyId;

    private String agencyName;

    private Map<String, ArrayList<AgencyOutletSelectListResDto>> allOutletNameList;

    private ArrayList<SelectItem> outletNameList = new ArrayList<SelectItem>();

    private String outletName;

    private String startDate;

    private String endDate;

    private String phoneNumber;

    private String email;

    private String address;

    private String remark;

    private String startMinDate;

    private String endMinDate;

    private String createdBy;

    private Timestamp createdTime;

    private String updatedBy;

    private Timestamp updatedTime;

    private boolean isError;

    private boolean isUpdate;

    private boolean isRegisterPage;

    private Stack<String> priorLinkStack;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public ArrayList<SelectItem> getAgencyNameList() {
        return agencyNameList;
    }

    public void setAgencyNameList(ArrayList<SelectItem> agencyNameList) {
        this.agencyNameList = agencyNameList;
    }

    public ArrayList<SelectItem> getOutletNameList() {
        return outletNameList;
    }

    public void setOutletNameList(ArrayList<SelectItem> outletNameList) {
        this.outletNameList = outletNameList;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartMinDate() {
        return startMinDate;
    }

    public void setStartMinDate(String startMinDate) {
        this.startMinDate = startMinDate;
    }

    public String getEndMinDate() {
        return endMinDate;
    }

    public void setEndMinDate(String endMinDate) {
        this.endMinDate = endMinDate;
    }

    public Map<String, ArrayList<AgencyOutletSelectListResDto>> getAllOutletNameList() {
        return allOutletNameList;
    }

    public void setAllOutletNameList(Map<String, ArrayList<AgencyOutletSelectListResDto>> allOutletNameList) {
        this.allOutletNameList = allOutletNameList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean getIsRegisterPage() {
        return isRegisterPage;
    }

    public void setIsRegisterPage(boolean isRegisterPage) {
        this.isRegisterPage = isRegisterPage;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public Stack<String> getPriorLinkStack() {
        return priorLinkStack;
    }

    public void setPriorLinkStack(Stack<String> priorLinkStack) {
        this.priorLinkStack = priorLinkStack;
    }

}
