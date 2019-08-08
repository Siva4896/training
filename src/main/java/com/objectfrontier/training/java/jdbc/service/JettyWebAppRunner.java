package com.objectfrontier.training.java.jdbc.service;

import java.io.File;

public class JettyWebAppRunner {
    private final Server server;
    private final WebAppContext context;

    public JettyWebAppRunner(int port, File webxml, File webappDir, String contextPath)
    throws Exception {
        assert webxml.isFile();
        assert webappDir.isDirectory();
        if (!contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }
        server = new Server(port);
        context = new WebAppContext();
        context.setDescriptor(webxml.getAbsolutePath());
        context.setResourceBase(webappDir.getAbsolutePath());
        context.setContextPath(contextPath);
        context.setParentLoaderPriority(true);
        context.setThrowUnavailableOnStartupException(true);

        server.setHandler(context);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
        server.destroy();
    }
}
