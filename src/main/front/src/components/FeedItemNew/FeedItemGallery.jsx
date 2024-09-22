import React from "react";

import "./FeedItemGallery.css";

const FeedItemGallery = ({ images = [] }) => {
  if (images.length === 0) return <></>;
  return (
    <div className={`post-images images-${images.length}`}>
      {images.slice(0, 3).map((image, index) => (
        <img key={index} src={image} alt={`Image ${index + 1}`} />
      ))}
      {images.length > 4 && (
        <div className="more-images-overlay">
          {images.length > 5 && <span>+{images.length - 4}</span>}
          <img key={1} src={images[3]} alt={`Image 4`} />
        </div>
      )}
    </div>
  );
};

export default FeedItemGallery;
