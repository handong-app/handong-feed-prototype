import { Box, Button, CssBaseline, Typography } from "@mui/material";
import React, { useState } from "react";
import MyAppbar from "../components/MyAppbar";
import MainDrawer from "../components/MainDrawer";
import MainDisplay from "../components/MainDisplay";
import FeedCard from "../components/FeedCard";
import { useFetchBe } from "../tools/api";
import InfiniteScroll from "react-infinite-scroller";
import { removeDuplicates } from "../tools/tools";

function MainFeed() {
  const [allFeeds, setAllFeed] = useState([]);
  const [allSeenFeedId, setAllSeenFeedId] = useState(new Set());
  const [hasMore, setHasMore] = useState(true);
  const fetch = useFetchBe();

  const loadData = async () => {
    const lastTimestamp = allFeeds.at(-1)?.sentAtEpoch || -1;
    const data = await fetch(
      `/kafeed/scrolllist?afterSentAt=${lastTimestamp}&all=y`
    );
    if (!Array.isArray(data)) return;
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
            subjectId: dd.subjectId,
          })),
        ],
        "id"
      )
    );
    if (data.length === 0) setHasMore(false);
  };

  const allFeedsToDisplay = allFeeds.filter(
    (item) => !allSeenFeedId.has(item.subjectId)
  );

  return (
    <MainDisplay>
      <h1>Main Feed</h1>
      <InfiniteScroll loadMore={loadData} hasMore={hasMore}>
        {allFeedsToDisplay.map((item) => (
          // <FeedItemNew
          //   key={item.id}
          //   item={item}
          //   setAllSeenFeedId={setAllSeenFeedId}
          // />
          <FeedCard
            key={item.id}
            item={item}
            setAllSeenFeedId={setAllSeenFeedId}
          />
        ))}
      </InfiniteScroll>
    </MainDisplay>
  );
}

export default MainFeed;
