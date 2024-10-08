import React from "react";

import "./FeedCardGallery.css";
import { getExtensionFromUrl, isImage, isVideo } from "../tools/tools";

const FeedCardGallery = ({ images = [] }) => {
  if (images.length === 0) return <></>;
  return (
    <div className="FeedCardGallery">
      <div className={`post-images images-${images.length}`}>
        {images.slice(0, 3).map((url, index) =>
          isImage(url) ? (
            <img key={index} src={url} alt={`${index + 1}`} />
          ) : isVideo(url) ? (
            <video key={index} controls>
              <source src={url} type={`video/${getExtensionFromUrl(url)}`} />
              Your browser does not support the video tag.
            </video>
          ) : (
            <>Unknown Data</>
          )
        )}
        {images.length >= 4 && (
          <div className="more-images-overlay">
            {images.length > 5 && <span>+{images.length - 4}</span>}
            <img key={1} src={images[3]} alt={`4`} />
          </div>
        )}
      </div>
    </div>
  );
};

export default FeedCardGallery;
