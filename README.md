# Sleep Tracker App

## Overview

The **Sleep Tracker App** is a mobile application designed to help users track and manage their sleep patterns. It allows users to record their sleep entries, view historical data, and learn about the benefits of good sleep. Built using Jetpack Compose for modern Android development, the app integrates with Google Sign-In and Firebase Authentication for secure user management.

## Features

- **Add New Sleep Entry**: Record details about your sleep, including date, sleep time, wake-up time, and duration.
- **View Sleep Data**: Access and review historical sleep entries from the past two weeks.
- **Google Sign-In Integration**: Securely sign in using Google accounts.
- **Firebase Authentication**: Manage user authentication with Firebase.

## Screens

### Home Screen

- **Learn About Sleep Benefits**: Navigate to information about the benefits of sleep.
- **Add New Sleep Entry**: Navigate to the screen where users can log a new sleep entry.
- **Sign Up**: Initiate the sign-up process with Google Sign-In.

### New Sleep Entry Screen

- **Date Picker**: Select the date of sleep.
- **Time Picker**: Select the time of sleep and wake-up time.
- **Submit**: Save the sleep entry and calculate sleep duration.
- **Reset**: Clear all input fields.

### View Sleep Data Screen

- **View Entries**: Display sleep entries from the past two weeks.

## Setup Instructions

1. **Clone the Repository**

   ```sh
   git clone https://github.com/yourusername/sleep-tracker-app.git
   cd sleep-tracker-app
   ```

2. **Open the Project**

   Open the project in Android Studio.

3. **Configure Google Sign-In**

   - Obtain a `default_web_client_id` from the [Google Cloud Console](https://console.cloud.google.com/).
   - Add your `default_web_client_id` to `res/values/strings.xml`.

4. **Sync Gradle**

   Ensure all dependencies are properly installed by syncing Gradle files.

5. **Run the App**

   Build and run the app on an emulator or physical device.

## Technologies Used

- **Jetpack Compose**: Modern toolkit for building native UI.
- **Firebase Authentication**: For managing user authentication.
- **Google Sign-In**: For secure user sign-in.
- **SharedPreferences**: For storing and retrieving user data.

## Contributing

1. **Fork the Repository**

   Create a personal fork of the repository to make changes.

2. **Create a Branch**

   Create a new branch for your changes.

   ```sh
   git checkout -b feature/new-feature
   ```

3. **Make Changes**

   Implement your feature or bug fix.

4. **Commit Changes**

   Commit your changes with a descriptive message.

   ```sh
   git commit -am 'Add new feature'
   ```

5. **Push to GitHub**

   Push your changes to your fork.

   ```sh
   git push origin feature/new-feature
   ```

6. **Create a Pull Request**

   Open a pull request from your branch to the main repository.

## License

This project is licensed under the MIT License.

## Contact

For any questions or suggestions, please reach out to me via my profile.
