package com.grind.two.four.seven.section.threadlocal;

import com.grind.two.four.seven.section.threadlocal.controller.DocumentController;
import com.grind.two.four.seven.section.threadlocal.security.threadlocal.AuthenticationService;
import com.grind.two.four.seven.section.threadlocal.security.threadlocal.SecurityContextHolder;
import com.grind.two.four.seven.section.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DocumentAccessWithThreadLocal {

    private static final Logger log = LoggerFactory.getLogger(DocumentAccessWithThreadLocal.class);
    private static final DocumentController documentController = new DocumentController(SecurityContextHolder::getContext);

    public static void main(String[] args) {
        Thread.ofVirtual().name("anonymous").start(() -> documentAccessWorkflow(1, "password1"));
        Thread.ofVirtual().name("admin").start(() -> documentAccessWorkflow(1, "password"));
        Thread.ofVirtual().name("editor").start(() -> documentAccessWorkflow(2, "password"));
        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static void documentAccessWorkflow(final Integer userId, final String password) {
        AuthenticationService.loginAndExecute(userId, password, () -> {
            documentController.read();
            documentController.editing();
            documentController.delete();
        });
    }


}
