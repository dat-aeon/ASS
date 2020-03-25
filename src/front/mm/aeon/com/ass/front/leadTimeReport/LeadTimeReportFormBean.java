/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.leadTimeReport;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import mm.aeon.com.ass.front.common.constants.LinkTarget;
import mm.com.dat.presto.main.core.front.controller.AbstractFormBean;
import mm.com.dat.presto.main.core.front.controller.Action;
import mm.com.dat.presto.main.core.front.controller.FormBean;
import mm.com.dat.presto.main.core.front.controller.IRequest;
import mm.com.dat.presto.main.core.front.controller.IResponse;

/**
 * 
 * SearchFormBean Class.
 * <p>
 * 
 * <pre>
 *      SearchFormBean Class.
 * </pre>
 * 
 * </p>
 */
@Name("leadTimeReportFormBean")
@Scope(ScopeType.CONVERSATION)
@FormBean
public class LeadTimeReportFormBean extends AbstractFormBean implements IRequest, IResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 3584695935700863500L;

    private LeadTimeReportHeaderBean searchHeaderBean;

    private List<LeadTimeReportLineBean> refreshline;

    private List<LeadTimeReportLineBean> line;

    private List<LeadTimeAverageReportLineBean> avgline;
    
    private boolean init = true;

    private boolean isUpdate = false;

    private boolean stop = false;
    
    private boolean showExport = true;
    
    @Begin(nested = true)
    public String init() {
        this.getMessageContainer().clearAllMessages(true);
        searchHeaderBean = new LeadTimeReportHeaderBean();
        init = false;
        isUpdate = true;
        return LinkTarget.SEARCH;
    }

    public String prepareSearch() {
        stop = true;
        isUpdate = true;
        return LinkTarget.SEARCH;
    }
    
    @Action
    public String search() {
        
        if (isUpdate) {
            isUpdate = false;
        }
        if(stop == true)
            stop = false;
        return LinkTarget.OK;
    }

   /* @Action
    public String refresh() {
        return LinkTarget.OK;
     }*/
    
    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public LeadTimeReportHeaderBean getSearchHeaderBean() {
        return searchHeaderBean;
    }

    public void setSearchHeaderBean(LeadTimeReportHeaderBean searchHeaderBean) {
        this.searchHeaderBean = searchHeaderBean;
    }

    public List<LeadTimeReportLineBean> getLine() {
        return line;
    }

    public List<LeadTimeReportLineBean> getRefreshline() {
        return refreshline;
    }

    public void setRefreshline(List<LeadTimeReportLineBean> refreshline) {
        this.refreshline = refreshline;
    }

    public void setLine(List<LeadTimeReportLineBean> line) {
        this.line = line;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isShowExport() {
        return showExport;
    }

    public void setShowExport(boolean showExport) {
        this.showExport = showExport;
    }

    public List<LeadTimeAverageReportLineBean> getAvgline() {
        return avgline;
    }

    public void setAvgline(List<LeadTimeAverageReportLineBean> avgline) {
        this.avgline = avgline;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    
}
