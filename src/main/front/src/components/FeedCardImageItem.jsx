import { useEffect, useState } from "react";
import { Item } from "react-photoswipe-gallery";

function FeedCardImageItem({ url, index, hidden }) {
  const [dimensions, setDimensions] = useState({ width: 300, height: 300 });

  useEffect(() => {
    const loadDimensions = async () => {
      try {
        const { width, height } = await preloadImage(url);
        setDimensions({ width, height });
      } catch (error) {
        console.error("Failed to load image dimensions", error);
      }
    };

    loadDimensions();
  }, [url]);

  return (
    <Item
      original={url}
      thumbnail={url}
      width={dimensions.width}
      height={dimensions.height}
    >
      {({ ref, open }) => (
        <img
          ref={ref}
          onClick={open}
          src={url}
          alt={`image-${index}`}
          className="thumbnail"
          loading="lazy"
          crossOrigin="anonymous"
          style={
            { display: hidden ? "none" : "block" }
            // { display: "block" }
          }
        />
      )}
    </Item>
  );
}

export default FeedCardImageItem;

const preloadImage = (src) => {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.src = src;
    img.crossOrigin = "anonymous";
    img.onload = () => resolve({ width: img.width, height: img.height });
    img.onerror = reject;
  });
};
