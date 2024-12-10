
# GeminiAiChatbotKMP

GeminiAiChatbotKMP is an app built using Kotlin Multiplatform using Jetpack Compose base UI Framework Compose Multiplatform. 
It demonstrates the use of Generative AI SDK for Kotlin Multiplatform (Currently Android and iOS). It features a simple chat based UI and experience to interact with AI.

## Libraries used


* [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/)
* [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
* [Generative AI KMP](https://github.com/PatilShreyas/generative-ai-kmp)


## Pre-requisites

* Java JDK 17+
* Latest stable version of Android Studio IDE
* Latest XCode (for iOS)
* Gemini API Key ([Click here](https://aistudio.google.com/app/apikey) to get it)

## Screenshot
<img src="https://github.com/sunildhiman90/GeminiAiChatbotKMP/blob/main/Screenshot_20240303_172017.png" data-canonical-src="https://github.com/sunildhiman90/GeminiAiChatbotKMP/blob/main/Screenshot_20240303_172017.png" width="200" height="400" />

## Setup

Clone this repository.
Open in the latest version of Android Studio.
Place your Gemini API key in [AppConfig] file as API_KEY constant.

Example:

`API_KEY=your api key`



This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
