package ra.common.identity;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A vouch (a {@link Signature} on an attribute of a {@link PublicKey}) must
 * survive persistence. Before the {@code toMap()} / {@code fromMap()} fix these
 * assertions all failed — every field came back null.
 */
public class SignatureRoundTripTest {

    private static Signature sample() {
        Signature s = new Signature();
        s.setValueSigned("alice");
        s.setAlgorithm("BIP-340");
        s.setSignedDate(new Date(1_757_030_400_000L));
        s.setSignedByUsername("bob");
        s.setSignedByFingerprint("100f6d8cbf94afb6fc58e9c384b9b3a6516091373a83c869f4e24a9d2bb4a494");
        s.setSignedByAddress("100f6d8cbf94afb6fc58e9c384b9b3a6516091373a83c869f4e24a9d2bb4a494");
        return s;
    }

    @Test
    public void signatureRoundTripsThroughJson() {
        Signature original = sample();

        Signature restored = new Signature();
        restored.fromJSON(original.toJSON());

        assertEquals("alice", restored.getValueSigned());
        assertEquals("BIP-340", restored.getAlgorithm());
        assertNotNull(restored.getSignedDate());
        assertEquals(1_757_030_400_000L, restored.getSignedDate().getTime());
        assertEquals("bob", restored.getSignedByUsername());
        assertEquals(original.getSignedByFingerprint(), restored.getSignedByFingerprint());
        assertEquals(original.getSignedByAddress(), restored.getSignedByAddress());
        assertEquals(original, restored);
    }

    @Test
    public void emptySignatureRoundTrips() {
        Signature restored = new Signature();
        restored.fromJSON(new Signature().toJSON());
        assertTrue(restored.toMap().isEmpty());
    }

    @Test
    public void publicKeyCarriesSignedAttributesThroughJson() {
        PublicKey pk = new PublicKey("100f6d8cbf94afb6fc58e9c384b9b3a6516091373a83c869f4e24a9d2bb4a494");
        pk.addSignedAttribute("name", sample());

        PublicKey restored = new PublicKey();
        restored.fromJSON(pk.toJSON());

        List<Signature> sigs = restored.getSignedAttributes().get("name");
        assertNotNull("signed attribute 'name' lost on persistence", sigs);
        assertEquals(1, sigs.size());
        assertEquals("alice", sigs.get(0).getValueSigned());
        assertEquals("BIP-340", sigs.get(0).getAlgorithm());
        assertEquals(1_757_030_400_000L, sigs.get(0).getSignedDate().getTime());
    }
}
