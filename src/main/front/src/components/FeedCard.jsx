import { useEffect, useRef, useState } from "react";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import { deepOrange, green } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import { convertTextToLinks, formatTimestamp } from "../tools/tools";
import ReactShowMoreText from "react-show-more-text";

import "./FeedCard.css";
import FeedCardGallery from "./FeedCardGallery";
import { Skeleton } from "@mui/material";
import { useFetchBe } from "../tools/api";
import { useSetRecoilState } from "recoil";
import { feedCountAtom } from "../recoil/feedAtom";

export default function FeedCard({ loading, item, watchSeen = false }) {
  const fetch = useFetchBe();
  const setFeedCount = useSetRecoilState(feedCountAtom);
  const [expanded, setExpanded] = useState(false);
  const [isScrolledUpOut, setIsScrolledUpOut] = useState(false);
  const [updateSeenServer, setUpdateSeenServer] = useState(false);
  const [like, setLike] = useState(item?.like);

  // eslint-disable-next-line unused-imports/no-unused-vars
  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const cardRef = useRef(null);

  useEffect(() => {
    const handleScroll = () => {
      if (!watchSeen) return;

      const elementTarget = cardRef.current;
      if (elementTarget) {
        const elementBottom =
          elementTarget.offsetTop + elementTarget.offsetHeight;

        // Check if the user has scrolled past the card
        if (window.scrollY > elementBottom) {
          // alert("You've scrolled past the card");
          setIsScrolledUpOut(true);
        }
      }
    };

    window.addEventListener("scroll", handleScroll);

    // Cleanup the scroll event listener on component unmount
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [watchSeen]);

  useEffect(() => {
    if (!isScrolledUpOut || updateSeenServer) return;
    setUpdateSeenServer(true);
    console.log(isScrolledUpOut, item.id);
    fetch("/feeduser/seen", "POST", { subjectId: item.subjectId }).then(() =>
      setFeedCount((prev) => prev - 1)
    );
  }, [item, isScrolledUpOut, fetch, setFeedCount, updateSeenServer]);

  return (
    <Card ref={cardRef} className="FeedCard" sx={{ my: 2 }}>
      {loading ? (
        <>
          {" "}
          <CardHeader
            avatar={<Skeleton variant="circular" width={40} height={40} />}
            // action={<Skeleton variant="rectangular" width={24} height={24} />}
            title={<Skeleton width="80%" />}
            subheader={<Skeleton width="40%" />}
          />
          <Skeleton variant="rectangular" width="100%" height={150} />
          <CardContent>
            <Skeleton variant="text" width="90%" />
            <Skeleton variant="text" width="80%" />
            <Skeleton variant="text" width="95%" />
          </CardContent>
        </>
      ) : (
        <>
          <CardHeader
            avatar={
              <Avatar
                sx={{
                  bgcolor: !isScrolledUpOut ? deepOrange[500] : green[700],
                }}
                aria-label="recipe"
              >
                실카
              </Avatar>
            }
            action={
              <IconButton
                aria-label="settings"
                onClick={() => {
                  if (like) {
                    fetch("/feeduser/unlike", "POST", {
                      subjectId: item.subjectId,
                    }).then(() => setLike(false));
                  } else {
                    fetch("/feeduser/like", "POST", {
                      subjectId: item.subjectId,
                    }).then(() => setLike(true));
                  }
                }}
              >
                <FavoriteIcon style={{ color: like ? "red" : "inherit" }} />
              </IconButton>
            }
            title="실명카톡방"
            subheader={formatTimestamp(item.createdAt)}
          />
          <CardMedia sx={{ width: "100%" }}>
            {item.files && <FeedCardGallery images={item.files} id={item.id} />}
          </CardMedia>
          <CardContent
            sx={{
              whiteSpace: "pre-wrap",
              wordWrap: "break-word",
              wordBreak: "break-all",
              overflowWrap: "break-word",
            }}
          >
            <ReactShowMoreText lines={3} truncatedEndingComponent="">
              {convertTextToLinks(item.content.trim())}
            </ReactShowMoreText>
          </CardContent>
          {/* <CardActions disableSpacing>
        <IconButton aria-label="add to favorites">
          <FavoriteIcon />
        </IconButton>
        <IconButton aria-label="share">
          <ShareIcon />
        </IconButton>
      </CardActions> */}
        </>
      )}
    </Card>
  );
}
