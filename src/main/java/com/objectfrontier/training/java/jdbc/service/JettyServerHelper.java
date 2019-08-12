package com.objectfrontier.training.java.jdbc.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import com.objectfrontier.training.java.jdbc.exception.AppException;
import com.objectfrontier.training.java.jdbc.exception.ExceptionCode;

public class JettyServerHelper {

    private final Server server;
    private final WebAppContext appContext;

    public JettyServerHelper(int port, String webConfig, String webDirLocation, String contextPath) throws Exception {

        try {
            server = new Server(port);
            appContext = new WebAppContext();
            appContext.setDescriptor(webConfig);
            appContext.setResourceBase(webDirLocation);
            appContext.setContextPath(contextPath);
            appContext.setParentLoaderPriority(true);
            appContext.setThrowUnavailableOnStartupException(true);

            server.setHandler(appContext);
        } catch (Exception e) {
            throw new AppException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
        server.destroy();
    }
}
