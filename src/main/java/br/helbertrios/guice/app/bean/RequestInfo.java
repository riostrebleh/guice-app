package br.helbertrios.guice.app.bean;

import java.util.HashSet;
import java.util.Set;

public class RequestInfo  {

    private RequestUser requestUser;
    private RequestAction requestAction;
    private String tokenID;
    private Integer sequencialNumber;

    private final Set<AutoCloseable> setDAOs = new HashSet<>(3);;

    public RequestInfo() {

    }
    public RequestInfo(RequestUser requestUser, RequestAction requestAction, String tokenID, Integer sequencialNumber) {
        this.requestUser = requestUser;
        this.requestAction = requestAction;
        this.tokenID = tokenID;
        this.sequencialNumber = sequencialNumber;
    }

    public RequestUser getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(RequestUser requestUser) {
        this.requestUser = requestUser;
    }

    public RequestAction getRequestAction() {
        return requestAction;
    }

    public void setRequestAction(RequestAction requestAction) {
        this.requestAction = requestAction;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public Integer getSequencialNumber() {
        return sequencialNumber;
    }

    public void setSequencialNumber(Integer sequencialNumber) {
        this.sequencialNumber = sequencialNumber;
    }

    public void addDAO(AutoCloseable dao) {
        setDAOs.add(dao);
    }
}
