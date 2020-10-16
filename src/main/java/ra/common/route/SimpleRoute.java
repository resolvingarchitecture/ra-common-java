package ra.common.route;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class SimpleRoute extends BaseRoute {

    public SimpleRoute() {}

    public SimpleRoute(String service, String operation) {
        super.service = service;
        super.operation = operation;
    }
}
