package ra.common.social;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Community extends Group {

    private final static int minMemberSize = 2;
    private final static int maxMemberSize = 150; // Dunbars Number

    // Hang-arounds are typically allowed to meet others in the community and invited to some events
    private final static int maxHangAroundSize = 10;
    // Associates have requested to formally join the community and been accepted by a number of members
    private final static int maxAssociatesSize = 10;
    // Prospects are Associates who have reached a determined level and been accepted by a number of members as an Associate
    private final static int maxProspectsSize = 10;

    private final Map<String,Individual> hangAroundsByPubKeyAddress = new HashMap<>();
    private final Map<String,Individual> associatesByPubKeyAddress = new HashMap<>();
    private final Map<String,Individual> prospectsByPubKeyAddress = new HashMap<>();

    private final Map<String,Member> membersByUsername = new HashMap<>();
    private final Map<String,Member> membersByFingerprint = new HashMap<>();
    private final Map<String,Member> membersByPubKeyAddress = new HashMap<>();

    private final Map<String,Individual> bannedIndividualsByPubKeyAddress = new HashMap<>();

    private UUID id;

}
