# OpenAI API Setup Guide

## Step 1: Get Your OpenAI API Key

1. **Go to OpenAI Platform:**
   - Visit: https://platform.openai.com/

2. **Sign up or Log in:**
   - Create an account if you don't have one
   - Or log in with your existing account

3. **Navigate to API Keys:**
   - Click on your profile icon (top right)
   - Select "API keys" or go to: https://platform.openai.com/api-keys

4. **Create a New API Key:**
   - Click "Create new secret key"
   - Give it a name (e.g., "SeeMenu")
   - Copy the key immediately (you won't be able to see it again!)
   - It looks like: `sk-proj-...` or `sk-...`

5. **Add Billing Information:**
   - Go to: https://platform.openai.com/account/billing
   - Add a payment method
   - Set up billing (GPT-4 Vision costs approximately $0.01-0.03 per image analysis)

## Step 2: Set Up Your API Key

### Option A: Environment Variable (Recommended)

**On macOS/Linux:**

Add to your `~/.zshrc` or `~/.bash_profile`:

```bash
export OPENAI_API_KEY="sk-your-actual-api-key-here"
```

Then reload:
```bash
source ~/.zshrc
```

**Verify it's set:**
```bash
echo $OPENAI_API_KEY
```

### Option B: Directly in application.properties (Not Recommended for Production)

Edit: `/Users/fengpeng/workspace/SeeMenu/backend/src/main/resources/application.properties`

Replace:
```properties
openai.api.key=${OPENAI_API_KEY:your-api-key-here}
```

With:
```properties
openai.api.key=sk-your-actual-api-key-here
```

⚠️ **Warning:** Don't commit your API key to Git!

## Step 3: Restart the Backend

If the backend is running, stop it (Ctrl+C) and restart:

```bash
cd /Users/fengpeng/workspace/SeeMenu/backend
mvn spring-boot:run
```

## Step 4: Test It!

1. Go to http://localhost:3000
2. Upload a menu image
3. Click "Analyze Menu"
4. Wait 5-10 seconds for AI analysis
5. See the extracted dish information!

## Pricing Information

**GPT-4o (recommended model):**
- Input: $2.50 per 1M tokens
- Output: $10.00 per 1M tokens
- Image: ~$0.01-0.03 per image

**Typical costs for SeeMenu:**
- Per menu analysis: $0.02-0.05
- 100 menu analyses: ~$2-5
- 1000 menu analyses: ~$20-50

## Troubleshooting

### "Invalid API Key" Error
- Make sure you copied the full key (starts with `sk-`)
- Check for extra spaces or quotes
- Verify the key is active at https://platform.openai.com/api-keys

### "Insufficient Quota" Error
- Add a payment method to your OpenAI account
- Check your usage limits at https://platform.openai.com/account/limits

### "Rate Limit Exceeded" Error
- You're making too many requests too quickly
- Wait a minute and try again
- Consider upgrading your OpenAI tier for higher limits

### Backend Won't Start
- Make sure the environment variable is set: `echo $OPENAI_API_KEY`
- Restart your terminal after setting the environment variable
- Check backend logs for specific errors

## Alternative: Use a Different Model

If you want to use a different OpenAI model, edit `application.properties`:

```properties
# Options:
# gpt-4o (best quality, recommended)
# gpt-4-turbo (good quality, faster)
# gpt-4 (high quality, slower)
openai.model=gpt-4o
```

## Next Steps

Once set up, you can:
1. Test with various menu images
2. Adjust the AI prompt in `AIMenuAnalyzer.java` for better results
3. Add more features like cuisine type detection
4. Save analyzed menus to a database

Happy menu scanning! 🍽️
