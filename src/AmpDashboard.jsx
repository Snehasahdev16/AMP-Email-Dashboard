import React, { useState, useEffect } from "react";
import "./App.css";

export default function AmpDashboard() {
  const [mode, setMode] = useState("html");
  const [input, setInput] = useState("");
  const [outputAmp, setOutputAmp] = useState("");
  const [loading, setLoading] = useState(false);
  const [previewWidth, setPreviewWidth] = useState("100%");

  const generateAmp = async () => {
    if (!input.trim()) {
      alert("Please enter some input before generating.");
      return;
    }
    setLoading(true);
    setOutputAmp("");

    try {
      const body = {
        html: mode === "html" ? input : null,
        idea: mode === "idea" ? input : null,
        formIdea: mode === "form" ? input : null,
      };

      const response = await fetch("http://localhost:8080/amp/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });

      const data = await response.json();
      setOutputAmp(data.ampHtml || "No AMP code received.");
    } catch (error) {
      setOutputAmp("Error: " + error.message);
    } finally {
      setLoading(false);
    }
  };

  const copyToClipboard = () => {
    if (outputAmp) {
      navigator.clipboard.writeText(outputAmp);
      alert("✅ AMP code copied to clipboard!");
    }
  };

  // Dynamically reload iframe when AMP code changes
  const [iframeKey, setIframeKey] = useState(0);
  useEffect(() => {
    if (outputAmp) setIframeKey(prev => prev + 1);
  }, [outputAmp]);

  return (
    <div className="dashboard-wrapper">
      <div className="dashboard-container">
        <h1 className="title">⚡ AMP4EMAIL DASHBOARD</h1>

        {/* Mode selection */}
        <div className="mode-select">
          <label>Choose input type: </label>
          <select value={mode} onChange={(e) => setMode(e.target.value)}>
            <option value="html">HTML → AMP4EMAIL</option>
            <option value="idea">Idea → AMP4EMAIL</option>
            <option value="form">Form Idea → AMP Form</option>
          </select>
        </div>

        {/* Input box */}
        <textarea
          className="input-box"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder={
            mode === "html"
              ? "Paste your HTML code here..."
              : mode === "idea"
              ? "Describe your email idea here..."
              : "Describe your form idea here..."
          }
        />

        {/* Generate button */}
        <div className="button-row">
          <button
            onClick={generateAmp}
            disabled={loading}
            className="btn-generate"
          >
            {loading ? "Generating..." : "Generate AMP4EMAIL Code"}
          </button>

          <a
            href="https://amp.gmail.dev/playground/"
            target="_blank"
            rel="noopener noreferrer"
            className="btn-playground"
          >
            Test in Gmail Playground
          </a>
        </div>

        {/* Preview and Output */}
        <div className="side-by-side">
          {/* Live Preview */}
          <div className="preview-section">
            <h3 className="preview-title">Live Preview:</h3>
            <div className="preview-controls">
              <button onClick={() => setPreviewWidth("375px")}>📱 Mobile</button>
              <button onClick={() => setPreviewWidth("768px")}>📟 Tablet</button>
              <button onClick={() => setPreviewWidth("100%")}>💻 Desktop</button>
            </div>
            {outputAmp && (
              <iframe
                key={iframeKey} // reload iframe when AMP changes
                title="AMP Preview"
                className="preview-frame"
                style={{
                  width: previewWidth,
                  height: "600px",
                  border: "1px solid #ccc",
                  overflow: "auto"
                }}
                srcDoc={outputAmp}
              />
            )}
          </div>

          {/* Code Output */}
          <div className="output-section">
            <h3 className="output-title">Generated AMP4EMAIL:</h3>
            <p className="note">(AMP code only, ready to use)</p>
            <div className="output-container">
              <pre className="output-code">{outputAmp}</pre>
              <button onClick={copyToClipboard} className="btn-copy">
                📋 Copy
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
