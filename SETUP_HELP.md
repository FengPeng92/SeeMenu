# Setup Help - Missing Dependencies

## Current Issues Detected

### 1. Maven Not Installed ❌

Maven is required to build and run the Java backend.

**Install with Homebrew:**
```bash
brew install maven
```

**Verify installation:**
```bash
mvn -version
```

You should see something like:
```
Apache Maven 3.9.x
```

### 2. NPM Permission Issue ❌

Your npm cache has permission problems.

**Fix it:**
```bash
sudo chown -R 501:20 "/Users/fengpeng/.npm"
```

**Then clear cache:**
```bash
npm cache clean --force
```

## After Installing Dependencies

### Step 1: Start Backend

```bash
cd /Users/fengpeng/workspace/SeeMenu/backend
mvn spring-boot:run
```

Keep this terminal open. You should see:
```
Started SeeMenuApplication in X.XXX seconds
```

### Step 2: Start Frontend (in a NEW terminal)

```bash
cd /Users/fengpeng/workspace/SeeMenu/frontend
npm install
npm run dev
```

You should see:
```
▲ Next.js 16.0.0
- Local:        http://localhost:3000
```

### Step 3: Open Browser

Navigate to: `http://localhost:3000`

## Alternative: Use Docker (If you prefer)

If you have Docker installed, I can create Docker containers for both services which will work without needing to install Maven or fix npm permissions.

Would you like me to create Docker configurations instead?

## Quick Check - What Do You Have?

Run these commands to check what's installed:

```bash
java -version      # Need Java 17+
mvn -version       # Need Maven 3.6+
node -version      # Need Node 18+
npm -version       # Need npm
```

## Need Help?

1. **Don't have Homebrew?** Install it from: https://brew.sh
2. **Don't have Java 17?** Run: `brew install openjdk@17`
3. **Prefer using an IDE?** Import the backend folder in IntelliJ IDEA and run from there

Let me know which approach you'd like to take!
