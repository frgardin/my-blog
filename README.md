# Software Engineering Blog

A Spring Boot blog application with a Notion-inspired design, built with Java 25 and the latest Spring Boot LTS version.

## Features

- **Modern Tech Stack**: Java 25, Spring Boot 4.0.0 LTS, Thymeleaf
- **Markdown Support**: Write articles in Markdown with automatic HTML conversion
- **Notion-Inspired Design**: Clean, minimalist interface inspired by Notion's aesthetic
- **Responsive Design**: Works seamlessly on desktop and mobile devices
- **Database Integration**: MySQL database with JPA
- **Sample Content**: Pre-populated with sample blog articles
- **Comprehensive Testing**: Full unit test coverage with JUnit 5 and Mockito

## Project Structure

```
src/
├── main/
│   ├── java/com/example/blog/
│   │   ├── BlogApplication.java          # Main application class
│   │   ├── config/
│   │   │   └── DataInitializer.java     # Sample data initialization
│   │   ├── controller/
│   │   │   └── BlogController.java       # Web controllers
│   │   ├── dto/
│   │   │   └── ArticleDTO.java          # Data transfer object
│   │   ├── mapper/
│   │   │   └── ArticleMapper.java        # Entity-DTO mapper
│   │   ├── model/
│   │   │   └── Article.java              # JPA entity
│   │   ├── repository/
│   │   │   └── ArticleRepository.java    # JPA repository
│   │   └── service/
│   │       ├── ArticleService.java        # Business logic
│   │       └── MarkdownService.java      # Markdown processing
│   └── resources/
│       ├── templates/
│       │   ├── index.html                # Home page
│       │   ├── article.html              # Article detail page
│       │   └── about.html                # About page
│       ├── static/
│       │   └── css/
│       │       └── style.css             # Notion-inspired styling
│       └── application.properties        # Application configuration
└── test/
    └── java/com/example/blog/
        ├── controller/
        │   └── BlogControllerTest.java   # Controller tests
        ├── dto/
        │   └── ArticleDTOTest.java       # DTO tests
        ├── mapper/
        │   └── ArticleMapperTest.java    # Mapper tests
        ├── model/
        │   └── ArticleTest.java           # Entity tests
        ├── repository/
        │   └── ArticleRepositoryTest.java # Repository tests
        ├── service/
        │   ├── ArticleServiceTest.java    # Service tests
        │   └── MarkdownServiceTest.java  # Markdown service tests
        └── resources/
            └── application-test.properties # Test configuration
```

## Getting Started

### Prerequisites

- Java 25 or later
- Maven 3.6 or later

### Running the Application

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd my-blog
   ```

2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

3. Open your browser and navigate to:
   - Home page: http://localhost:8080
   - About page: http://localhost:8080/about

### Running Tests

Run the unit test suite:
```bash
mvn test
```

The application includes comprehensive unit tests with 46 tests covering:
- Model entities and DTOs
- Service layer business logic
- Controller endpoints
- Mapper functionality
- Markdown processing

### Database Configuration

The application uses MySQL for production. For testing, it uses an in-memory H2 database configured in `src/test/resources/application-test.properties`.

## Adding New Articles

New articles can be added through the MySQL database or by extending the application with an admin interface. Each article requires:

- **title**: Article title
- **slug**: URL-friendly identifier (unique)
- **content**: Markdown content
- **summary**: Brief description for listing pages
- **published**: Boolean flag for visibility

## Customization

### Styling

The CSS is located in `src/main/resources/static/css/style.css` and uses CSS variables for easy customization:

```css
:root {
    --notion-bg: #ffffff;
    --notion-accent: #2383e2;
    /* Add your custom colors */
}
```

### Templates

Thymeleaf templates are in `src/main/resources/templates/`:
- `index.html`: Blog listing page
- `article.html`: Individual article view
- `about.html`: About page

## Technologies Used

- **Java 25**: Latest Java features and performance improvements
- **Spring Boot 4.0.0**: Latest LTS version with long-term support
- **Spring Web**: RESTful web services
- **Spring Data JPA**: Database abstraction layer
- **Thymeleaf**: Server-side templating engine
- **MySQL**: Production database
- **H2 Database**: In-memory database for testing
- **CommonMark**: Markdown parsing and rendering
- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework for unit tests
- **Maven**: Build and dependency management

## Design Inspiration

The design is inspired by Notion's clean, minimalist aesthetic with:
- Subtle borders and shadows
- Thoughtful typography and spacing
- Neutral color palette with accent colors
- Responsive grid layouts
- Smooth transitions and hover effects

## Future Enhancements

- Admin interface for article management
- Search functionality (repository methods already implemented)
- Tag/category system
- Comment system
- RSS feed
- Social sharing buttons
- Dark mode support
- Integration tests for repository layer
- Performance optimization with caching

## License

This project is open source and available under the MIT License.
