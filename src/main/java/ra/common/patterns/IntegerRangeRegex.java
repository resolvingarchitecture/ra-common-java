package ra.common.patterns;

import java.util.*;
import java.util.logging.Logger;

public class IntegerRangeRegex {

    private static final Logger LOG = Logger.getLogger(IntegerRangeRegex.class.getName());

    public static String generate(Long min, Long max) {
        return "^"+getRegExFromSegs(getRegexSegs(min, max))+"$";
    }

    private static String getRegExFromSegs(List<String> segs) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for(String seg : segs) {
            if(first) {
                sb.append(seg);
                first = false;
            } else {
                sb.append("|");
                sb.append(seg);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Return a list of regular expressions that match the numbers
     * that fall within the range of the given numbers, inclusive.
     * Assumes the given strings are numbers of the same length,
     * and 0-left-pads the resulting expressions, if necessary, to the
     * same length.
     * @param begStr
     * @param endStr
     * @return
     */
    private static List<String> getRegexSegs(String begStr, String endStr) {
        long start = Long.parseLong(begStr);
        long end   = Long.parseLong(endStr);
        int stringLength = begStr.length();
        return toRegex(getRegexPairs(start, end), stringLength);
    }

    /**
     * Return a list of regular expressions that match the numbers
     * that fall within the range of the given numbers, inclusive.
     * @param beg
     * @param end
     * @return
     */
    private static List<String> getRegexSegs(long beg, long end) {
        return toRegex(getRegexPairs(beg, end));
    }

    /**
     * return the list of integers that are the paired integers
     * used to generate the regular expressions for the given
     * range. Each pair of integers in the list -- 0,1, then 2,3,
     * etc., represents a range for which a single regular expression
     * is generated.
     * @param start
     * @param end
     * @return
     */
    private static List<Long> getRegexPairs(long start, long end) {
        ArrayList<Long> leftPairs = new ArrayList<>();
        long middleStartPoint = fillLeftPairs(leftPairs, start, end);
        ArrayList<Long> rightPairs = new ArrayList<>();
        long middleEndPoint = fillRightPairs(rightPairs, middleStartPoint, end);
        List<Long> pairs = new ArrayList<>(leftPairs);
        if (middleEndPoint > middleStartPoint) {
            pairs.add(middleStartPoint);
            pairs.add(middleEndPoint);
        }
        pairs.addAll(rightPairs);
        return pairs;
    }

    /**
     * print the given list of integer pairs - used for debugging.
     * @param list
     */
    @SuppressWarnings("unused")
    private static void printPairList(List<Long> list) {
        if (list.size() > 0) {
            LOG.info(String.format("%d-%d", list.get(0), list.get(1)));
            int i = 2;
            while (i < list.size()) {
                LOG.info(String.format(", %d-%d", list.get(i), list.get(i + 1)));
                i = i + 2;
            }
            System.out.println();
        }
    }

    /**
     * return the regular expressions that match the ranges in the given
     * list of integers. The list is in the form firstRangeStart, firstRangeEnd,
     * secondRangeStart, secondRangeEnd, etc.
     * @param pairs
     * @return
     */
    private static List<String> toRegex(List<Long> pairs) {
        return toRegex(pairs, 1);
    }

    /**
     * return the regular expressions that match the ranges in the given
     * list of integers. The list is in the form firstRangeStart, firstRangeEnd,
     * secondRangeStart, secondRangeEnd, etc. Each regular expression is 0-left-padded,
     * if necessary, to match strings of the given width.
     * @param pairs
     * @param minWidth
     * @return
     */
    private static List<String> toRegex(List<Long> pairs, int minWidth) {
        List<String> list = new ArrayList<>();
        String numberWithWidth = String.format("%%0%dd", minWidth);
        for (Iterator<Long> iterator = pairs.iterator(); iterator.hasNext();) {
            String start = String.format(numberWithWidth, iterator.next()); // String.valueOf(iterator.next());
            String end = String.format(numberWithWidth, iterator.next());
            list.add(toRegex(start, end));
        }
        return list;
    }

    /**
     * return a regular expression string that matches the range
     * with the given start and end strings.
     * @param start
     * @param end
     * @return
     */
    private static String toRegex(String start, String end) {
        assert start.length() == end.length();

        StringBuilder result = new StringBuilder();

        for (int pos = 0; pos < start.length(); pos++) {
            if (start.charAt(pos) == end.charAt(pos)) {
                result.append(start.charAt(pos));
            } else {
                result.append('[').append(start.charAt(pos)).append('-')
                        .append(end.charAt(pos)).append(']');
            }
        }
        return result.toString();
    }

    /**
     * Return the integer at the end of the range that is not covered
     * by any pairs added to the list.
     * @param rightPairs
     * @param start
     * @param end
     * @return
     */
    private static long fillRightPairs(List<Long> rightPairs, long start, long end) {
        long firstBeginRange = end;    // the end of the range not covered by pairs
        // from this routine.
        long y = end;
        long x = getPreviousBeginRange(y);

        if(x>0) {
            while (x >= start) {
                rightPairs.add(y);
                rightPairs.add(x);
                y = x - 1;
                firstBeginRange = y;
                x = getPreviousBeginRange(y);
            }
        }
        Collections.reverse(rightPairs);
        return firstBeginRange;
    }

    /**
     * Return the integer at the start of the range that is not covered
     * by any pairs added to its list.
     * @param leftInts
     * @param start
     * @param end
     * @return
     */
    private static long fillLeftPairs(ArrayList<Long> leftInts, long start, long end) {
        long x = start;
        long y = getNextLeftEndRange(x);

        while (y < end) {
            leftInts.add(x);
            leftInts.add(y);
            x = y + 1;
            y = getNextLeftEndRange(x);
        }
        return x;
    }

    /**
     * given a number, return the number altered such
     * that any 9s at the end of the number remain, and
     * one more 9 replaces the number before the other
     * 9s.
     * @param num
     * @return
     */
    private static long getNextLeftEndRange(long num) {
        char[] chars = String.valueOf(num).toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == '0') {
                chars[i] = '9';
            } else {
                chars[i] = '9';
                break;
            }
        }
        return Long.parseLong(String.valueOf(chars));
    }

    /**
     * given a number, return the number altered such that
     * any 9 at the end of the number is replaced by a 0,
     * and the number preceding any 9s is also replaced by
     * a 0.
     * @param num
     * @return
     */
    private static int getPreviousBeginRange(long num) {
        char[] chars = String.valueOf(num).toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == '9') {
                chars[i] = '0';
            } else {
                chars[i] = '0';
                break;
            }
        }
        return Integer.parseInt(String.valueOf(chars));
    }
}
