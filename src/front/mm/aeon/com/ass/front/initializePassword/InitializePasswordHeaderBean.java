/**************************************************************************
 * $Date: 2018-06-20$
 * $Author: Khin Yadanar Thein $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.initializePassword;

import java.io.Serializable;
import java.sql.Timestamp;

public class InitializePasswordHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3002213596232890612L;

    private String password;
    
    private Timestamp updatedTime;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

}
