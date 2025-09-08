import React from "react";

export default function IdeaInputForm({ idea, setIdea, onGenerate, loading }) {
  return (
    <div className="mb-6">
      {/* Input Box */}
      <textarea
        className="w-full h-48 p-4 border-2 border-gray-400 rounded-lg resize-none text-gray-700 text-sm mb-2"
        value={idea}
        onChange={(e) => setIdea(e.target.value)}
        placeholder="Describe your email idea here..."
      />

      {/* Generate Button */}
      <button
        onClick={onGenerate}
        disabled={loading}
        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg shadow-md font-semibold transition"
      >
        {loading ? "Generating..." : "Convert Idea to AMP4EMAIL"}
      </button>
    </div>
  );
}

