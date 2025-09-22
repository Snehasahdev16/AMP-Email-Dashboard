import React, { useState } from "react";
import Preview from "./Preview";
import AmpDashboard from './AmpDashboard';

function App() {
  const [htmlInput, setHtmlInput] = useState("");
  const [ideaInput, setIdeaInput] = useState("");
  const [ampCode, setAmpCode] = useState("");
  const handleGenerate = async () => {
    try {
      const response = await fetch("http://localhost:8080/amp/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          html: htmlInput,
          idea: ideaInput,
        }),
      });

      const data = await response.json();
      setAmpCode(data.ampHtml || "No output generated.");
    } catch (error) {
      setAmpCode("Error generating AMP4EMAIL: " + error.message);
    }
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(ampCode);
    alert("Copied AMP code to clipboard!");
  };

  return (
    <div className="dashboard-wrapper">
      <div className="dashboard-container">
        <h1 className="title">⚡ AMP4EMAIL DASHBOARD</h1>

        {/* Input Columns */}
        <div className="input-columns">
          <div style={{ flex: 1, display: "flex", flexDirection: "column" }}>
            <label style={{ marginBottom: "8px", fontWeight: "bold" }}>
              HTML Input
            </label>
            <textarea
              className="input-box"
              placeholder="Paste your HTML code here..."
              value={htmlInput}
              onChange={(e) => setHtmlInput(e.target.value)}
            />
          </div>

          <div style={{ flex: 1, display: "flex", flexDirection: "column" }}>
            <label style={{ marginBottom: "8px", fontWeight: "bold" }}>
              Idea Input
            </label>
            <textarea
              className="input-box"
              placeholder="Write your idea here..."
              value={ideaInput}
              onChange={(e) => setIdeaInput(e.target.value)}
            />
          </div>
        </div>

        {/* Buttons */}
        <div className="button-row">
          <button className="btn-generate" onClick={handleGenerate}>
            Generate AMP4EMAIL Code
          </button>
          <a
            href="https://playground.amp.dev/?runtime=amp4email"
            target="_blank"
            rel="noopener noreferrer"
            className="btn-playground"
          >
            Test in Gmail Playground
          </a>
        </div>

        {/* Side by Side Output + Preview */}
        <div className="side-by-side">
          {/* Live Preview */}
          <div className="preview-section">
            <h3>Live Preview:</h3>
            <Preview ampHtml={ampCode} />
          </div>

          {/* Generated Code */}
          <div className="output-section">
            <h3>Generated AMP4EMAIL:</h3>
            <div className="output-container">
              <pre className="output-code">{ampCode}</pre>
              <button className="btn-copy" onClick={handleCopy}>
                Copy
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
