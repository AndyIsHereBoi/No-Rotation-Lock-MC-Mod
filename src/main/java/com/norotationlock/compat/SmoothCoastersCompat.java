package com.norotationlock.compat;

public final class SmoothCoastersCompat {
    private SmoothCoastersCompat() {}

    /**
     * Returns true if SmoothCoasters mod is present and camera rotation toggle is enabled.
     * Uses reflection to avoid a hard dependency.
     */
    public static boolean isActive() {
        try {
            Class<?> clazz = Class.forName("me.m56738.smoothcoasters.SmoothCoasters");
            java.lang.reflect.Method getInstance = clazz.getMethod("getInstance");
            Object instance = getInstance.invoke(null);
            if (instance == null) return false;
            java.lang.reflect.Method getRotationToggle = clazz.getMethod("getRotationToggle");
            Object res = getRotationToggle.invoke(instance);
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
            Class<?> clazz = Class.forName("me.m56738.smoothcoasters.SmoothCoasters");
            java.lang.reflect.Method getInstance = clazz.getMethod("getInstance");
            Object instance = getInstance.invoke(null);
            if (instance == null) return;
            java.lang.reflect.Method setLimit = clazz.getMethod("setRotationLimit", float.class, float.class, float.class, float.class);
            if (enabled) {
                // Pass values that result in scHasLimit == false in SmoothCoasters
                setLimit.invoke(instance, -360f, 360f, -180f, 180f);
            } else {
                // Restore reasonable defaults
                setLimit.invoke(instance, -180f, 180f, -90f, 90f);
            }
        } catch (Throwable ignored) {
            // SmoothCoasters not present or API changed; ignore
        }
    }
}
