# No Rotation Lock

This mod was made with mostly AI

I use this on [mcparks](https://mcparks.us). Some rides and riding entities limit your rotation angles or how far you can look side to side. When in freecam this can be annoying, so this mod was created.

## Compatibility

- Minecraft 26.1 – 26.1.2
- Fabric Loader 0.19.5 or newer
- Fabric API
- Java 25

For Minecraft 1.18 – 1.20.1, use the `1.20.1-1.18` branch.

### SmoothCoasters

Works alongside [SmoothCoasters](https://github.com/bergerhealer/SmoothCoasters) (`26.1-v1`). While you are mounted,
the mod widens SmoothCoasters' rotation limit so its clamping cannot fight free look, and restores the defaults on
dismount. The integration is entirely reflective, so SmoothCoasters remains an optional dependency and is skipped
cleanly whenever it is absent.

## Building

Requires JDK 25.

```
./gradlew build
```

The resulting jar is written to `build/libs/`.