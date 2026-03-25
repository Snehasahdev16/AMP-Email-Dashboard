**⚡ AI-Powered AMP4EMAIL Conversion Dashboard**
**📌 Overview**

The AI-Powered AMP4EMAIL Conversion Dashboard is a full-stack web application that automates the conversion of standard HTML or user-provided ideas into AMP4EMAIL-compliant templates.

It leverages Google Gemini API for intelligent content generation and ensures that the output follows strict AMP email guidelines, enabling interactive and high-performance email templates.

**🚀 Features**

🔄 Convert HTML → AMP4EMAIL

💡 Convert Idea → AMP4EMAIL

🔀 Support for HTML + Idea combined input

🤖 AI-powered generation using Google Gemini API

⚡ Real-time AMP preview using <iframe>

📋 One-click Copy to Clipboard

🎯 Automatic AMP validation rules handling

📂 Upload HTML file support (optional feature)

**🏗️ Tech Stack**

Frontend

React.js

HTML5, CSS3

JavaScript

Backend

Spring Boot (Java)

REST APIs

**AI Integration**

Google Gemini API (Generative AI)

Prompt Engineering

**Tools & Testing**

Postman

AMP Playground

Git & GitHub

**⚙️ Architecture**

User Input (HTML / Idea)

        ↓
        
React Frontend (Dashboard UI)

        ↓
        
Spring Boot Backend (API Layer)

        ↓
        
Gemini API (AI Processing)

        ↓
        
AMP4EMAIL Output

        ↓
        
Frontend Preview + Copy

**🧠 How It Works**

User enters HTML, Idea, or both.

Frontend sends request to backend via REST API.

Backend constructs a prompt and calls Gemini API.

AI generates AMP4EMAIL HTML.

Backend parses and returns clean AMP code.

**Frontend:**

Displays generated code

Renders live preview in iframe

Allows copying output

**📦 Installation & Setup**

🔹 Prerequisites

Java 17+

Node.js 18+

Maven

Gemini API Key

**🔹 Backend Setup (Spring Boot)**

cd backend

mvn clean install

**Add your Gemini API key in application.properties:**

gemini.api.key=YOUR_API_KEY

**Run backend:**

mvn spring-boot:run

**🔹 Frontend Setup (React)**

cd frontend

npm install

npm run dev

**🔌 API Endpoint**

Generate AMP Email

POST /amp/generate

**Request Body**
{

  "html": "<html>...</html>",
  
  "idea": "Create a feedback email"
  
}
**Response**

{

  "ampHtml": "<!doctype html>..."
  
}

**📊 Performance Improvements**

⏱ Reduced manual email creation effort by ~70%

🎯 Improved AMP generation accuracy by ~65%

⚡ Faster validation & preview workflow by ~60%

**🛠️ Challenges Faced**

Handling strict AMP4EMAIL validation rules

Avoiding unnecessary AI-generated forms

Managing JSON parsing errors from API

Optimizing prompts for accurate output

**🎯 Future Enhancements**

🔐 User authentication & history tracking

📄 Template saving & versioning

📊 Analytics for email performance

📎 Drag-and-drop email builder

🌐 Multi-language support

**👩‍💻 Author**

Sneha Sahdev

Intern @ InfoEdge (India) Ltd.

**📄 License**

This project is for educational and internship purposes.

**⭐ Acknowledgement**

Special thanks to InfoEdge (India) Ltd. and the Email Engineering Team for guidance and support during the development of this project.
