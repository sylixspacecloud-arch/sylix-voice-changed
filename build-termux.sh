#!/bin/bash

# Sylix Voice Changer - Termux Build Script (Simplified)
# Uses system gradle instead of wrapper for better Termux compatibility

echo "=========================================="
echo "Sylix Voice Changer - Termux Build"
echo "=========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Step 1: Update and install dependencies
echo -e "${YELLOW}Step 1: Updating packages...${NC}"
apt update -y
apt install -y gradle
echo -e "${GREEN}✓ Packages ready${NC}"
echo ""

# Step 2: Verify gradle
echo -e "${YELLOW}Step 2: Verifying Gradle...${NC}"
gradle --version
if [ $? -ne 0 ]; then
    echo -e "${RED}Error: Gradle not found${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Gradle verified${NC}"
echo ""

# Step 3: Build APK
echo -e "${YELLOW}Step 3: Building APK...${NC}"
echo "This will take 10-15 minutes. Please wait..."
echo "Keep your phone plugged in and don't close Termux."
echo ""

# Set environment for Termux
export GRADLE_OPTS="-Xmx512m -Xms256m"
export JAVA_OPTS="-Xmx512m -Xms256m"

# Build using system gradle directly
gradle assembleDebug --no-daemon

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}=========================================="
    echo "✓ BUILD SUCCESSFUL!"
    echo "==========================================${NC}"
    echo ""
    echo "Your APK is ready at:"
    APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
    echo -e "${GREEN}$APK_PATH${NC}"
    echo ""
    
    if [ -f "$APK_PATH" ]; then
        ls -lh "$APK_PATH"
        echo ""
        echo "Installation steps:"
        echo "1. Open your file manager"
        echo "2. Navigate to: $(pwd)/$APK_PATH"
        echo "3. Tap the APK file to install"
        echo "4. Grant permissions when prompted"
        echo "5. Open Sylix Voice Changer!"
    fi
    echo ""
else
    echo ""
    echo -e "${RED}=========================================="
    echo "✗ BUILD FAILED"
    echo "==========================================${NC}"
    echo ""
    echo "Try these steps:"
    echo "1. Close other apps to free memory"
    echo "2. Run: apt update && apt upgrade -y"
    echo "3. Run this script again"
    echo ""
    echo "Or try manual build:"
    echo "  gradle assembleDebug --no-daemon"
    exit 1
fi
