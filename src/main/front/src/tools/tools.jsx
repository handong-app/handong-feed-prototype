export function formatTimestamp(timestamp) {
  // Parse the timestamp into a Date object
  const date = new Date(timestamp);

  // Extract the parts of the date
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are zero-based
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");

  // Format the parts into the desired format
  return `${year}년 ${month}월 ${day}일 ${hours}:${minutes}`;
}

// Function to detect URLs and convert them to hyperlinks
export const convertTextToLinks = (text) => {
  // Regular expression to detect URLs
  const urlRegex = /(https?:\/\/[^\s]+)/g;

  // Split the text by URLs and map over the parts
  return text.split(urlRegex).map((part, index) => {
    // If the part matches the URL regex, return a hyperlink
    if (part.match(urlRegex)) {
      return (
        <a href={part} key={index} target="_blank" rel="noopener noreferrer">
          {part}
        </a>
      );
    }
    // Otherwise, return the text as it is
    return <span key={index}>{part}</span>;
  });
};

export const removeDuplicates = (arr, key) => {
  const seen = new Set();
  return arr.filter((item) => {
    const val = item[key]; // Get the property value to check
    if (seen.has(val)) {
      return false; // Skip if it's already in the set
    }
    seen.add(val); // Otherwise, add to set and keep the item
    return true;
  });
};

export const getExtensionFromUrl = (url) => {
  const cleanUrl = url.split("?")[0]; // Remove anything after '?'
  return cleanUrl.split(".").pop().toLowerCase(); // Get the file extension
};

// Function to check if the URL is an image
export const isImage = (url) => {
  const imageExtensions = ["jpg", "jpeg", "png", "gif", "bmp", "svg"];
  const extension = getExtensionFromUrl(url); // Get cleaned extension
  return imageExtensions.includes(extension);
};

// Function to check if the URL is a video
export const isVideo = (url) => {
  const videoExtensions = ["mp4", "webm", "ogg", "mov", "avi"];
  const extension = getExtensionFromUrl(url); // Get cleaned extension
  return videoExtensions.includes(extension);
};
