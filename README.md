# SeeMenu

A web application that scans restaurant menus and returns detailed information about the dishes.

## Overview

SeeMenu allows users to upload images of restaurant menus and receive comprehensive information about the dishes, including:
- Dish names and descriptions
- Ingredients and allergens
- Nutritional information
- Pricing
- Dietary classifications (vegetarian, vegan, gluten-free, etc.)

## Project Structure

```
SeeMenu/
├── backend/           # Spring Boot API server
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/seemenu/
│   │   │   │   ├── controller/    # REST controllers
│   │   │   │   ├── service/       # Business logic
│   │   │   │   ├── model/         # Data models
│   │   │   │   ├── dto/           # Data transfer objects
│   │   │   │   └── config/        # Configuration
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend/          # Next.js React application
│   ├── app/
│   │   ├── components/
│   │   ├── page.tsx
│   │   └── layout.tsx
│   └── package.json
├── docs/              # Documentation
├── assets/            # Static assets and examples
└── README.md
```

## Tech Stack

### Backend
- **Java 17+**
- **Spring Boot 3.2.0** - RESTful API framework
- **Maven** - Build and dependency management
- **Lombok** - Reduce boilerplate code

**Future integrations:**
- OCR library (Tesseract, Google Cloud Vision)
- AI/ML for dish information extraction
- Database (PostgreSQL/MongoDB)

### Frontend
- **Next.js 16** - React framework
- **React 19** - UI library
- **TypeScript** - Type-safe JavaScript
- **Tailwind CSS** - Styling
- **Axios** - HTTP client

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and npm
- A code editor (VS Code, IntelliJ IDEA, etc.)

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The backend API will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Run the development server:
   ```bash
   npm run dev
   ```

The frontend will start on `http://localhost:3000`

## API Endpoints

### Health Check
- **GET** `/api/menu/health` - Check if the API is running

### Menu Upload
- **POST** `/api/menu/upload` - Upload a menu image for analysis
  - Request: `multipart/form-data` with file field
  - Response: JSON with dish information

Example response:
```json
{
  "success": true,
  "message": "Menu processed successfully",
  "dishes": [
    {
      "name": "Margherita Pizza",
      "description": "Classic pizza with tomato, mozzarella, and basil",
      "price": "$12.99",
      "ingredients": ["tomato", "mozzarella", "basil"],
      "allergens": ["dairy", "gluten"],
      "dietaryInfo": ["vegetarian"],
      "nutritionalInfo": "450 calories per serving"
    }
  ]
}
```

## Features

### Current
- ✅ Menu image upload interface
- ✅ File preview functionality
- ✅ Basic API structure
- ✅ Responsive UI design

### Planned
- [ ] OCR text extraction from menu images
- [ ] AI-powered dish information extraction
- [ ] Ingredient and allergen detection
- [ ] Nutritional information estimation
- [ ] Multi-language support
- [ ] Restaurant menu database
- [ ] User authentication
- [ ] Save favorite menus
- [ ] Search history

## Development

### Backend Development

The backend follows a standard Spring Boot architecture:
- **Controllers** handle HTTP requests
- **Services** contain business logic
- **Models** represent data structures
- **DTOs** transfer data between layers

To add new features:
1. Create models in `model/` package
2. Add business logic in `service/` package
3. Expose endpoints in `controller/` package

### Frontend Development

The frontend uses Next.js App Router:
- **app/page.tsx** - Main landing page
- **app/components/** - Reusable React components
- **app/layout.tsx** - Root layout

To add new components:
1. Create component in `app/components/`
2. Import and use in pages

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## Building for Production

### Backend
```bash
cd backend
mvn clean package
java -jar target/seemenu-backend-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd frontend
npm run build
npm start
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

TBD

## Future Enhancements

1. **OCR Integration** - Implement Tesseract or cloud-based OCR
2. **AI/ML Models** - Add dish recognition and classification
3. **Database** - Store processed menus and dish information
4. **Authentication** - User accounts and saved menus
5. **Mobile App** - React Native or Flutter mobile version
6. **Analytics** - Track popular dishes and user preferences
7. **Multi-language** - Support for international menus
8. **Dietary Filters** - Advanced filtering by dietary restrictions

## Contact

For questions or suggestions, please open an issue on GitHub.
