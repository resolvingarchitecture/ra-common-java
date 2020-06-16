package ra.common.route;

import ra.util.DequeStack;
import ra.util.JSONParser;
import ra.util.JSONPretty;
import ra.util.Stack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public final class DynamicRoutingSlip extends BaseRoute implements RoutingSlip {

    private Logger LOG = Logger.getLogger(DynamicRoutingSlip.class.getName());

    private Stack<Route> routes = new DequeStack<>();
    private Route currentRoute;
    private Boolean inProgress = false;

    public DynamicRoutingSlip() {}

    @Override
    public Integer numberRemainingRoutes() {
        return routes.numberRemainingRoutes();
    }

    @Override
    public Boolean inProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    @Override
    public void start() {
        inProgress = true;
    }

    public Route getCurrentRoute() {
        if(currentRoute==null) {
            nextRoute();
        }
        return currentRoute;
    }

    @Override
    public Route nextRoute() {
        Route next = routes.pop();
        currentRoute = next;
        return next;
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
        if(inProgress!=null) m.put("inProgress",inProgress);
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
        if(m.get("inProgress")!=null) inProgress = (Boolean)m.get("inProgress");
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
