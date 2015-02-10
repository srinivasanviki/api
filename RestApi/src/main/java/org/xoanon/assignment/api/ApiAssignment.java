package org.xoanon.assignment.api;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.restexpress.RestExpress;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xoanon.assignment.api.di.ApiDI;
import org.xoanon.assignment.api.routes.AllRoutes;

import java.io.File;

/**
 * Created by viki on 10/2/15.
 */
public class ApiAssignment {
    public static void main(String args[]){
       String path=Thread.currentThread().getContextClassLoader().getResource("api.properties").getFile();
        File configFile=new File(path);
        PropertiesConfiguration configuration=new PropertiesConfiguration();
        configuration.setDelimiterParsingDisabled(true);
        try {
            configuration.load(configFile);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        final AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.getBeanFactory().registerSingleton("config",configuration);
        appContext.register(ApiDI.class);
        appContext.refresh();
        int buffer_size= configuration.getInt("restexpress.receive.buffer.size");
        int thread_count= configuration.getInt("restexpress.io.thread.count");
        int executor_thread_count= configuration.getInt("restexpress.executor.thread.count");
        RestExpress server=new RestExpress().setName("API").setKeepAlive(true).setReuseAddress(true);
        server.setReceiveBufferSize(buffer_size);
        server.setIoThreadCount(thread_count);
        server.setExecutorThreadCount(executor_thread_count);

        AllRoutes.defineRoutes(server,appContext);
        server.setMaxContentSize(1024 * 1024 * 10);
        int maxPorts = configuration.getInt("server.port.max", 1);
        int startPort = configuration.getInt("server.port", 7071);
        for (int i = 0; i < maxPorts; i++) {
            server.bind(startPort + i);
        }

    }
}
