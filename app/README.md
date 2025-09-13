# Book Finder App

Setup Instructions
- Clone the repository:
- git clone https://github.com/dhara2805/BookFinderApp.git
- Open the project in Android Studio.
- Sync Gradle to download dependencies.
- Run the app on an emulator or device with internet connection.

Architecture Explanation
- MVVM - Each feature has a Screen + ViewModel.
- Clean Architecture (partial)
- data/ - Local (Room) + Remote (Retrofit DTOs) + Repository Impl.
- domain/ - Models, Repository Interface, UseCases.
- presentation/ - Composable screens + ViewModels.
- Dependency Injection - via NetworkModule (Retrofit, DB, Repository).

API Integration
- API: OpenLibrary
- Retrofit for networking.
- Pagination handled by sending page numbers in search requests.
- Covers and details fetched using Work IDs.

Cross-Platform Discussion
- Our current app is developed for Android using Kotlin + Jetpack Compose + MVVM.
- To adapt this solution to other platforms:

iOS
- Use Kotlin Multiplatform (KMM) to share networking, repository, and database logic.
- SwiftUI can be used for UI, consuming the shared logic.

Web
- Reuse API + Repository logic in Kotlin/JS or reimplement with JavaScript (React/Angular).
- Pagination & caching remain consistent.

Desktop
- Use Jetpack Compose Multiplatform for Desktop.
- Reuse the same ViewModels, UseCases, and Repository layers directly.

This ensures maximum code reusability and minimizes duplication across platforms.


Database Design
- Room Database with:
- BookEntity
- BookDao for CRUD operations.
- Data is cached locally for offline support (details screen fully supported).

Known Limitations
- Offline support implemented only for book details, not search listing.
- Not all book descriptions are available via API.
- Unit tests and full use-case layer are not fully implemented.
- Image loading may take time due to large cover sizes.