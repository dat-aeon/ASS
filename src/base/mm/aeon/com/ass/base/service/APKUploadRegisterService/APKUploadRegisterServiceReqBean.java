/**************************************************************************
 * $Date: 2018-09-21$
 * $Author: SHOON LATT WINNE $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.service.APKUploadRegisterService;

import mm.com.dat.presto.main.common.service.bean.AbstractServiceReqBean;

public class APKUploadRegisterServiceReqBean extends AbstractServiceReqBean {

    private static final long serialVersionUID = -6473762488720461939L;

    private String version;
    
    private String description;
    
    private String fileName;
    
    private String filePath;
    
    private String createdBy;
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getServiceId() {
        // TODO Auto-generated method stub
        return "APKUPLOADSI";
    }

}
