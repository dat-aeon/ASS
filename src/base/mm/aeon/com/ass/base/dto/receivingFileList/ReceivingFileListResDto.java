/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.receivingFileList;

import java.util.Date;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class ReceivingFileListResDto implements IResServiceDto{

    private static final long serialVersionUID = -4014626560373692299L;

    private Date receivedTime;

    private String FileName;
    
    private String status;

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

}

