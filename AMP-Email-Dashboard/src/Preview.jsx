import React from "react";

export default function Preview({ ampCode }) {
  const copyToClipboard = () => {
    navigator.clipboard.writeText(ampCode);
    alert("AMP code copied to clipboard!");
  };

  return (
    <div>
      <h3 className="font-semibold text-lg mb-1">Generated AMP4EMAIL:</h3>
      <p className="text-sm text-gray-600 mb-2">
        (Kindly remove any extra fences at the start or end of the code)
      </p>
      <div className="relative">
        <pre className="bg-gray-100 p-4 border border-gray-300 rounded-lg overflow-x-auto text-sm min-h-[200px]">
          {ampCode}
        </pre>
        {ampCode && (
          <button
            onClick={copyToClipboard}
            className="absolute bottom-2 right-2 bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded shadow-sm text-xs"
          >
            Copy to clipboard
          </button>
        )}
      </div>
    </div>
  );
}


