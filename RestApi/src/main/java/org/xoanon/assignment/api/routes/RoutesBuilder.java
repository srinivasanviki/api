package org.xoanon.assignment.api.routes;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;
import org.springframework.context.ApplicationContext;

/**
 * Created by viki on 10/2/15.
 */
public class RoutesBuilder {
    public static void defineRoutes(RestExpress server, ApplicationContext appContext) {
        server.uri("/api/user/create", appContext.getBean("usersController")).action("createUsers", HttpMethod.POST);
        server.uri("/api/user", appContext.getBean("usersController")).action("getUsers", HttpMethod.GET);
    }
}
