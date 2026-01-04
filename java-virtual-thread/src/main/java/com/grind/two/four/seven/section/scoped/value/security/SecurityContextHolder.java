package com.grind.two.four.seven.section.scoped.value.security;

import com.grind.two.four.seven.section.threadlocal.security.SecurityContext;
import com.grind.two.four.seven.section.threadlocal.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ScopedValue<SecurityContext> CONTEXT_HOLDER = ScopedValue.newInstance();

    private SecurityContextHolder() {
    }

    public static SecurityContext getContext() {
        return CONTEXT_HOLDER.orElse(ANONYMOUS_CONTEXT);
    }

    static ScopedValue<SecurityContext> getScopedValue() {
        return CONTEXT_HOLDER;
    }
}
