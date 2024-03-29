package ra.common;

import java.lang.reflect.Field;
import java.util.TimeZone;

/**
 * Methods to find out what system we are running on
 */
public class SystemVersion {

    private static final boolean isLinux = System.getProperty("os.name").startsWith("Linux");
    private static final boolean isWin = System.getProperty("os.name").startsWith("Win");
    private static final boolean isMac = System.getProperty("os.name").startsWith("Mac");
    private static final boolean isArm = System.getProperty("os.arch").startsWith("arm");
    private static final boolean isX86 = System.getProperty("os.arch").contains("86") ||
            System.getProperty("os.arch").equals("amd64");
    private static final boolean isGentoo = System.getProperty("os.version").contains("gentoo") ||
            System.getProperty("os.version").contains("hardened");  // Funtoo
    private static final boolean isAndroid;
    private static final boolean isApache;
    private static final boolean isGNU;
    private static final boolean isOpenJDK;
    private static final boolean is64;
    private static final boolean hasWrapper = System.getProperty("wrapper.version") != null;
    private static final boolean isLinuxService;
    private static final boolean isSlow;

    private static final boolean oneDotSix;
    private static final boolean oneDotSeven;
    private static final boolean oneDotEight;
    private static final boolean oneDotNine;
    private static final boolean oneDotTen;
    private static final boolean oneDotEleven;
    private static final boolean oneDotTwelve;
    private static final boolean oneDotThirteen;
    private static final boolean oneDotFourteen;

    private static final int androidSDK;

    static {
        boolean is64Temp = "64".equals(System.getProperty("sun.arch.data.model")) ||
                System.getProperty("os.arch").contains("64");
        if (isWin && !is64Temp) {
            // http://stackoverflow.com/questions/4748673/how-can-i-check-the-bitness-of-my-os-using-java-j2se-not-os-arch
            // http://blogs.msdn.com/b/david.wang/archive/2006/03/26/howto-detect-process-bitness.aspx
            String arch = System.getenv("PROCESSOR_ARCHITECTURE");
            String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
            is64Temp = (arch != null && arch.endsWith("64")) ||
                    (wow64Arch != null && wow64Arch.endsWith("64"));
        }
        is64 = is64Temp;

        String vendor = System.getProperty("java.vendor");
        isAndroid = vendor.contains("Android");
        isApache = vendor.startsWith("Apache");
        isGNU = vendor.startsWith("GNU Classpath") ||               // JamVM
                vendor.startsWith("Free Software Foundation");      // gij
        String runtime = System.getProperty("java.runtime.name");
        isOpenJDK = runtime != null && runtime.contains("OpenJDK");
        isLinuxService = isLinux || !isWin && !isMac && !isAndroid;
        isSlow = isAndroid || isApache || isArm || isGNU || getMaxMemory() < 48*1024*1024L;

        int sdk = 0;
        if (isAndroid) {
            try {
                Class<?> ver = Class.forName("android.os.Build$VERSION", true, ClassLoader.getSystemClassLoader());
                Field field = ver.getField("SDK_INT");
                sdk = field.getInt(null);
            } catch (Exception e) {}
        }
        androidSDK = sdk;

        if (isAndroid) {
            oneDotSix = androidSDK >= 9;
            oneDotSeven = androidSDK >= 19;
            // https://developer.android.com/guide/platform/j8-jack.html
            // some stuff in 23, some in 24
            oneDotEight = androidSDK >= 26;
            oneDotNine = false;
            oneDotTen = false;
            oneDotEleven = false;
            oneDotTwelve = false;
            oneDotThirteen = false;
            oneDotFourteen = false;
        } else {
            String version = System.getProperty("java.version");
            // handle versions like "8-ea" or "9-internal"
            if (!version.startsWith("1."))
                version = "1." + version;
            oneDotSix = VersionComparator.comp(version, "1.6") >= 0;
            oneDotSeven = oneDotSix && VersionComparator.comp(version, "1.7") >= 0;
            oneDotEight = oneDotSeven && VersionComparator.comp(version, "1.8") >= 0;
            oneDotNine = oneDotEight && VersionComparator.comp(version, "1.9") >= 0;
            oneDotTen = oneDotNine && VersionComparator.comp(version, "1.10") >= 0;
            oneDotEleven = oneDotTen && VersionComparator.comp(version, "1.11") >= 0;
            oneDotTwelve = oneDotEleven && VersionComparator.comp(version, "1.12") >= 0;
            oneDotThirteen = oneDotTwelve && VersionComparator.comp(version, "1.13") >= 0;
            oneDotFourteen = oneDotThirteen && VersionComparator.comp(version, "1.14") >= 0;
        }
    }

    public static boolean isLinux() {return isLinux;}

    public static boolean isWindows() {
        return isWin;
    }

    public static boolean isMac() {
        return isMac;
    }

    public static boolean isAndroid() {
        return isAndroid;
    }

    /**
     *  Apache Harmony JVM, or Android
     */
    public static boolean isApache() {
        return isApache || isAndroid;
    }

    /**
     *  gij or JamVM with GNU Classpath
     */
    public static boolean isGNU() {
        return isGNU;
    }

    /**
     *  @since 0.5.0
     */
    public static boolean isGentoo() {
        return isGentoo;
    }

    /**
     *  @since 0.5.0
     */
    public static boolean isOpenJDK() {
        return isOpenJDK;
    }

    /**
     *  @since 0.5.0
     */
    public static boolean isARM() {
        return isArm;
    }

    /**
     *  @since 0.5.0
     */
    public static boolean isX86() {
        return isX86;
    }

