# Discord Integration Guide

## Overview

Sylix Voice Changer integrates with Discord to provide seamless voice conversion during Discord voice calls. This guide explains how the integration works, its limitations, and how to set it up.

## How It Works

### Detection

The app automatically detects:

1. **Discord Installation:** Checks if Discord app is installed via PackageManager
2. **Discord Running:** Monitors if Discord process is active via ActivityManager
3. **Voice Session:** Attempts to detect active Discord voice calls

### Voice Selection

When Discord is detected as running:

1. A popup appears with available voice presets
2. User selects desired voice
3. Selected voice is used for subsequent audio processing
4. Voice can be changed mid-call with quick selector

### Audio Routing

Due to Android security model limitations, direct microphone routing to Discord is not possible. Instead:

1. **Input:** Capture microphone audio in Sylix app
2. **Processing:** Convert audio using selected voice preset
3. **Output:** Play converted audio through speaker
4. **Discord Pickup:** Discord captures speaker output as microphone input

## Setup Instructions

### Step 1: Install Discord

Ensure Discord is installed on your Android device:

1. Open Google Play Store
2. Search for "Discord"
3. Install official Discord app

### Step 2: Enable Microphone Permission

1. Open Sylix Voice Changer
2. Grant microphone permission when prompted
3. Verify permission in Settings → Apps → Sylix Voice Changer → Permissions

### Step 3: Configure System Audio Settings

**Important:** Configure these settings for Discord to capture the converted voice:

#### Android 10-11

1. Open **Settings → Sound & Vibration**
2. Tap **Advanced**
3. Enable **Mono Audio** (optional, for better clarity)
4. Go back to **Settings → Apps → Special App Access**
5. Tap **Modify System Settings**
6. Enable Sylix Voice Changer

#### Android 12+

1. Open **Settings → Sound & Vibration**
2. Tap **Advanced**
3. Look for **Audio Routing** or **Microphone** settings
4. Configure to allow app audio routing

### Step 4: Test Setup

1. Open Discord app
2. Join a voice channel
3. Open Sylix Voice Changer
4. Select a voice preset
5. Tap microphone button to start voice conversion
6. Speak and listen to converted voice in Discord

## Voice Presets for Discord

| Preset | Best For | Latency |
|--------|----------|---------|
| Realistic Female | General use, professional | Low |
| Soft Female | Casual conversations | Low |
| Young Female | Gaming, casual | Low |
| Anime Girl | Gaming, entertainment | Medium |
| Deep Female | Serious discussions | Low |
| Male | General use | Low |
| Deep Male | Authority, serious tone | Low |
| Kid | Fun, entertainment | Medium |
| Robot | Gaming, sci-fi | Medium |
| Demon | Gaming, entertainment | Medium |

## Quick Voice Switching

During a Discord call:

1. Tap the voice preset carousel at top of Home screen
2. Select new voice
3. Voice changes immediately for next audio input
4. No need to restart call

## Troubleshooting

### Discord Not Detected

**Problem:** App doesn't detect Discord is running

**Solutions:**
1. Ensure Discord is installed and running
2. Check if Discord has necessary permissions
3. Restart Discord app
4. Restart Sylix Voice Changer

**Diagnostic:**
- Open Diagnostics screen
- Check "Discord Status" section
- Verify "Discord Installed" shows "Yes"

### No Audio Output

**Problem:** Converted voice not heard in Discord

**Solutions:**
1. Verify microphone permission is granted
2. Check system audio settings (see Setup Step 3)
3. Ensure speaker is not muted
4. Test audio in Diagnostics → Audio Pipeline
5. Restart both apps

### High Latency

**Problem:** Noticeable delay between speaking and hearing voice

**Solutions:**
1. Select lower-latency voice preset (Realistic Female, Male)
2. Close other apps to free RAM
3. Reduce buffer size in Settings
4. Enable GPU acceleration in Settings
5. Use device with better processor (Snapdragon 800+)

