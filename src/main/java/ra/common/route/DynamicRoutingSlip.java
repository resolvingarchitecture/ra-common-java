package ra.common.route;

import ra.common.DequeStack;
import ra.common.JSONParser;
import ra.common.JSONPretty;
import ra.common.Stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Routing Slip implemented as a dynamic stack using a Dequeu.
 *
 * @author objectorange
 */
public final class DynamicRoutingSlip extends BaseRoute implements RoutingSlip {

    private Logger LOG = Logger.getLogger(DynamicRoutingSlip.class.getName());

    private Stack<Route> routes = new DequeStack<>();
    private Route currentRoute;

    public DynamicRoutingSlip() {}

    @Override
    public Integer numberRemainingRoutes() {
        return routes.numberRemainingRoutes();
    }

    public Route getCurrentRoute() {
        if(currentRoute==null) {
            nextRoute();
        }
        return currentRoute;
    }

    @Override
    public Route nextRoute() {
        if(routes!= null && routes.numberRemainingRoutes() > 0) {
            Route next = routes.pop();
            currentRoute = next;
        } else {
            currentRoute = null;
        }
        return currentRoute;
    }

    @Override
    public Route peekAtNextRoute() {
        return routes.peek();
    }

    public boolean addRoute(Route route) {
        route.setRouteId(getRouteId());
        routes.push(route);
        return true;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        if(currentRoute!=null) m.put("currentRoute", currentRoute.toMap());
        if(routes!=null) {
            List<Map<String, Object>> rl = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            Iterator<Route> i = routes.getIterator();
            Route r;
            while(i.hasNext()) {
                r = i.next();
                rl.add(r.toMap());
            }
            m.put("routes", rl);
        }
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("currentRoute")!=null) {
            Map<String,Object> rm = (Map<String,Object>)m.get("currentRoute");
            String routeClass = (String)rm.get("type");
            try {
                currentRoute = (Route)Class.forName(routeClass).getConstructor().newInstance();
                currentRoute.fromMap(rm);
            } catch (Exception e) {
                LOG.warning(e.getLocalizedMessage());
            }
        }
        if(m.get("routes")!=null) {
            // MUST iterate routes list backwards to ensure stack is built correctly
            routes = new DequeStack<>();
            List<Map<String, Object>> rl = (List<Map<String, Object>>)m.get("routes");
            Map<String, Object> rm;
            Route r;
            for( int i = rl.size() - 1 ; i >=0 ; i-- ){
                rm = rl.get(i);
                if(rm.get("type")!=null) {
                    String type = (String)rm.get("type");
                    try {
                        r = (Route) Class.forName(type).getConstructor().newInstance();
                        r.fromMap(rm);
                        routes.push(r);
                    } catch (Exception e) {
                        LOG.warning(e.getLocalizedMessage());
                    }
                }
            }
        }
    }

    @Override
    public String toJSON() {
        return JSONPretty.toPretty(JSONParser.toString(toMap()), 4);
    }

    @Override
    public void fromJSON(String json) {
        fromMap((Map<String, Object>)JSONParser.parse(json));
    }

    @Override
    public String toString() {
        return toJSON();
    }

}
