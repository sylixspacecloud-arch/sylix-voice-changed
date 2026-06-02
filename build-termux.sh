#!/bin/bash

# Sylix Voice Changer - Termux Build Script (Optimized)
# This script automates the entire build process for Termux on Android phones

echo "=========================================="
echo "Sylix Voice Changer - Termux Build Script"
echo "=========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Step 1: Update package manager
echo -e "${YELLOW}Step 1: Updating package manager...${NC}"
apt update -y
if [ $? -ne 0 ]; then
    echo -e "${RED}Error: Failed to update package manager${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Package manager updated${NC}"
echo ""

# Step 2: Install required packages
echo -e "${YELLOW}Step 2: Installing required packages...${NC}"
echo "This may take a few minutes..."

# Install gradle (which includes Java)
apt install -y gradle
if [ $? -ne 0 ]; then
    echo -e "${RED}Error: Failed to install gradle${NC}"
    exit 1
fi

# Also install git if not already installed
apt install -y git
echo -e "${GREEN}✓ Required packages installed${NC}"
echo ""

# Step 3: Verify installation
echo -e "${YELLOW}Step 3: Verifying installation...${NC}"
gradle --version
if [ $? -ne 0 ]; then
    echo -e "${RED}Error: Gradle not properly installed${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Gradle verified${NC}"
echo ""

# Step 4: Build the APK
echo -e "${YELLOW}Step 4: Building APK...${NC}"
echo "This will take 10-15 minutes. Please wait..."
echo "Keep your phone plugged in and don't close Termux."
echo ""

# Make gradlew executable
chmod +x ./gradlew

# Set Termux-specific environment variables for better compatibility
export GRADLE_OPTS="-Xmx512m -Xms256m"
export JAVA_OPTS="-Xmx512m -Xms256m"

# Run the build with Termux-optimized settings
./gradlew assembleDebug --no-daemon

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}=========================================="
    echo "✓ BUILD SUCCESSFUL!"
    echo "==========================================${NC}"
    echo ""
    echo "Your APK is ready at:"
    echo -e "${GREEN}app/build/outputs/apk/debug/app-debug.apk${NC}"
    echo ""
    echo "Next steps:"
    echo "1. Open the APK file with your file manager"
    echo "2. Tap to install"
    echo "3. Grant permissions when prompted"
    echo "4. Launch Sylix Voice Changer!"
    echo ""
    echo "APK file location:"
    ls -lh app/build/outputs/apk/debug/app-debug.apk
    echo ""
else
    echo ""
    echo -e "${RED}=========================================="
    echo "✗ BUILD FAILED"
    echo "==========================================${NC}"
    echo ""
    echo "Troubleshooting:"
    echo "1. Make sure you have at least 1.6GB free storage"
    echo "2. Try running: apt update && apt upgrade -y"
    echo "3. Close other apps to free up memory"
    echo "4. Then run this script again"
    echo ""
    echo "If it still fails, try manual build:"
    echo "  chmod +x ./gradlew"
    echo "  ./gradlew assembleDebug --no-daemon"
    exit 1
fi
