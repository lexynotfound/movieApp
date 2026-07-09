# MovieAssessmentApp

A native Android application for browsing movies using The Movie Database (TMDB) API.
Built as a technical assessment for frontend mobile.

---

## Tech Stack

| Category | Library |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt 2.59.2 + KSP 2.2.10-2.0.2 |
| Network | Retrofit + OkHttp |
| Image Loading | Coil 3 |
| Paging | Paging 3 |
| Async | Kotlin Coroutines + Flow / StateFlow |
| Navigation | Navigation Compose |
| Testing | JUnit 4, MockK, kotlinx-coroutines-test |

---

## Architecture

```
UI (Compose) → ViewModel → UseCase → Repository Interface → Repository Impl → DataSource → API Service
```

**Layers:**
- `presentation/` — Composable screens, ViewModels, UiState sealed interfaces
- `domain/` — Pure Kotlin models, repository interfaces, use cases (no Android/Retrofit imports)
- `data/` — DTOs, API service, data sources, mappers, paging sources, repository implementation
- `di/` — Hilt modules: NetworkModule, RepositoryModule
- `core/` — Navigation, shared UI components, constants, common state wrappers

---

## Folder Structure

```
app/src/main/java/com/coorvo/movieapp/
├── MovieAssessmentApplication.kt
├── MainActivity.kt
├── core/
│   ├── common/       Constants.kt, UiState.kt
│   ├── navigation/   Screen.kt, AppNavHost.kt
│   └── ui/component/ LoadingView, ErrorView, EmptyView, MovieCard, GenreItem, ReviewItem
├── data/
│   ├── mapper/       GenreMapper, MovieMapper, MovieDetailMapper, ReviewMapper, TrailerMapper
│   ├── paging/       MoviePagingSource, ReviewPagingSource
│   ├── remote/
│   │   ├── api/      TmdbApiService
│   │   ├── datasource/ MovieRemoteDataSource (interface + impl)
│   │   └── dto/      GenreDto, MovieDto, MovieDetailDto, ReviewDto, VideoDto + response DTOs
│   └── repository/   MovieRepositoryImpl
├── di/               NetworkModule, RepositoryModule, UseCaseModule
├── domain/
│   ├── model/        Genre, Movie, MovieDetail, Review, Trailer
│   ├── repository/   MovieRepository (interface)
│   └── usecase/      GetGenresUseCase, GetMoviesByGenreUseCase, GetMovieDetailUseCase,
│                     GetMovieReviewsUseCase, GetMovieTrailerUseCase
└── presentation/
    ├── genre/        GenreUiState, GenreViewModel, GenreScreen
    ├── movies/       MoviesUiState, MoviesViewModel, MoviesScreen
    └── detail/       MovieDetailUiState, MovieDetailViewModel, MovieDetailScreen
```

---

## Setup: TMDB API Key

1. Get a free API key at [https://www.themoviedb.org/settings/api](https://www.themoviedb.org/settings/api)
2. Open `local.properties` in the project root (this file is NOT committed to version control)
3. Add the following line:
   ```
   TMDB_API_KEY=your_actual_api_key_here
   ```
4. Sync Gradle

> **Security note:** `local.properties` is listed in `.gitignore`. Never commit your API key.

---

## Running the Project

**Requirements:**
- Android Studio Hedgehog or newer
- JDK 11+
- Android SDK 36
- A physical device or emulator running Android 7.0+ (API 24)

**Steps:**
1. Clone the repository
2. Set up your TMDB API key (see above)
3. Open the project in Android Studio
4. Click **Run** or use `./gradlew assembleDebug`

---

## Features

- **Genre list** — Browse all official TMDB movie genres
- **Movie list** — Discover movies filtered by selected genre with endless scrolling (Paging 3)
- **Movie detail** — View poster/backdrop, title, overview, release date, rating, runtime, and genre chips
- **Watch Trailer** — Opens the official YouTube trailer in YouTube or browser
- **User reviews** — Read reviews with endless scrolling (Paging 3)
- **Loading states** — Spinner on initial load
- **Error states** — Error message + Retry button on failure
- **Empty states** — Friendly message when no data is returned

---

## Positive and Negative Cases Handled

| Case | Where |
|---|---|
| Genre list loaded successfully | GenreScreen |
| No genres returned | GenreScreen → EmptyView |
| Network error on genre fetch | GenreScreen → ErrorView + Retry |
| Movies load with paging | MoviesScreen (LazyVerticalGrid) |
| No movies for selected genre | MoviesScreen → EmptyView |
| Movies fail to load | MoviesScreen → ErrorView + Retry |
| Movie detail loads fully | MovieDetailScreen |
| Null poster/backdrop path | AsyncImage gracefully handles null |
| Trailer available | "Watch Trailer" button shown |
| No trailer available | Button hidden |
| Reviews load with paging | MovieDetailScreen |
| No reviews for movie | ReviewItem section → EmptyView |
| Reviews fail to load | Review section → ErrorView + Retry |
| API key not set | App shows error on genre load (HTTP 401), does not crash |

---

## Testing

Unit tests are in `app/src/test/`:

| Test File | Coverage |
|---|---|
| `GetGenresUseCaseTest` | Success, failure (IOException), empty list |
| `GetMovieDetailUseCaseTest` | Success, HTTP failure, null poster path |
| `MovieRepositoryImplTest` | Success mapping, IOException, empty genres |
| `GenreMapperTest` | DTO → domain mapping, empty list, id preservation |

Run tests:
```bash
./gradlew test
```
