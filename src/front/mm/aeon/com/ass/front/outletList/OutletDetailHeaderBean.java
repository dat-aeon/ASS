/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.outletList;

import java.io.Serializable;
import java.util.Stack;

public class OutletDetailHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 992544123670064821L;

    private Integer outletId;

    private Stack<String> priorLinkStack;

    public OutletDetailHeaderBean() {
        super();
        this.priorLinkStack = new Stack<String>();
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    public Stack<String> getPriorLinkStack() {
        return priorLinkStack;
    }

    public void setPriorLinkStack(Stack<String> priorLinkStack) {
        this.priorLinkStack = priorLinkStack;
    }

}
