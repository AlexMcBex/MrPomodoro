# Mr Pomodoro

![MrPomodoro](./app/src/main/res/drawable/tomato_image_png.png)

Mr Pomodoro is a productivity app that uses the Pomodoro Technique to help users manage their time more effectively.

## Features

* 25-minute work intervals followed by 5-minute rest intervals
* Customize your work and rest times according to your preferences
* Receive a motivational advice from [Advice Slip API](https://api.adviceslip.com/) after each work interval

## Setup

### Prerequisites

* Android SDK v30
* Android Build Tools v30.0.2
* Android Support Repository

### Installation

1. Clone this repository and import into **Android Studio**
```sh
git clone https://github.com/alexmcbex/MrPomodoro.git
```
2. Go to Gradle Scripts, then to **build.gradle (Module: app)**.

3. Look for **minSdkVersion** and change it to whichever version you have.

### Running the App

To run the app, you need to have an Android device or emulator connected. Then, in Android Studio, click on the "Run" button.

## Built With

* [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - For asynchronous and more..
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 

## Contributing

Contributions are welcome! Please read the [contributing guidelines](link_to_your_contributing_guidelines.md) first.

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

```
Remember to replace the "YourUsername" in the git clone command with your actual GitHub username. Also, you might want to replace the "link_to_your_contributing_guidelines.md" with an actual link if you have one.