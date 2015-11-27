package org.myapp.base;

import org.eclipse.jetty.server.Server;

/**
 * 使用Jetty运行调试Web应用, 在Console快速重载应用.
 * 
 */
public class Application {

    public static final int PORT = 8889;
    public static final String CONTEXT = "/";
    public static final String[] TLD_JAR_NAMES = new String[] { "spring-webmvc" };

    public static void main(String[] args) {

        // 启动Jetty
        Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
        JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);
        try {
            System.out.println("[HINT] Don't forget to set -XXEsWebServer:MaxPermSize=128m");
            server.start();
            System.out.println("Server running at http://localhost:" + PORT + CONTEXT);
            System.out.println("[HINT] Hit Enter to reload the application quickly");

            // 等待用户输入回车重载应用.
            while (true) {
                char c = (char) System.in.read();
                if (c == '\n') {
                    JettyFactory.reloadContext(server);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
