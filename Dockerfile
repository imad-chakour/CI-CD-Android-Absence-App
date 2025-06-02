FROM ubuntu:20.04

ENV ANDROID_SDK_URL="https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip" \
    ANDROID_HOME="/opt/android-sdk" \
    PATH="${PATH}:/opt/android-sdk/cmdline-tools/latest/bin:/opt/android-sdk/platform-tools"

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
        wget unzip openjdk-17-jdk git && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir -p ${ANDROID_HOME} && \
    wget -q -O /tmp/android-sdk.zip ${ANDROID_SDK_URL} && \
    unzip -q /tmp/android-sdk.zip -d ${ANDROID_HOME}/cmdline-tools && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest && \
    chmod +x ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager && \
    rm /tmp/android-sdk.zip

RUN yes | ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager --licenses && \
    ${ANDROID_HOME}/cmdline-tools/latest/bin/sdkmanager \
        "platform-tools" \
        "platforms;android-33" \
        "build-tools;33.0.2"

WORKDIR /app
COPY . .

CMD ["./gradlew", "assembleDebug"]
