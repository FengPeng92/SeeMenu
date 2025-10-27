# Quick Start Guide

Get SeeMenu up and running in 5 minutes!

## Prerequisites Check

Before starting, make sure you have:
- ✅ Java 17 or higher (`java -version`)
- ✅ Maven 3.6+ (`mvn -version`)
- ✅ Node.js 18+ (`node -version`)
- ✅ npm (`npm -version`)

## Step 1: Start the Backend

Open a terminal and run:

```bash
cd backend
mvn spring-boot:run
```

You should see:
```
Started SeeMenuApplication in X.XXX seconds
```

The API is now running at `http://localhost:8080`

Test it:
```bash
curl http://localhost:8080/api/menu/health
```

## Step 2: Start the Frontend

Open a **new terminal** and run:

```bash
cd frontend
npm install
npm run dev
```

You should see:
```
▲ Next.js 16.0.0
- Local:        http://localhost:3000
```

## Step 3: Try It Out!

1. Open your browser to `http://localhost:3000`
2. You'll see the SeeMenu interface
3. Click "Select a menu image" and upload any image
4. Click "Analyze Menu" to test the upload

**Note:** The current version returns mock data. OCR and AI features are coming soon!

## Troubleshooting

### Backend won't start
- Check if port 8080 is already in use: `lsof -i :8080`
- Make sure Java 17+ is installed: `java -version`
- Try cleaning Maven cache: `mvn clean install`

### Frontend won't start
- Check if port 3000 is already in use
- Delete `node_modules` and `package-lock.json`, then run `npm install` again
- Make sure you're in the `frontend` directory

### Upload fails
- Make sure the backend is running on port 8080
- Check browser console for errors (F12)
- Verify CORS is enabled in the backend (it should be by default)

## Next Steps

Now that everything is working, you can:

1. **Explore the code:**
   - Backend: `backend/src/main/java/com/seemenu/`
   - Frontend: `frontend/app/`

2. **Add OCR capabilities:**
   - Integrate Tesseract or Google Cloud Vision API
   - Update `MenuService.java` with OCR logic

3. **Enhance the UI:**
   - Modify `MenuUpload.tsx` component
   - Add new pages in `frontend/app/`

4. **Add a database:**
   - Add Spring Data JPA dependency
   - Configure PostgreSQL or MongoDB
   - Create entity classes

Happy coding! 🚀
