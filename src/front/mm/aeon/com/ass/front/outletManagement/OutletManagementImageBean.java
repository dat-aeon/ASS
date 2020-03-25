/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletManagement;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;

public class OutletManagementImageBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4840971777152050323L;

    private UploadedFile outletImage;

    public UploadedFile getOutletImage() {
        return outletImage;
    }

    public void setOutletImage(UploadedFile outletImage) {
        this.outletImage = outletImage;
    }

}
