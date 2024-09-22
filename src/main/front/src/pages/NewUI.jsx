import "./NewUI.css";
import FeedItemNew from "../components/FeedItemNew/FeedItemNew";
import WeatherData from "../components/FeedItemNew/WeatherData";
import { useState } from "react";
import InfiniteScroll from "react-infinite-scroller";
import { removeDuplicates } from "../tools/tools";

function NewUI() {
  const [allFeeds, setAllFeed] = useState([]);
  const [hasMore, setHasMore] = useState(true);

  const loadData = async () => {
    const lastTimestamp = allFeeds.at(-1)?.sentAtEpoch || -1;
    const data = await (
      await fetch(`/api/kafeed/scrolllist?afterSentAt=${lastTimestamp}`)
    ).json();
    setAllFeed((prev) =>
      removeDuplicates(
        [
          ...prev,
          ...data.map((dd) => ({
            author: "실명카톡방2",
            sentAtEpoch: dd.sentAt,
            createdAt: new Date(dd.sentAt * 1000),
            id: dd.id,
            content: dd.message,
            files: dd.files,
            img: dd.files[0], // Temp just for testing
          })),
        ],
        "id"
      )
    );
    if (data.length === 0) setHasMore(false);
  };

  return (
    <div className="NewUI">
      <div className="header">
        <h1>Handong Feed</h1>
      </div>

      <div className="list">
        <WeatherData />
        {allFeeds.length === 0 && <div>No Entry..</div>}
        <InfiniteScroll loadMore={loadData} hasMore={hasMore}>
          {allFeeds.map((item) => (
            <FeedItemNew key={item.id} item={item} />
          ))}
        </InfiniteScroll>
      </div>
    </div>
  );
}

export default NewUI;
