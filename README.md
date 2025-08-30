# AMP Builder with AI 🚀

An intelligent full-stack platform that helps users generate, test, and send AMP HTML Emails using AI.

This project provides a dashboard where users can enter an idea or paste HTML code, and the system will generate valid AMP4Email code. Users can also test their emails, trigger backend APIs, and view real-time analytics when emails are opened.

🌟 Features

⚡ AI-Powered AMP HTML Generator – Convert plain HTML or natural language ideas into valid AMP Email code.

📝 Dashboard (React + Vite) – Clean and fast UI with live code editing & AMP preview.

📧 Email Testing – Trigger test emails directly from the dashboard.

🔄 Update Workflow – User inputs → modified code → API/Kafka pipeline.

📊 Analytics – Track email opens & fetch API data dynamically.

🛠 Tech Stack

Frontend (Vite + React + CSS)

React (UI, Code Editor, AMP Preview)

Vite (fast build tool)

Plain CSS (for styling)

Axios (API calls)

Backend (Java + Spring Boot)

Spring Boot (REST APIs, Email sending, Kafka integration)

Java (business logic)

Kafka (asynchronous updates & event-driven flow)

MySQL (store inputs, logs, history)

AI Integration

LLM-based AMP Email generator

📂 Project Structure

 AMP-Builder-AI/
│
├── backend/            
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
│
├── frontend/           
│   ├── src/
│   ├── index.html
│   └── package.json
│
├── .gitignore          
├── README.md           
└── application.properties.example   # Template config file

🚀 Getting Started


1️⃣ Clone the repository

git clone https://github.com/Snehasahdev16/AMP-Builder-AI.git

cd AMP-Builder-AI


2️⃣ Setup Backend (Spring Boot)

cd backend

//Import project in IntelliJ IDEA or any Java IDE

//Copy application.properties.example → application.properties

mvn clean install

mvn spring-boot:run

Backend runs on 👉 http://localhost:8080


3️⃣ Setup Frontend (Vite + React)

cd frontend

npm install

npm run dev

Frontend runs on 👉 http://localhost:5173 (default Vite port)


🔑 Configuration

Copy application.properties.example → application.properties and set:

spring.application.name=AMP-Email-Dashboard

spring.mail.host=smtp.gmail.com

spring.mail.port=587

spring.mail.username=your_email@gmail.com

spring.mail.password=your_app_password   //Use App Password, not real Gmail password

spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.starttls.enable=true

logging.level.org.springframework.mail=DEBUG

logging.level.org.springframework.mail.javamail=DEBUG


🔑 Notes:
Replace your_email@gmail.com and your_app_password with your own.

For Gmail, always generate a Google App Password instead of using your actual Gmail password.

Regular Gmail password won’t work due to Google’s “Less Secure Apps” policy.

Keep real config in application.properties (ignored in Git).

Commit only the .example file.


💡 Use Cases

Capture Info – User enters an idea/code → AI generates AMP HTML.

Update Flow – User modifies AMP code → backend updates via API/Kafka.

Email Open Tracking – Backend fetches API data dynamically → shows insights in HTML.


📸 Screenshots (to be added)

Dashboard with input box & generated AMP HTML

Code editor & preview

Email testing flow


🤝 Contributing

Fork the repo

Create a feature branch (git checkout -b feature-name)

Commit changes (git commit -m "Add feature")

Push to branch (git push origin feature-name)

Open a Pull Request