    /**
     *  Our best guess on whether this is a slow architecture / OS / JVM,
     *  using some simple heuristics.
     *
     */
    public static boolean isSlow() {
        // we don't put the NBI call in the static field,
        // to prevent a circular initialization with NBI.
//        return isSlow || !NativeBigInteger.isNative();
        return isSlow;
    }

    /**
     *  Better than (new VersionComparator()).compare(System.getProperty("java.version"), "1.6") &gt;= 0
     *  as it handles Android also, where java.version = "0".
     *
     *  @return true if Java 1.6 or higher, or Android API 9 or higher
     */
    public static boolean isJava6() {
        return oneDotSix;
    }

    /**
     *  Better than (new VersionComparator()).compare(System.getProperty("java.version"), "1.7") &gt;= 0
     *  as it handles Android also, where java.version = "0".
     *
     *  @return true if Java 1.7 or higher, or Android API 19 or higher
     *  @since 0.9.14
     */
    public static boolean isJava7() {
        return oneDotSeven;
    }

    /**
     *
     *  @return true if Java 1.8 or higher, or Android API 26 or higher.
     *  @since 0.5.2
     */
    public static boolean isJava8() {
        return oneDotEight;
    }

    /**
     *
     *  @return true if Java 1.9 or higher, false for Android.
     *  @since 0.5.2
     */
    public static boolean isJava9() {
        return oneDotNine;
    }

    /**
     *
     *  @return true if Java 1.10 or higher, false for Android.
     *  @since 0.5.2
     */
    public static boolean isJava10() { return oneDotTen; }

    /**
     *
     *  @return true if Java 1.11 or higher, false for Android.
     *  @since 0.5.2
     */
    public static boolean isJava11() { return oneDotEleven; }

    /**
     *
     *  @return true if Java 1.12 or higher, false for Android.
     *  @since 0.5.2
     */
    public static boolean isJava12() { return oneDotTwelve; }

    /**
     * This isn't always correct.
     * http://stackoverflow.com/questions/807263/how-do-i-detect-which-kind-of-jre-is-installed-32bit-vs-64bit
     * http://mark.koli.ch/2009/10/javas-osarch-system-property-is-the-bitness-of-the-jre-not-the-operating-system.html
     * http://mark.koli.ch/2009/10/reliably-checking-os-bitness-32-or-64-bit-on-windows-with-a-tiny-c-app.html
     * sun.arch.data.model not on all JVMs
     * sun.arch.data.model == 64 =&gt; 64 bit processor
     * sun.arch.data.model == 32 =&gt; A 32 bit JVM but could be either 32 or 64 bit processor or libs
     * os.arch contains "64" could be 32 or 64 bit libs
     */
    public static boolean is64Bit() {
        return is64;
    }

    /**
     *  @since 0.5.0
     */
    public static boolean isLinuxService() {
        return isLinuxService;
    }

    /**
     *  Identical to android.os.Build.VERSION.SDK_INT.
     *  For use outside of Android code.
     *  @return The SDK (API) version, e.g. 8 for Froyo, 0 if unknown
     */
    public static int getAndroidVersion() {
        return androidSDK;
    }

    /**
     *  Is the wrapper present?
     */
    public static boolean hasWrapper() {
        return hasWrapper;
    }

    /**
     *  Runtime.getRuntime().maxMemory() but check for
     *  bogus values
     *  @since 0.5.0
     */
    public static long getMaxMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        if (maxMemory >= Long.MAX_VALUE / 2)
            maxMemory = 96*1024*1024L;
        return maxMemory;
    }

    /**
     *  System Time Zone is set in JVM by Router by what's in its 1m5.config file
     *  which currently is UTC then saves it to the system property
     *  1m5.systemTimeZone.
     *
     *  @return non-null
     *  @since 0.5.0
     */
    public static TimeZone getSystemTimeZone() {
        String systemTimeZone = System.getProperty("1m5.systemTimeZone");
        if (systemTimeZone != null)
            return TimeZone.getTimeZone(systemTimeZone);
        return TimeZone.getDefault();
    }

    /**
     * Prior to setting the time zone to the system time zone, the Router
     * grabs the current users Time Zone and places it in the system
     * property 1m5.userTimeZone. This method retrieves the value
     * of this parameter to support user Locales.
     *
     * @return non-null
     * @since 0.6.3
     */
    public static TimeZone getUserTimeZone() {
        String userTimeZone = System.getProperty("1m5.userTimeZone");
        if(userTimeZone != null)
            return TimeZone.getTimeZone(userTimeZone);
        return TimeZone.getDefault();
    }

    /**
     *  @since 0.5.0
     */
    public static void main(String[] args) {
        System.out.println("64 bit   : " + is64Bit());
        System.out.println("Java 6   : " + isJava6());
        System.out.println("Java 7   : " + isJava7());
        System.out.println("Java 8   : " + isJava8());
        System.out.println("Java 9   : " + isJava9());
        System.out.println("Java 10  : " + isJava10());
        System.out.println("Java 11  : " + isJava11());
        System.out.println("Java 12  : " + isJava12());
        System.out.println("Android  : " + isAndroid());
        if (isAndroid())
            System.out.println("  Version: " + getAndroidVersion());
        System.out.println("Apache   : " + isApache());
        System.out.println("ARM      : " + isARM());
        System.out.println("Gentoo   : " + isGentoo());
        System.out.println("GNU      : " + isGNU());
        System.out.println("Linux Svc: " + isLinuxService());
        System.out.println("Mac      : " + isMac());
        System.out.println("OpenJDK  : " + isOpenJDK());
        System.out.println("Slow     : " + isSlow());
        System.out.println("Windows  : " + isWindows());
        System.out.println("Wrapper  : " + hasWrapper());
        System.out.println("x86      : " + isX86());
        System.out.println("Max mem  : " + getMaxMemory());

    }
}
