import { useEffect, useState } from "react";
import MainDisplay from "../components/MainDisplay";
import FeedCard from "../components/FeedCard";
import InfiniteScroll from "react-infinite-scroller";
import useLoadData from "../hooks/useLoadData";
import { useFeedCount } from "../hooks/useFeed";
import { useFetchBe } from "../tools/api";
import { useSetRecoilState } from "recoil";
import { feedCountAtom } from "../recoil/feedAtom";
import {
  Box,
  Button,
  Card,
  CardContent,
  Fab,
  Modal,
  Tooltip,
  Typography,
} from "@mui/material";
import MarkEmailReadOutlinedIcon from "@mui/icons-material/MarkEmailReadOutlined";
import { useNavigate } from "react-router-dom";

function MainFeed() {
  const fetch = useFetchBe();
  const navigate = useNavigate();
  const setFeedCount = useSetRecoilState(feedCountAtom);
  const [allFeeds, hasMore, loadData] = useLoadData({ type: "unseen" });
  const [feedNumber] = useFeedCount();
  const [doingBulkDelete, setDoingBulkDelete] = useState(false);

  const [readAllModal, setReadAllModal] = useState(0);

  useEffect(() => {
    if (doingBulkDelete || hasMore || feedNumber < 1) return;
    setDoingBulkDelete(true);
    console.log(feedNumber);
    allFeeds.slice(-feedNumber).map((item) => {
      console.log("bulk delete", item.id);
      fetch("/feeduser/seen", "POST", { subjectId: item.subjectId }).then(() =>
        setFeedCount(0)
      );
    });
  }, [hasMore, feedNumber, allFeeds, fetch, setFeedCount, doingBulkDelete]);

  return (
    <MainDisplay>
      <Modal open={readAllModal != 0}>
        <Card
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 400,
            p: 2,
          }}
        >
          <CardContent>
            <Typography variant="h6" component="div" gutterBottom>
              모든 피드를 읽음 처리하시겠습니까?
            </Typography>
            <Typography variant="body2" color="text.secondary">
              이 행동은 되돌릴 수 없습니다.
            </Typography>
            <Box sx={{ display: "flex", justifyContent: "flex-end", mt: 2 }}>
              <Button
                onClick={() => setReadAllModal(0)}
                sx={{ mr: 1 }}
                disabled={readAllModal === 2}
              >
                취소
              </Button>
              <Button
                variant="contained"
                color="error"
                loading={readAllModal === 2}
                onClick={() => {
                  setReadAllModal(2);
                  fetch("/feeduser/readall", "POST", {
                    lastSentAt: allFeeds[0].sentAtEpoch,
                  })
                    .then(() => {
                      console.log("reloading current page");
                      scroll(0, 0);
                      navigate(0);
                    })
                    .catch((e) => {
                      alert(e.message);
                      setReadAllModal(1);
                    });
                }}
              >
                읽음처리
              </Button>
            </Box>
          </CardContent>
        </Card>
      </Modal>
      <Tooltip title="모두 읽음 처리">
        <Fab
          color="primary"
          aria-label="add"
          disabled={allFeeds.length === 0}
          sx={{
            position: "fixed",
            bottom: {
              xs: 70,
              sm: 20,
            },
            right: 20,
          }}
          onClick={() => setReadAllModal(1)}
        >
          <MarkEmailReadOutlinedIcon />
        </Fab>
      </Tooltip>
      {!hasMore && allFeeds.length === 0 && (
        <Card sx={{ my: 2 }}>
          <CardContent>
            <Typography variant="h5" component="div" align="center">
              모든 피드를 읽었어요!
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
          <FeedCard key={item.id} item={item} watchSeen={true} />
        ))}
      </InfiniteScroll>
    </MainDisplay>
  );
}

export default MainFeed;
