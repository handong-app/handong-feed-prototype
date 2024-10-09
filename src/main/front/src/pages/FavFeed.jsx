import MainDisplay from "../components/MainDisplay";
import FeedCard from "../components/FeedCard";
import InfiniteScroll from "react-infinite-scroller";
import useLoadData from "../hooks/useLoadData";
import { Card, CardContent, Typography } from "@mui/material";

function FavFeed() {
  const [allFeeds, hasMore, loadData] = useLoadData({ type: "like" });

  return (
    <MainDisplay>
      {!hasMore && allFeeds.length === 0 && (
        <Card sx={{ my: 2 }}>
          <CardContent>
            <Typography variant="h5" component="div" align="center">
              피드에 좋아요를 눌러보세요
            </Typography>
          </CardContent>
        </Card>
      )}
      <InfiniteScroll
        loadMore={loadData}
        hasMore={hasMore}
        loader={Array(1)
          .fill()
          .map((_, index) => (
            <FeedCard key={index} loading />
          ))}
      >
        {allFeeds.map((item) => (
          <FeedCard key={item.id} item={item} />
        ))}
      </InfiniteScroll>
    </MainDisplay>
  );
}

export default FavFeed;
