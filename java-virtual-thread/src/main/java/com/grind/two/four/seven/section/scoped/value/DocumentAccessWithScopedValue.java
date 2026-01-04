package com.grind.two.four.seven.section.scoped.value;

import com.grind.two.four.seven.section.scoped.value.security.AuthenticationService;
import com.grind.two.four.seven.section.scoped.value.security.SecurityContextHolder;
import com.grind.two.four.seven.section.threadlocal.controller.DocumentController;
import com.grind.two.four.seven.section.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DocumentAccessWithScopedValue {

    private static final Logger log = LoggerFactory.getLogger(DocumentAccessWithScopedValue.class);
    private static final DocumentController documentController = new DocumentController(SecurityContextHolder::getContext);

    public static void main(String[] args) {
//        Thread.ofVirtual().name("anonymous").start(() -> documentAccessWorkflow(1, "password1"));
//        Thread.ofVirtual().name("admin").start(() -> documentAccessWorkflow(1, "password"));
        Thread.ofVirtual().name("editor").start(() -> documentAccessWorkflow(3, "password"));
        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static void documentAccessWorkflow(final Integer userId, final String password) {
        AuthenticationService.loginAndExecute(userId, password, () -> {
            documentController.read();
            AuthenticationService.runAsAdmin(() -> {
                documentController.editing();
                documentController.delete();
            });
//            documentController.editing();
            documentController.read();
        });
    }
}
