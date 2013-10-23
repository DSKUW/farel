package pl.edu.uw.dsk.monitoring;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class JenkinsSimpleBean {
    String result;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
}

class OpsViewToken {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

class OpsViewResponse {
    public OpsViewSummary getSummary() {
        return summary;
    }

    public void setSummary(OpsViewSummary summary) {
        this.summary = summary;
    }

    public OpsViewState[] getList() {
        return list;
    }

    public void setList(OpsViewState[] list) {
        this.list = list;
    }

    public OpsViewSummary summary;
    public OpsViewState[] list = new OpsViewState[1];
}

class OpsViewSummary {
    public int getHandled() {
        return handled;
    }

    public void setHandled(int handled) {
        this.handled = handled;
    }

    public int getUnhandled() {
        return unhandled;
    }

    public void setUnhandled(int unhandled) {
        this.unhandled = unhandled;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public OpsViewService getService() {
        return service;
    }

    public void setService(OpsViewService service) {
        this.service = service;
    }

    public OpsViewHost getHost() {
        return host;
    }

    public void setHost(OpsViewHost host) {
        this.host = host;
    }

    int handled;
    int unhandled;
    int total;
    OpsViewService service;
    OpsViewHost host;
}

class OpsViewService {
    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public int getHandled() {
        return handled;
    }

    public void setHandled(int handled) {
        this.handled = handled;
    }

    public int getUnhandled() {
        return unhandled;
    }

    public void setUnhandled(int unhandled) {
        this.unhandled = unhandled;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    int ok;
    int handled;
    int unhandled;
    int total;
}

class OpsViewHost {
    public int getHandled() {
        return handled;
    }

    public void setHandled(int handled) {
        this.handled = handled;
    }

    public int getUnhandled() {
        return unhandled;
    }

    public void setUnhandled(int unhandled) {
        this.unhandled = unhandled;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    int handled;
    int unhandled;
    int up;
    int total;
}

class OpsViewState {
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OpsViewInnerSummary getSummary() {
        return summary;
    }

    public void setSummary(OpsViewInnerSummary summary) {
        this.summary = summary;
    }

    public int getUnhandled() {
        return unhandled;
    }

    public void setUnhandled(int unhandled) {
        this.unhandled = unhandled;
    }

    public int getMax_check_attempts() {
        return max_check_attempts;
    }

    public void setMax_check_attempts(int max_check_attempts) {
        this.max_check_attempts = max_check_attempts;
    }

    public int getNum_interfaces() {
        return num_interfaces;
    }

    public void setNum_interfaces(int num_interfaces) {
        this.num_interfaces = num_interfaces;
    }

    public int getState_duration() {
        return state_duration;
    }

    public void setState_duration(int state_duration) {
        this.state_duration = state_duration;
    }

    public OpsViewServices[] getServices() {
        return services;
    }

    public void setServices(OpsViewServices[] services) {
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState_type() {
        return state_type;
    }

    public void setState_type(String state_type) {
        this.state_type = state_type;
    }

    public int getCurrent_check_attempt() {
        return current_check_attempt;
    }

    public void setCurrent_check_attempt(int current_check_attempt) {
        this.current_check_attempt = current_check_attempt;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getNum_services() {
        return num_services;
    }

    public void setNum_services(int num_services) {
        this.num_services = num_services;
    }

    public int getDowntime() {
        return downtime;
    }

    public void setDowntime(int downtime) {
        this.downtime = downtime;
    }

    public int getLast_check() {
        return last_check;
    }

    public void setLast_check(int last_check) {
        this.last_check = last_check;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    String icon;
    String state;
    OpsViewInnerSummary summary;

    int unhandled;
    int max_check_attempts;
    int num_interfaces;
    int state_duration;
    OpsViewServices[] services = new OpsViewServices[5];
    String name;
    String state_type;
    int current_check_attempt;
    String output;
    int num_services;
    int downtime;
    int last_check;
    String alias;
}

class OpsViewInnerSummary {
    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public int getHandled() {
        return handled;
    }

    public void setHandled(int handled) {
        this.handled = handled;
    }

    public String getComputed_state() {
        return computed_state;
    }

    public void setComputed_state(String computed_state) {
        this.computed_state = computed_state;
    }

    public int getUnhandled() {
        return unhandled;
    }

    public void setUnhandled(int unhandled) {
        this.unhandled = unhandled;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    int ok;
    int handled;
    String computed_state;
    int unhandled;
    int total;
}

class OpsViewServices {
    public int getMax_check_attempts() {
        return max_check_attempts;
    }

    public void setMax_check_attempts(int max_check_attempts) {
        this.max_check_attempts = max_check_attempts;
    }

    public int getMarkdown() {
        return markdown;
    }

    public void setMarkdown(int markdown) {
        this.markdown = markdown;
    }

    public int getState_duration() {
        return state_duration;
    }

    public void setState_duration(int state_duration) {
        this.state_duration = state_duration;
    }

    public String getState_type() {
        return state_type;
    }

    public void setState_type(String state_type) {
        this.state_type = state_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent_check_attempt() {
        return current_check_attempt;
    }

    public void setCurrent_check_attempt(int current_check_attempt) {
        this.current_check_attempt = current_check_attempt;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getService_object_id() {
        return service_object_id;
    }

    public void setService_object_id(int service_object_id) {
        this.service_object_id = service_object_id;
    }

    public int getUnhandled() {
        return unhandled;
    }

    public void setUnhandled(int unhandled) {
        this.unhandled = unhandled;
    }

    public int getDowntime() {
        return downtime;
    }

    public void setDowntime(int downtime) {
        this.downtime = downtime;
    }

    public int getLast_check() {
        return last_check;
    }

    public void setLast_check(int last_check) {
        this.last_check = last_check;
    }

    public int getPerfdata_available() {
        return perfdata_available;
    }

    public void setPerfdata_available(int perfdata_available) {
        this.perfdata_available = perfdata_available;
    }

    int max_check_attempts;
    int markdown;
    int state_duration;
    String state_type;
    String name;
    int current_check_attempt;
    String output;
    String state;
    int service_object_id;
    int unhandled;
    int downtime;
    int last_check;
    int perfdata_available;
}