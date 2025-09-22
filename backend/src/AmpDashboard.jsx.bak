import React, { useState } from "react";

export default function AmpDashboard() {
  const [inputHtml, setInputHtml] = useState("");
  const [inputIdea, setInputIdea] = useState("");
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
          html: inputHtml || null,
          idea: inputIdea || null,
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

  const copyToClipboard = () => {
    navigator.clipboard.writeText(outputAmp);
    alert("AMP code copied to clipboard!");
  };

  return (
    <div className="p-8 bg-gray-50 min-h-screen font-sans">
      <h1 className="text-3xl font-extrabold mb-6 text-blue-800 flex items-center gap-2">
        ⚡ AMP4EMAIL DASHBOARD
      </h1>

      {/* Input for HTML */}
      <textarea
        className="w-full h-40 p-3 border rounded mb-4"
        value={inputHtml}
        onChange={(e) => setInputHtml(e.target.value)}
        placeholder="Paste your HTML code here (optional)."
      />

      {/* Input for Idea */}
      <textarea
        className="w-full h-24 p-3 border rounded mb-4"
        value={inputIdea}
        onChange={(e) => setInputIdea(e.target.value)}
        placeholder="Or describe your email idea here (optional)."
      />

      <button
        onClick={generateAmp}
        disabled={loading}
        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg shadow-md mb-6"
      >
        {loading ? "Generating..." : "Generate AMP4EMAIL"}
      </button>

      {/* Output Section */}
      <div>
        <h3 className="font-semibold text-lg mb-2">Generated AMP4EMAIL:</h3>
        <pre className="bg-gray-100 p-4 border rounded overflow-x-auto text-sm min-h-[200px]">
          {outputAmp}
        </pre>
        {outputAmp && (
          <button
            onClick={copyToClipboard}
            className="mt-2 bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded shadow-sm text-xs"
          >
            Copy to clipboard
          </button>
        )}
      </div>
    </div>
  );
}
