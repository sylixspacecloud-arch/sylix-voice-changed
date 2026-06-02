# Sylix Voice Changer - Quick Start Guide

## ⚡ Fastest Way to Get the APK (No Building!)

Since building on your phone has compatibility issues, here are the **easiest alternatives**:

### Option 1: Use Appetize.io (Recommended - Easiest!)

Appetize.io can build Android apps directly from GitHub:

1. Go to: **https://appetize.io**
2. Click **"Build from GitHub"**
3. Enter your repo: `sylixspacecloud-arch/sylix-voice-changed`
4. It will build automatically
5. Download the APK

**Time:** ~5-10 minutes  
**Difficulty:** ⭐ (Very Easy)

---

### Option 2: Use BrowserStack App Live

1. Go to: **https://www.browserstack.com/app-live**
2. Upload your GitHub repo
3. They'll build the APK for you
4. Download when ready

**Time:** ~10-15 minutes  
**Difficulty:** ⭐ (Very Easy)

---

### Option 3: Use Codemagic (Free Tier)

1. Go to: **https://codemagic.io**
2. Sign up with GitHub
3. Connect your repo
4. Click "Build"
5. Download APK from build artifacts

**Time:** ~15-20 minutes  
**Difficulty:** ⭐⭐ (Easy)

---

### Option 4: Ask a Friend with a PC

If you know someone with a computer:

1. They clone the repo: `git clone https://github.com/sylixspacecloud-arch/sylix-voice-changed.git`
2. They install Android Studio
3. They build the APK
4. They send you the APK file

**Time:** ~30 minutes  
**Difficulty:** ⭐⭐ (Moderate)

---

## 📱 Once You Have the APK

### Installation on Your Phone:

1. **Download the APK** to your phone
2. Open **File Manager**
3. Navigate to **Downloads** folder
4. Tap the **app-debug.apk** file
5. Tap **"Install"** when prompted
6. Grant **Microphone** permission
7. Tap **"Open"** to launch the app!

---

## 🔧 What's Inside the APK

✅ **Material 3 UI** - Modern Android design  
✅ **10 Voice Presets** - Realistic Female, Soft Female, Young Female, Anime Girl, Deep Female, Male, Deep Male, Kid, Robot, Demon  
✅ **7 Screens** - Home, Voice Library, Live Test, Discord Integration, Downloads, Settings, Diagnostics  
✅ **MVVM Architecture** - Professional code structure  
✅ **ONNX Runtime Ready** - For AI voice conversion  
✅ **Discord Integration** - Detect and integrate with Discord  

---

## 📝 Project Information

- **Language:** Kotlin
- **Minimum Android:** 10 (API 24)
- **Target Android:** 14 (API 34)
- **Size:** ~50MB (after adding voice models)
- **Architecture:** MVVM + Clean Code

---

## 🚀 Recommended Next Steps

1. **Get the APK** using one of the methods above
2. **Install on your phone**
3. **Test the app** - Open it and explore
4. **Add voice models** - Place ONNX models in `app/src/main/assets/models/`
5. **Rebuild** - Use one of the cloud services again
6. **Deploy** - Submit to Google Play Store

---

## 📚 Documentation

All documentation is in the GitHub repo:
- `README.md` - Project overview
- `ARCHITECTURE.md` - System design
- `BUILD_GUIDE.md` - Detailed build instructions
- `AUDIO_PROCESSING.md` - Audio pipeline
- `DISCORD_INTEGRATION.md` - Discord setup
- `TERMUX_GUIDE.md` - Building on phone

---

## ❓ FAQ

**Q: Can I build on my phone?**  
A: Yes, but Termux has compatibility issues. Cloud services are easier.

**Q: How much does Appetize.io cost?**  
A: Free tier available! Perfect for testing.

**Q: Can I modify the code?**  
A: Yes! Edit the Kotlin files in the repo and rebuild.

**Q: How do I add voice models?**  
A: Place ONNX/TFLite files in `app/src/main/assets/models/`

**Q: Is the app free?**  
A: Yes! Open source and free to use.

---

## 🎯 Your Repository

**GitHub:** https://github.com/sylixspacecloud-arch/sylix-voice-changed

All code is ready to build. Just use one of the cloud services above to get your APK!

---

**Happy building! 🚀**
