package com.approve.son.documentapproval.global.system.context;

public class SonContextHolder {
    private static final ThreadLocal<SonContext> contextHolder = new ThreadLocal<>();

    /**
     * @name : setContext
     * @param : context
     * @description : Context 셋팅한다.
     */
    public static void setContext(SonContext context) {
        contextHolder.set(context);
    }

    /**
     * @name : getContext
     * @return : ProductSupplyContext
     * @description : Context를 얻는다.
     */
    public static SonContext getContext() {
        SonContext context = contextHolder.get();
        if (context == null) {
            context = new SonContext();
            contextHolder.set(context);
        }
        return context;
    }

    /**
     * @name : clearContext
     * @description : context를 clear 한다.
     */
    public static void clearContext() {
        contextHolder.remove();
    }
}
