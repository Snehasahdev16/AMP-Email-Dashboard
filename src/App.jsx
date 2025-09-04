import React, { useState } from "react";

export default function App() {
  const [mode, setMode] = useState("html"); // "html" or "idea"
  const [input, setInput] = useState("");
  const [outputAmp, setOutputAmp] = useState("");
  const [loading, setLoading] = useState(false);

  const generateAmp = async () => {
    if (!input.trim()) {
      alert("Please enter something first!");
      return;
    }

    setLoading(true);
    setOutputAmp("");
    try {
      const response = await fetch("http://localhost:8080/amp/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(
          mode === "html" ? { html: input } : { idea: input }
        ),
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
    navigator.clipboard.writeText(outputAmp);
    alert("AMP code copied to clipboard!");
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-50 font-sans">
      <div className="w-full max-w-4xl bg-white shadow-lg rounded-xl p-8">
        {/* Header */}
        <h1 className="text-3xl font-extrabold mb-6 text-blue-800 text-center flex justify-center items-center gap-2">
          ⚡ AMP4EMAIL DASHBOARD
        </h1>

        {/* Mode Selector */}
        <div className="mb-4 text-center">
          <label className="font-semibold mr-2">Choose Input Type:</label>
          <select
            value={mode}
            onChange={(e) => setMode(e.target.value)}
            className="border border-gray-400 rounded p-2"
          >
            <option value="html">HTML Code</option>
            <option value="idea">Email Idea</option>
          </select>
        </div>

        {/* Input Box */}
        <div className="relative mb-4">
          <textarea
            className="w-full h-64 p-4 border-2 border-gray-400 rounded-lg shadow-sm resize-none text-gray-700 text-sm"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            placeholder={
              mode === "html"
                ? "Paste your HTML code here."
                : "Describe your email idea here..."
            }
          />
          {loading && (
            <span className="absolute bottom-2 right-2 bg-white px-2 py-1 text-gray-600 text-xs rounded shadow-sm">
              Generating...
            </span>
          )}
        </div>

        {/* Buttons */}
        <div className="flex justify-between items-center mb-6">
          <button
            onClick={generateAmp}
            disabled={loading}
            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg shadow-md font-semibold transition"
          >
            {mode === "html"
              ? "Convert HTML to AMP4EMAIL"
              : "Convert Idea to AMP4EMAIL"}
          </button>

          <a
            href="https://amp.gmail.dev/playground/"
            target="_blank"
            rel="noopener noreferrer"
            className="bg-yellow-300 hover:bg-yellow-400 text-blue-800 font-semibold px-3 py-2 rounded shadow-sm transition"
          >
            Test at Gmail Playground
          </a>
        </div>

        {/* Output Section */}
        <div>
          <div className="flex justify-between items-center mb-1">
            <h3 className="font-semibold text-lg">Generated AMP4EMAIL:</h3>
          </div>
          <p className="text-sm text-gray-600 mb-2">
            (Remove any extra fences at the start or end of the code if present)
          </p>
          <div className="relative">
            <pre className="bg-gray-100 p-4 border border-gray-300 rounded-lg overflow-x-auto text-sm min-h-[200px] whitespace-pre-wrap">
              {outputAmp}
            </pre>
            {outputAmp && (
              <button
                onClick={copyToClipboard}
                className="absolute bottom-2 right-2 bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded shadow-sm text-xs"
              >
                Copy
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

