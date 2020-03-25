/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.apkUpload;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class APKUploadHeaderBean implements Serializable {

    private static final long serialVersionUID = 3002213596232890612L;

    private UploadedFile file;
    
    private String description;
    
    private String version;
    
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
