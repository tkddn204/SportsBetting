package com.ssanggland.views;

public class LoginSession {

    private long sessionUserId;

    public static LoginSession getInstance() {
        return INSTANCE.loginSession;
    }

    public void setSessionUserId(long sessionUserId) {
        this.sessionUserId = sessionUserId;
    }

    public long getSessionUserId() {
        return sessionUserId;
    }

    private LoginSession() { }

    private static class INSTANCE {
        public static LoginSession loginSession = new LoginSession();
    }

}
