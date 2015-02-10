package org.xoanon.assignment.api.routes;

import org.restexpress.RestExpress;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by viki on 10/2/15.
 */
public class AllRoutes {
    public static void defineRoutes(RestExpress server, AnnotationConfigApplicationContext appContext) {
      RoutesBuilder.defineRoutes(server,appContext);
    }
}
