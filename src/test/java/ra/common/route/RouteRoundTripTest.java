package ra.common.route;

import org.junit.Test;

import ra.common.messaging.TextMessage;
import ra.common.identity.DID;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@code routeId} was lost on JSON round-trip because {@link BaseRoute#fromMap}
 * read the key {@code "routedId"} (a typo) while {@link BaseRoute#toMap} wrote
 * {@code "routeId"}. {@link TextMessage} lost {@code to} / {@code from} /
 * {@code text} because its {@code toMap} / {@code fromMap} only delegated to
 * super. Both fixed in 1.3.2.
 */
public class RouteRoundTripTest {

    @Test
    public void simpleRouteKeepsRouteIdThroughJson() {
        SimpleRoute r = new SimpleRoute("svc", "OP");
        r.setRouteId(42L);
        r.setRouted(true);

        SimpleRoute restored = new SimpleRoute();
        restored.fromMap(r.toMap());

        assertEquals(Long.valueOf(42L), restored.getRouteId());
        assertEquals(Boolean.TRUE, restored.getRouted());
        assertEquals("svc", restored.getService());
        assertEquals("OP", restored.getOperation());
    }

    @Test
    public void externalRouteKeepsRouteIdThroughJson() {
        SimpleExternalRoute r = new SimpleExternalRoute("svc", "SEND");
        r.setRouteId(-7L);

        SimpleExternalRoute restored = new SimpleExternalRoute();
        restored.fromMap(r.toMap());

        assertEquals(Long.valueOf(-7L), restored.getRouteId());
    }

    @Test
    public void textMessageRoundTripsItsFields() {
        TextMessage m = new TextMessage(new DID(), new DID(), "hello world");
        m.getTo().setUsername("alice");
        m.getFrom().setUsername("bob");

        TextMessage restored = new TextMessage();
        restored.fromMap(m.toMap());

        assertEquals("hello world", restored.getText());
        assertNotNull(restored.getTo());
        assertNotNull(restored.getFrom());
        assertEquals("alice", restored.getTo().getUsername());
        assertEquals("bob", restored.getFrom().getUsername());
    }
}
