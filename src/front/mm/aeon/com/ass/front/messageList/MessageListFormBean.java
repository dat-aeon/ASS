/**************************************************************************
 * $Date : $
 * $Author : $
 * $Rev : $
 * Copyright (c) 2014 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.messageList;

import java.util.List;



import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;

@Name("messageListFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class MessageListFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 7565735811794600069L;

    private boolean init = true;

    @In(required = false, value = "doReload")
    @Out(required = false, value = "doReload")
    private Boolean doReload;

    private MessageListHeaderBean searchHeaderBean;

    private List<MessageListLineBean> lineBeanList;

    @Begin(nested = true)
    public void init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new MessageListHeaderBean();
        doReload = new Boolean(true);
        init = false;
    }
    
    @Action
    public String search() {
        return LinkTarget.SEARCH;
    }
    
    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Boolean getDoReload() {
        return doReload;
    }

    public void setDoReload(Boolean doReload) {
        this.doReload = doReload;
    }

    public MessageListHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(MessageListHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<MessageListLineBean> getLineBeanList() {
        return lineBeanList;
    }

    public void setLineBeanList(List<MessageListLineBean> lineBeanList) {
        this.lineBeanList = lineBeanList;
    }

}
