
# AMP Builder with AI 🚀

An intelligent full-stack platform that helps users generate and test AMP HTML Emails using AI.

This project provides a dashboard where users can enter an idea or paste HTML code, and the system will generate valid AMP4Email code. Users can also test their emails, trigger backend APIs, and view real-time analytics when emails are opened.


## 🌟 Features

🔹⚡ AI-Powered AMP HTML Generator – Convert plain HTML or natural language ideas into valid AMP Email code.

🔹📝 Dashboard (React + Vite) – Clean and fast UI with live code editing & AMP preview.

🔹📧 Email Testing – Trigger test emails directly from the dashboard.

🔹🔄 Update Workflow – User inputs → modified code → API/Kafka pipeline.

🔹📊 Analytics – Track email opens & fetch API data dynamically.


## 🛠 Tech Stack

**Frontend (Vite + React + CSS)**

🔹React (UI, Code Editor, AMP Preview)

🔹Vite (fast build tool)

🔹Plain CSS (for styling)

🔹Axios (API calls)

**Backend (Java + Spring Boot)**

🔹Spring Boot (REST APIs, Email sending, Kafka integration)

🔹Java (business logic)

🔹Kafka (asynchronous updates & event-driven flow)

🔹MySQL (store inputs, logs, history)

**AI Integration**

🔹Gemini 2.5 pro AMP Email generator


## 📂 Project Structure

 AMP-Builder-AI/
 
├── backend/

│ ├── src/main/java/

│ ├── src/main/resources/

│ └── pom.xml

├── frontend/

│ ├── src/

│ ├── index.html

│ └── package.json

├── .gitignore

├── README.md

└── application.properties.example # Template config file


## 🚀 Getting Started

1️⃣ **Clone the repository**

🔹git clone https://github.com/Snehasahdev16/AMP-Builder-AI.git

🔹cd AMP-Builder-AI

2️⃣ **Setup Backend (Spring Boot)**

🔹cd backend

#Import project in IntelliJ IDEA or any Java IDE

#Copy application.properties.example → application.properties

🔹mvn clean install

🔹mvn spring-boot:run

#Backend runs on 👉 http://localhost:8080

3️⃣ **Setup Frontend (Vite + React)**

🔹cd frontend

🔹npm install

🔹npm run dev

#Frontend runs on 👉 http://localhost:5173 (default Vite port)


## 🔑 Configuration

#Copy application.properties.example → application.properties and set:

🔹spring.application.name=AMP-Email-Dashboard

🔹 server.port=8080

🔹 gemini.api.key=YOUR_KEY


## 💡 Use Cases

🔹Capture Info – User enters an idea/code → AI generates AMP HTML.

🔹Update Flow – User modifies AMP code → backend updates via API/Kafka.


## 📸 Screenshots (to be added)

🔹Dashboard with input box & generated AMP HTML

🔹Code editor & preview


## 🤝 Contributing

🔹Fork the repo

🔹Create a feature branch (git checkout -b feature-name)

🔹Commit changes (git commit -m "Add feature")

🔹Push to branch (git push origin feature-name)

🔹Open a Pull Request

# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
