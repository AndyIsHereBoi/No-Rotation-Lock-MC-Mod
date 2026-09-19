package com.norotationlock.compat;

public final class SmoothCoastersCompat {
    private SmoothCoastersCompat() {}

    /**
     * SmoothCoasters has moved its API class between packages, so try every known location.
     * A null result means "not installed" (or the API changed beyond recognition), in which
     * case callers must silently do nothing.
     *
     * Known layouts:
     *   26.2+   me.m56738.smoothcoasters.common.SmoothCoasters  (mod split into common/fabric modules)
     *   <=26.1  me.m56738.smoothcoasters.SmoothCoasters
     */
    private static final String[] API_CLASS_NAMES = {
            "me.m56738.smoothcoasters.common.SmoothCoasters",
            "me.m56738.smoothcoasters.SmoothCoasters",
    };

    private static Class<?> findApiClass() {
        for (String name : API_CLASS_NAMES) {
            try {
                return Class.forName(name);
            } catch (Throwable ignored) {
                // Candidate absent; try the next one.
            }
        }
        return null;
    }

    /**
     * Returns true if SmoothCoasters mod is present and camera rotation toggle is enabled.
     * Uses reflection to avoid a hard dependency.
     */
    public static boolean isActive() {
        try {
            Class<?> clazz = findApiClass();
            if (clazz == null) return false;
            Object instance = clazz.getMethod("getInstance").invoke(null);
            if (instance == null) return false;
            Object res = clazz.getMethod("getRotationToggle").invoke(instance);
            if (res instanceof Boolean) {
                return (Boolean) res;
            }
        } catch (Throwable ignored) {
            // SmoothCoasters is not present or API changed
        }
        return false;
    }

    /**
     * Ask SmoothCoasters to set rotation limit to a very wide range (disables limiting)
     * or restore defaults.
     */
    public static void setUnlimitedRotation(boolean enabled) {
        try {
            Class<?> clazz = findApiClass();
            if (clazz == null) return;
            Object instance = clazz.getMethod("getInstance").invoke(null);
            if (instance == null) return;
            java.lang.reflect.Method setLimit = clazz.getMethod("setRotationLimit", float.class, float.class, float.class, float.class);
            if (enabled) {
                // These bounds make SmoothCoasters compute scHasLimit == false, i.e. no clamping:
                // scHasLimit = minYaw > -180 || maxYaw < 180 || minPitch > -90 || maxPitch < 90
                setLimit.invoke(instance, -360f, 360f, -180f, 180f);
            } else {
                // Match SmoothCoasters' own default/reset bounds.
                setLimit.invoke(instance, -180f, 180f, -90f, 90f);
            }
        } catch (Throwable ignored) {
            // SmoothCoasters not present or API changed; ignore
        }
    }
}