### Voice Sounds Robotic

**Problem:** Converted voice has artifacts or sounds unnatural

**Solutions:**
1. Try different voice preset
2. Adjust microphone input level in Settings
3. Enable noise suppression in Settings
4. Speak more clearly and at normal pace
5. Check if Discord audio quality is set to high

### Discord Crashes

**Problem:** Discord crashes when using Sylix

**Solutions:**
1. Update Discord to latest version
2. Update Android OS to latest version
3. Clear Discord cache: Settings → Apps → Discord → Storage → Clear Cache
4. Disable Sylix audio routing temporarily
5. Report issue to Discord support

## Android Limitations

### Why Direct Microphone Routing Isn't Possible

Android's security model prevents direct microphone routing to other apps:

1. **Permission Isolation:** Each app has isolated microphone access
2. **Audio Focus:** Only one app can use microphone at a time
3. **No Audio Injection:** Apps cannot inject audio into other apps' microphone input
4. **SELinux Restrictions:** Kernel-level security prevents workarounds

### Workarounds Implemented

Sylix provides these alternatives:

1. **Speaker Output Capture:** Discord captures converted audio from speaker
2. **Voice Selection Popup:** Quick voice switching without leaving Discord
3. **Low Latency Processing:** Minimizes delay in audio conversion
4. **Automatic Detection:** Detects when Discord is active

### Future Solutions

Potential improvements (if Android allows):

1. **Accessibility Service:** May allow audio routing (requires user permission)
2. **Audio Plugin API:** If Google releases official API
3. **Custom ROM Support:** Devices with modified Android
4. **Bluetooth Loopback:** Route audio through Bluetooth (experimental)

## Advanced Configuration

### Custom Audio Routing

For advanced users with rooted devices:

1. Install Magisk module for audio routing
2. Configure in Sylix Settings → Advanced
3. Enable "Custom Audio Routing"
4. Test in Diagnostics

**Warning:** Rooting device voids warranty and may cause instability.

### Performance Tuning

In Settings → Performance:

1. **Processing Latency:** Low/Balanced/High Quality
2. **Buffer Size:** Small/Medium/Large
3. **GPU Acceleration:** On/Off
4. **Thread Priority:** Normal/High
5. **Memory Mode:** Aggressive/Normal/Conservative

## Discord Integration Checklist

- [ ] Discord installed and running
- [ ] Microphone permission granted to Sylix
- [ ] System audio settings configured
- [ ] Voice preset selected
- [ ] Microphone button working
- [ ] Audio output heard in Discord
- [ ] Voice conversion working correctly
- [ ] Latency acceptable (<100ms)
- [ ] No crashes or errors

## Support & Feedback

### Report Issues

1. Open Diagnostics screen
2. Tap "Export Logs"
3. Share logs with support
4. Include: Device model, Android version, Discord version

### Request Features

1. Open Settings
2. Tap "Send Feedback"
3. Describe desired feature
4. Include use case

## FAQ

**Q: Can I use Sylix with other voice apps?**

A: Yes! Sylix works with any app that uses microphone input (Telegram, WhatsApp, Skype, etc.)

**Q: Does Sylix work with Discord on PC?**

A: No, this is an Android-only app. For PC, use alternative voice changers.

**Q: Can I use multiple voice presets simultaneously?**

A: No, only one voice can be active at a time. Switch voices using the preset selector.

**Q: Does voice conversion work in Discord DMs?**

A: Yes, voice conversion works in both group calls and DMs.

**Q: Can I record my converted voice?**

A: Yes, use the Live Test screen to record and save audio.

**Q: Is my voice data stored or uploaded?**

A: No, all processing is local. No data is sent to servers.

**Q: Can I use Sylix while streaming on Discord?**

A: Yes, but ensure you have permission from other call participants.

---

For more information, see:
- `README.md` - Project overview
- `ARCHITECTURE.md` - Technical architecture
- `AUDIO_PROCESSING.md` - Audio processing details
