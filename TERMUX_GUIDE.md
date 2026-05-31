# Building Sylix Voice Changer on Android Phone with Termux

This guide shows you how to build the Sylix Voice Changer APK directly on your Android phone using Termux - no PC needed!

## What You Need

- ✅ Android phone (Android 7+)
- ✅ ~1.6GB free storage space
- ✅ Termux app (free from Google Play Store)
- ✅ File manager app (usually built-in)
- ✅ ~20-30 minutes for first build

## Step 1: Install Termux

1. Open **Google Play Store** on your phone
2. Search for **"Termux"**
3. Install the official Termux app (by Fredrik Fornwall)
4. Open Termux when installation completes

You should see a black terminal screen with `~$` prompt.

## Step 2: Download the Project

### Option A: Using the ZIP File (Easiest)

1. Download `sylix-voice-changer.zip` to your phone
2. Use your file manager to extract it to `/sdcard/Download/`
3. In Termux, type:
   ```bash
   cd /sdcard/Download/sylix-voice-changer
   ```

### Option B: Using Git

In Termux, type:

```bash
apt update
apt install -y git
git clone https://github.com/your-username/sylix-voice-changer.git
cd sylix-voice-changer
```

## Step 3: Run the Build Script (Easiest Way!)

I've created an automatic build script for you. Just run:

```bash
bash build-termux.sh
```

This script will:
- ✅ Update package manager
- ✅ Install Gradle and Java
- ✅ Verify everything is installed
- ✅ Build the APK
- ✅ Show you where the APK is located

**Wait 5-10 minutes** for the build to complete. Your phone might get warm - that's normal!

## Step 4: Manual Build (If Script Doesn't Work)

If the script has issues, do this manually:

### Install Gradle

```bash
apt update
apt install -y gradle
```

Verify it worked:
```bash
gradle --version
```

### Build the APK

```bash
./gradlew assembleDebug
```

Wait for it to finish. You'll see "BUILD SUCCESSFUL" when done.

## Step 5: Find Your APK

After the build completes, your APK is at:

```
app/build/outputs/apk/debug/app-debug.apk
```

To find it easily:

1. Open your file manager
2. Navigate to: `sylix-voice-changer/app/build/outputs/apk/debug/`
3. You'll see `app-debug.apk`

## Step 6: Install the App

1. In your file manager, tap on `app-debug.apk`
2. Tap "Install" when prompted
3. Grant permissions when asked
4. Wait for installation to complete
5. Tap "Open" to launch Sylix Voice Changer!

## Troubleshooting

### Error: "Unable to locate package openjdk-17"

**Solution:** Use the build script which handles this automatically:
```bash
bash build-termux.sh
```

Or manually install just gradle:
```bash
apt install -y gradle
```

### Error: "Not enough storage space"

**Solution:** Free up space on your phone
- Delete unused apps
- Clear cache: Settings → Apps → Clear Cache
- Delete old downloads
- Need at least 1.6GB free

### Build takes too long or freezes

**Solution:** 
- Make sure phone isn't in low power mode
- Close other apps
- Keep phone plugged in during build
- Try again if it fails

### "Permission denied" when running script

**Solution:** Make it executable first:
```bash
chmod +x build-termux.sh
bash build-termux.sh
```

### "BUILD FAILED" message

**Solution:** Try these steps:
```bash
apt update
apt upgrade -y
cd /path/to/sylix-voice-changer
./gradlew clean
./gradlew assembleDebug
```

## Performance Tips

- **Faster builds:** Close other apps before building
- **Keep phone cool:** Don't use phone while building
- **Stable connection:** Build on WiFi if possible
- **Plug in phone:** Keep battery charged during build

## What Happens During Build

1. **Download dependencies** (~2-3 min) - Gradle downloads required libraries
2. **Compile code** (~3-5 min) - Kotlin code is compiled to Java bytecode
3. **Create APK** (~1-2 min) - APK package is created
4. **Sign APK** (~30 sec) - Debug signature is added

Total time: **5-10 minutes** (first build takes longer)

## After Installation

Once you've built and installed the app:

1. **Grant Permissions:**
   - Microphone - needed for voice capture
   - Notifications - for Discord alerts

2. **First Launch:**
   - App will initialize
   - Check Diagnostics for system info
   - Try the Home screen with microphone button

3. **Next Builds (Faster):**
   - Subsequent builds are faster (~3-5 min)
   - Gradle caches dependencies
   - Just run: `./gradlew assembleDebug`

## Uninstalling Termux

If you want to remove everything later:

1. Uninstall Termux app from Play Store
2. Everything is deleted (safe!)
3. No files left behind

## Safety & Privacy

- ✅ Termux runs in a sandbox - isolated from system
- ✅ You have full control
- ✅ Can be uninstalled anytime
- ✅ No background processes
- ✅ No data collection
- ✅ Completely safe

## Next Steps

After successfully building:

1. **Test the app** - Open Sylix Voice Changer
2. **Check Diagnostics** - Verify system info
3. **Read documentation** - See README.md for features
4. **Add voice models** - Place ONNX models in app/src/main/assets/models/

## Advanced: Rebuilding After Changes

If you modify the code and want to rebuild:

```bash
cd /path/to/sylix-voice-changer
./gradlew clean
./gradlew assembleDebug
```

Then reinstall the new APK.

## Getting Help

If you get stuck:

1. Check the error message carefully
2. Try the troubleshooting section above
3. Make sure you have enough storage
4. Try restarting Termux: `exit` then reopen app
5. Try: `apt update && apt upgrade -y`

## Summary

| Step | Command | Time |
|------|---------|------|
| 1 | Install Termux | 2 min |
| 2 | Download project | 2 min |
| 3 | Run build script | 10 min |
| 4 | Install APK | 1 min |
| **Total** | | **15 min** |

---

**That's it!** You now have Sylix Voice Changer running on your phone! 🎉

For more information about the app, see `README.md` and other documentation files.
