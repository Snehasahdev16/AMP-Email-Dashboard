import React, { useState } from "react";
import IdeaInputForm from "./IdeaInputForm";
import Preview from "./Preview";
import "./App.css";

export default function App() {
  const [inputType, setInputType] = useState("html");
  const [inputValue, setInputValue] = useState("");
  const [outputAmp, setOutputAmp] = useState("");
  const [loading, setLoading] = useState(false);

  const generateAmp = async () => {
    setLoading(true);
    setOutputAmp("");
    try {
      const response = await fetch("http://localhost:8080/amp/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          html: inputType === "html" ? inputValue : null,
          idea: inputType === "idea" ? inputValue : null,
        }),
      });

      const data = await response.json();
      setOutputAmp(data.ampHtml || "No AMP code received.");
    } catch (error) {
      setOutputAmp("Error: " + error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-container">
      <div className="dashboard-box">
        {/* Title */}
        <h1 className="title">⚡ AMP4EMAIL DASHBOARD</h1>

        {/* Dropdown to choose input type */}
        <div className="input-switch">
          <label>Input Type:</label>
          <select
            value={inputType}
            onChange={(e) => setInputType(e.target.value)}
          >
            <option value="html">HTML Code</option>
            <option value="idea">Email Idea</option>
          </select>
        </div>

        {/* Input form */}
        <IdeaInputForm
          inputType={inputType}
          inputValue={inputValue}
          setInputValue={setInputValue}
        />

        {/* Buttons */}
        <div className="button-row">
          <button onClick={generateAmp} disabled={loading}>
            {loading ? "Generating..." : "Convert to AMP4EMAIL"}
          </button>
          <a
            href="https://amp.gmail.dev/playground/"
            target="_blank"
            rel="noopener noreferrer"
            className="playground-link"
          >
            Test at Gmail Playground
          </a>
        </div>

        {/* Output */}
        <h3 className="output-heading">Generated AMP4EMAIL:</h3>
        <p className="hint">(Remove any extra fences if present)</p>

        <div className="code-box">
          <pre>{outputAmp}</pre>
        </div>

        {/* Live Preview */}
        {outputAmp && <Preview ampHtml={outputAmp} />}
      </div>
    </div>
  );
}
