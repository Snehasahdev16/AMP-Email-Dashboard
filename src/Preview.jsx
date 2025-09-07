import React, { useEffect, useRef } from "react";

export default function Preview({ ampHtml }) {
  const iframeRef = useRef();

  useEffect(() => {
    if (iframeRef.current) {
      const doc = iframeRef.current.contentDocument;
      doc.open();
      doc.write(ampHtml);
      doc.close();
    }
  }, [ampHtml]);

  return (
    <div>
      <h3>Live Preview:</h3>
      <iframe
        ref={iframeRef}
        title="AMP Preview"
        style={{ width: "100%", height: "400px", border: "1px solid #ccc", borderRadius: "6px" }}
      />
    </div>
  );
}
