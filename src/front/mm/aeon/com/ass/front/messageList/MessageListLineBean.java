/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.messageList;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageListLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6086045120964162770L;

    private String agencyUserName;

    private String sendBy;

    private Timestamp sentTime;

    private String message;

    public String getAgencyUserName() {
        return agencyUserName;
    }

    public void setAgencyUserName(String agencyUserName) {
        this.agencyUserName = agencyUserName;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
