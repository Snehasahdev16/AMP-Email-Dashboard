import React, { useState } from "react";
import "./index.css";
import "./App.css";

function App() {
  const [ampCode, setAmpCode] = useState("");
  const [email, setEmail] = useState("");
  const [result, setResult] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);
  setResult("");

  try {
    const response = await fetch("http://localhost:8080/api/dashboard/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        ampHtmlCode: ampCode,
        recipient: email,
      }),
    });

    if (!response.ok) {
      throw new Error("Server error: " + response.status);
    }

    const data = await response.text(); // backend returns plain string
    setResult(data);
  } catch (error) {
    console.error("Error:", error);
    setResult("Error connecting to backend");
  } finally {
    setLoading(false);
  }
};


  return (
    <div className="App">
      <h1>AMP Builder with AI</h1>
      <form onSubmit={handleSubmit} className="form">
        <textarea
          placeholder="Paste AMP HTML code here..."
          value={ampCode}
          onChange={(e) => setAmpCode(e.target.value)}
          required
        ></textarea>

        <input
          type="email"
          placeholder="Enter email to verify..."
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? "Checking..." : "Verify"}
        </button>
      </form>

      {result && <p className="result">{result}</p>}
    </div>
  );
}

export default App;
