#!/bin/bash

# ==============================================================================
# CONFIGURATION
# ==============================================================================
# Based on your previous error, this is your path:
FX_PATH="/home/knowledge/Downloads/javafx-sdk-25.0.1/lib"

# ==============================================================================
# 1. CLEANUP & PREPARATION
# ==============================================================================
echo "--- Cleaning previous build ---"
rm -rf bin
mkdir -p bin

# ==============================================================================
# 2. ASSET MANAGEMENT
# ==============================================================================
echo "--- Setting up Game Assets ---"

if [ -d "GameAssets" ]; then
    echo "Copying GameAssets to build folder..."
    # FIX: Copy content directly to bin, preserving your 'imagesandstyles' subfolder
    cp -r GameAssets/* bin/
else
    echo "WARNING: 'GameAssets' folder not found!"
fi

# Fallback: Find CSS file if it wasn't copied correctly and place it in root
FOUND_CSS=$(find src GameAssets -name "*.css" | head -n 1)
if [ -n "$FOUND_CSS" ]; then
    echo "Found CSS at: $FOUND_CSS"
    cp "$FOUND_CSS" bin/
    # Also copy to imagesandstyles just in case code looks there
    mkdir -p bin/imagesandstyles
    cp "$FOUND_CSS" bin/imagesandstyles/
fi

# ==============================================================================
# 3. COMPILATION
# ==============================================================================
echo "--- Compiling Java Source Code ---"

find src -name "*.java" > sources.txt

if [ -z "$FX_PATH" ]; then
    javac -d bin @sources.txt
else
    echo "Using JavaFX from: $FX_PATH"
    # Includes javafx.media for your sound code
    javac --module-path "$FX_PATH" --add-modules javafx.controls,javafx.fxml,javafx.media -d bin @sources.txt
fi

if [ $? -eq 0 ]; then
    echo "Compilation Successful."
    rm sources.txt
else
    echo "Compilation FAILED."
    rm sources.txt
    exit 1
fi

# ==============================================================================
# 4. EXECUTION
# ==============================================================================
echo "--- Starting Game ---"

if [ -z "$FX_PATH" ]; then
    java -cp bin main.Main
else
    # Includes javafx.media for your sound code
    java --module-path "$FX_PATH" --add-modules javafx.controls,javafx.fxml,javafx.media -cp bin main.Main
fi
