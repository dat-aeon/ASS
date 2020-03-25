/**************************************************************************
 * $Date: 2018-09-18$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusUpload;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

public class JudgementStatusUploadHeaderBean implements Serializable {

    private static final long serialVersionUID = 3002213596232890612L;

    private UploadedFile file;

    private ArrayList<SelectItem> statusList = new ArrayList<SelectItem>();

    private int status;
    
    private ArrayList<String> headerList;
    
    public int getStatus() {
        return status;
    }

    public ArrayList<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public ArrayList<String> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(ArrayList<String> headerList) {
        this.headerList = headerList;
    }

}
