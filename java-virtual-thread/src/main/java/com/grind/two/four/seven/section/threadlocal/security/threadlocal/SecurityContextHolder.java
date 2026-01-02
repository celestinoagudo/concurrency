package com.grind.two.four.seven.section.threadlocal.security.threadlocal;

import com.grind.two.four.seven.section.threadlocal.security.SecurityContext;
import com.grind.two.four.seven.section.threadlocal.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ThreadLocal<SecurityContext> contextHolder = ThreadLocal.withInitial(() -> ANONYMOUS_CONTEXT);
    private SecurityContextHolder() {
    }

    public static SecurityContext getContext() {
        return contextHolder.get();
    }

    static void setContextHolder(final SecurityContext securityContext) {
        contextHolder.set(securityContext);
    }

    static void clear() {
        contextHolder.remove();
    }
}
