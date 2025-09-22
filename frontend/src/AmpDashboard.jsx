import React, { useState, useEffect } from "react";
import Preview from "./Preview";
import "./App.css";

export default function AmpDashboard() {
  const [htmlInput, setHtmlInput] = useState("");
  const [ideaInput, setIdeaInput] = useState("");
  const [ampCode, setAmpCode] = useState("");
  const [loading, setLoading] = useState(false);
  const [previewWidth, setPreviewWidth] = useState("100%");
  const [iframeKey, setIframeKey] = useState(0);

  useEffect(() => {
    if (ampCode) setIframeKey((prev) => prev + 1);
  }, [ampCode]);

  const generateAmp = async () => {
    if (!htmlInput.trim() && !ideaInput.trim()) {
      alert("Please enter HTML code or an Idea before generating.");
      return;
    }

    setLoading(true);
    setAmpCode("");

    try {
      const body = {
        html: htmlInput.trim() || null,
        idea: ideaInput.trim() || null,
      };

      const response = await fetch("http://localhost:8080/amp/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });

      const data = await response.json();
      setAmpCode(data.ampHtml || "No AMP code received.");
    } catch (error) {
      setAmpCode("Error: " + error.message);
    } finally {
      setLoading(false);
    }
  };

  const copyToClipboard = () => {
    if (ampCode) {
      navigator.clipboard.writeText(ampCode);
      alert("✅ AMP code copied to clipboard!");
    }
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
          <button
            className="btn-generate"
            onClick={generateAmp}
            disabled={loading}
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

        {/* Preview + Output */}
        <div className="side-by-side">
          {/* Live Preview */}
          <div className="preview-section">
            <h3 className="preview-title">Live Preview:</h3>
            <div className="preview-controls">
              <button onClick={() => setPreviewWidth("375px")} style={{ color: "black" }} >📱 Mobile </button>
              <button onClick={() => setPreviewWidth("768px")} style={{ color: "black" }} >📟 Tablet </button>
              <button onClick={() => setPreviewWidth("100%")}  style={{ color: "black" }} >💻 Desktop </button>
            </div>
            {ampCode && (
              <iframe
                key={iframeKey}
                title="AMP Preview"
                className="preview-frame"
                style={{
                  width: previewWidth,
                  height: "600px",
                  border: "1px solid #ccc",
                  overflow: "auto",
                }}
                srcDoc={ampCode}
              />
            )}
          </div>

          {/* Code Output */}
          <div className="output-section">
            <h3 className="output-title">Generated AMP4EMAIL:</h3>
            <p className="note">(AMP code only, ready to use)</p>
            <div className="output-container">
              <pre className="output-code">{ampCode}</pre>
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
