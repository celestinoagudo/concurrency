package com.grind.two.four.seven.section.threadlocal.controller;

import com.grind.two.four.seven.section.threadlocal.security.SecurityContext;
import com.grind.two.four.seven.section.threadlocal.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class DocumentController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
    private final Supplier<SecurityContext> securityContextSupplier;

    public DocumentController(final Supplier<SecurityContext> securityContextSupplier) {
        this.securityContextSupplier = securityContextSupplier;
    }

    public void read() {
        validateUserRole(UserRole.VIEWER);
        log.info("reading...");
    }

    private void validateUserRole(final UserRole requiredRole) {
        var securityContext = securityContextSupplier.get();
        if (!securityContext.hasPermission(requiredRole)) {
            log.error("User {} doesn't have {} permission", securityContext.userId(), requiredRole);
            throw new SecurityException("Unauthorized access. Required role: %s".formatted(requiredRole));
        }
    }

    public void editing() {
        validateUserRole(UserRole.EDITOR);
        log.info("editing...");
    }

    public void delete() {
        validateUserRole(UserRole.ADMIN);
        log.info("deleting...");
    }
}
