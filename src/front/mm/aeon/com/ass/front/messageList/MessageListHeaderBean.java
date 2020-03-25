/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.messageList;

import java.io.Serializable;
import java.util.Date;

public class MessageListHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9031001348728885817L;

    private String agencyUserName;

    private Date fromDateTime;

    private Date toDateTime;

    public String getAgencyUserName() {
        return agencyUserName;
    }

    public void setAgencyUserName(String agencyUserName) {
        this.agencyUserName = agencyUserName;
    }

    public Date getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Date fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Date getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Date toDateTime) {
        this.toDateTime = toDateTime;
    }

}
