import "./FeedCardGallery.css";
import { getExtensionFromUrl, isBlob, isImage, isVideo } from "../tools/tools";
import { useEffect, useState } from "react";

const FeedCardGallery = ({ images = [] }) => {
  const [filteredImages, setFilteredImages] = useState([]);

  useEffect(() => {
    (async () => {
      setFilteredImages(
        (
          await Promise.all(
            images.map(async (url) => {
              return url;
            })
          )
        ).filter((url) => url)
      );
    })();
  }, [images]);

  // const filteredImages = images.filter(
  //   (url) => isImage(url) || isVideo(url) || isBlob(url)
  // );
  if (filteredImages.length === 0) return <></>;
  return (
    <div className="FeedCardGallery">
      <div className={`post-images images-${filteredImages.length}`}>
        {filteredImages.slice(0, 3).map((url, index) =>
          isImage(url) ? (
            <img key={index} src={url} alt={`${index + 1}`} loading="lazy" />
          ) : isVideo(url) ? (
            <video key={index} controls>
              <source src={url} type={`video/${getExtensionFromUrl(url)}`} />
              Your browser does not support the video tag.
            </video>
          ) : (
            <>
              <div>{url}</div>
            </>
          )
        )}
        {filteredImages.length >= 4 && (
          <div className="more-images-overlay">
            {filteredImages.length > 5 && <span>+{images.length - 4}</span>}
            <img key={1} src={images[3]} alt={`4`} loading="lazy" />
          </div>
        )}
      </div>
    </div>
  );
};

export default FeedCardGallery;
