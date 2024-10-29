import "./FeedCardGallery.css";
import { Gallery, Item } from "react-photoswipe-gallery";
import { getExtensionFromUrl, isImage, isVideo } from "../tools/tools";

import "photoswipe/dist/photoswipe.css";
import FeedCardImageItem from "./FeedCardImageItem";

const FeedCardGallery = ({ images = [] }) => {
  const filteredImages = images.filter((url) => isImage(url) || isVideo(url));
  if (filteredImages.length === 0) return <></>;
  return (
    <div className="FeedCardGallery">
      <Gallery withDownloadButton>
        <div className={`post-images images-${filteredImages.length}`}>
          {filteredImages.slice(0, 3).map((url, index) =>
            isImage(url) ? (
              <FeedCardImageItem url={url} index={index} />
            ) : // <img key={index} src={url} alt={`${index + 1}`} loading="lazy" />
            isVideo(url) ? (
              <video key={index} controls>
                <source src={url} type={`video/${getExtensionFromUrl(url)}`} />
                Your browser does not support the video tag.
              </video>
            ) : (
              <>Unknown Data</>
            )
          )}
          {filteredImages.length >= 4 && (
            <div className="more-images-overlay">
              {filteredImages.length > 5 && <span>+{images.length - 4}</span>}
              <FeedCardImageItem url={images[3]} index={3} />
              {filteredImages.slice(4).map((url, index) => (
                <FeedCardImageItem url={url} index={index + 4} hidden={true} />
              ))}

              {/* <img key={1} src={images[3]} alt={`4`} loading="lazy" /> */}
            </div>
          )}
        </div>
      </Gallery>
    </div>
  );
};

export default FeedCardGallery;
