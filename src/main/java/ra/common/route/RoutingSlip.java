package ra.common.route;

/**
 *
 */
public interface RoutingSlip extends Route {

    Integer numberRemainingRoutes();

    Route getCurrentRoute();

    Route peekAtNextRoute();

    Route nextRoute();

}
