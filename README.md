# Voice Changer App

Android app that lets users **record their voice** and play it back with fun real-time effects — similar to classic voice-changer apps on the Play Store.

## Features

| Effect | Pitch | Description |
|--------|-------|-------------|
| 👧 Girl | ×1.6 | High-pitched girly voice |
| 👨 Man | ×0.75 | Deep masculine voice |
| 👩 Woman | ×1.2 | Slightly higher feminine voice |
| 🧒 Child | ×1.8 | Cute child voice |
| 👴 Old Man | ×0.6 | Slow, deep old voice |
| 🤖 Robot | ×0.9 | Flat robotic sound |
| 👽 Alien | ×2.0 | Super high alien voice |
| 😈 Devil | ×0.5 | Deep scary devil voice |
| 🧌 Giant | ×0.4 | Slowest, deepest possible |

## How it works

1. Select a voice effect from the grid
2. Tap **Record** — speak into your microphone
3. Tap **Stop** to finish recording
4. Tap **Play** to hear your voice with the selected effect applied

Voice effects are achieved by adjusting the **pitch** and **speed** of `MediaPlayer` playback via `PlaybackParams`.

## Tech Stack

- Kotlin + Jetpack Compose (Material 3)
- `MediaRecorder` / `MediaPlayer` with `PlaybackParams`
- ViewModel + StateFlow
- Min SDK 24, Target SDK 36

## Building

```bash
# Copy local.properties.example → local.properties and fill in keystore details
cp local.properties.example local.properties

./gradlew assembleDebug
```

## Releasing

Set environment variables `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` and place `keystore.jks` in the project root, then:

```bash
./gradlew bundleRelease
```
