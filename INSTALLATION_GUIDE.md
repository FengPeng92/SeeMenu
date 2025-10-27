# SeeMenu Installation Guide

Follow these steps in order to get everything set up.

## Step 1: Install Homebrew (Package Manager for macOS)

Homebrew makes it easy to install software on macOS.

**Copy and paste this into your terminal:**

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

**Important:** After installation completes, it will show you 2 commands to run. They look like:
```bash
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
eval "$(/opt/homebrew/bin/brew shellenv)"
```

**Run those commands** (copy from your terminal output, not from here).

**Verify Homebrew is installed:**
```bash
brew --version
```

You should see: `Homebrew 4.x.x`

---

## Step 2: Install Java 17

```bash
brew install openjdk@17
```

**Set up Java in your PATH:**

```bash
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk

echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**Verify Java is installed:**
```bash
java -version
```

You should see: `openjdk version "17.x.x"`

---

## Step 3: Install Maven

```bash
brew install maven
```

**Verify Maven is installed:**
```bash
mvn -version
```

You should see: `Apache Maven 3.x.x`

---

## Step 4: Fix npm Permissions

```bash
sudo chown -R 501:20 "/Users/fengpeng/.npm"
npm cache clean --force
```

**Verify npm works:**
```bash
npm -version
```

---

## Step 5: Install Frontend Dependencies

```bash
cd /Users/fengpeng/workspace/SeeMenu/frontend
npm install
```

This will take 1-2 minutes. You should see packages being installed.

---

## Step 6: Start the Backend Server

**Open Terminal Window #1:**

```bash
cd /Users/fengpeng/workspace/SeeMenu/backend
mvn spring-boot:run
```

Wait for this message:
```
Started SeeMenuApplication in X.XXX seconds
```

**Leave this terminal window open!**

---

## Step 7: Start the Frontend Server

**Open Terminal Window #2 (NEW window):**

```bash
cd /Users/fengpeng/workspace/SeeMenu/frontend
npm run dev
```

You should see:
```
▲ Next.js 16.0.0
- Local:        http://localhost:3000
```

**Leave this terminal window open too!**

---

## Step 8: Open Your Browser

Go to: **http://localhost:3000**

You should see the SeeMenu interface! 🎉

---

## Troubleshooting

### "Command not found" after installing something
- Run: `source ~/.zshrc`
- Or close and reopen your terminal

### "Port already in use"
- For backend (8080): `lsof -i :8080` then `kill -9 <PID>`
- For frontend (3000): `lsof -i :3000` then `kill -9 <PID>`

### Installation hangs or fails
- Press Ctrl+C to cancel
- Try running the command again

### Still having issues?
- Make sure you completed ALL steps in order
- Check that you ran the PATH setup commands for Java
- Restart your terminal after Step 2

---

## Quick Reference - All Commands in Order

```bash
# 1. Install Homebrew
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
# (Run the 2 commands it tells you to run)

# 2. Install Java 17
brew install openjdk@17
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# 3. Install Maven
brew install maven

# 4. Fix npm
sudo chown -R 501:20 "/Users/fengpeng/.npm"
npm cache clean --force

# 5. Install frontend deps
cd /Users/fengpeng/workspace/SeeMenu/frontend
npm install

# 6. Start backend (Terminal 1)
cd /Users/fengpeng/workspace/SeeMenu/backend
mvn spring-boot:run

# 7. Start frontend (Terminal 2 - NEW window)
cd /Users/fengpeng/workspace/SeeMenu/frontend
npm run dev

# 8. Open browser to http://localhost:3000
```

---

**Estimated total time: 15-20 minutes**

Good luck! 🚀
