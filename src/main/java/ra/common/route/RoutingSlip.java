package ra.common.route;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface RoutingSlip extends Route {

    Integer numberRemainingRoutes();

    Boolean inProgress();

    void start();

    Route getCurrentRoute();

    Route nextRoute();

    Route peekAtNextRoute();
}
