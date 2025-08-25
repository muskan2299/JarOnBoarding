# JarOnBoarding

A modern, animated onboarding experience for Android built with Jetpack Compose.

This app showcases a stacked card UI with smooth morph animations (size, position, alignment) driven by a single progress value. It demonstrates DI with Hilt, networking with Retrofit/OkHttp, image loading with Coil, and optional HTTP caching for fast, offline\-tolerant UX.

Primary use case: provide a reference onboarding flow with high\-quality animations and production patterns.

## Architecture

- Pattern: MVVM with lightweight Clean layering
- Layers:
  - UI: Jetpack Compose components render state and handle user input
  - Presentation: ViewModels expose UI state via Coroutines/Flow
  - Data: Repositories orchestrate remote sources and map models
  - Remote: Retrofit/OkHttp API with disk cache
- Dependency Injection: Hilt (`@Module` in `di`)
- Navigation: Compose screens with in\-screen transitions

## Tech Stack and Libraries

- Kotlin, Coroutines/Flow — structured concurrency and reactive state
- Jetpack Compose, Material 3 — declarative UI and theming
- Compose Animation APIs — `animate*`, `updateTransition`, lerp\-based morphing
- Coil — `AsyncImage` for image loading and caching
- Retrofit2, OkHttp3 — REST client with disk HTTP cache
- Gson — JSON serialization for Retrofit
- Hilt — dependency injection
- Gradle Kotlin DSL — type\-safe build configuration

Why: Compose simplifies complex animations; Hilt removes manual wiring; Retrofit/OkHttp is robust for networking; Coil integrates cleanly with Compose; Coroutines/Flow keep UI state predictable.

## Project Structure

Single\-module app in `app`.

- `app/build.gradle.kts` — Android, Kotlin, Compose, dependency config
- `app/src/main/AndroidManifest.xml` — app manifest and permissions
- `app/src/main/java/.../ui/` — Compose screens, card stack, animations
- `app/src/main/java/.../data/remote/` — Retrofit `ApiService`, DTOs
- `app/src/main/java/.../data/repository/` — repository implementations
- `app/src/main/java/.../di/` — Hilt modules (e.g., `NetworkModule`)
- `app/src/main/java/.../model/` — domain/UI models
- `app/src/main/java/.../util/` — extensions and helpers
- `app/src/main/res/` — themes, colors, drawables, strings

Responsibilities:
- UI renders state and triggers intents
- ViewModels map domain data to UI state
- Repositories provide data to ViewModels
- DI modules configure and provide dependencies

## Features

- Animated onboarding cards with expand/collapse
- Smooth lerp\-based morphing of size, position, padding, and alignment
- Image loading and caching with Coil
- Network data via Retrofit/OkHttp
- Disk HTTP caching and basic offline support (configurable)
- Material 3 theming

Implementation notes:
- UI entirely in Compose
- Animations use a single progress value to synchronize transitions
- OkHttp cache and interceptors configured in `di/NetworkModule`

## Setup and Installation

Prerequisites:
- Android Studio
- JDK 17+
- Android SDK and build tools

Clone and open the project:
- Run the following from a terminal.
```bash
git clone https://github.com/muskan2299/JarOnBoarding.git
cd JarOnBoarding
```

Build from terminal (macOS):
- Use Gradle wrapper.
```bash
./gradlew clean assembleDebug
```

Open in Android Studio and Run:
- Let Gradle sync.
- Select a device/emulator and run.

Configuration:
- API base URL is defined in `app/src/main/java/**/di/NetworkModule.kt`.
- No API keys required by default.
- If using offline cache logic, ensure the permission below exists in `app/src/main/AndroidManifest.xml`.
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Usage

- Launch the app to see the onboarding card stack.
- Tap a card to expand; animations morph image and text smoothly.
- Collapsed state shows a compact summary; expanded state reveals detailed content.
- Cached responses improve load times and provide basic offline behavior.

Screenshots/GIFs:
- `docs/images/screenshot_1.png`
- `docs/images/screenshot_2.gif`

## Paths of Interest

- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/**/di/NetworkModule.kt`
- `app/src/main/java/**/ui/`
- `app/src/main/java/**/data/`
