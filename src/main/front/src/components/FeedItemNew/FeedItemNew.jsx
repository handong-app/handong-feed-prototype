import React from "react";

import kakaotalk from "./kakaotalk.png";
import comment from "./comment.png";
import like from "./like.png";
import share from "./share.png";
import { convertTextToLinks } from "../../tools/tools";
import FeedItemGallery from "./FeedItemGallery";
import ReactShowMoreText from "react-show-more-text";

function formatTimestamp(timestamp) {
  // Parse the timestamp into a Date object
  const date = new Date(timestamp);

  // Extract the parts of the date
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are zero-based
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");

  // Format the parts into the desired format
  return `${year}년 ${month}월 ${day}일 ${hours}:${minutes}`;
}

function FeedItemNew({ item, getAllData }) {
  return (
    <div className="FeedItem">
      <div className="moreinfo">
        <div className="leftflx">
          <img className="authorpic" src={kakaotalk} alt="authorpic" />
          <div className="authordate">
            <div className="author">{item.author}</div>
            <div className="date">{formatTimestamp(item.createdAt)}</div>
          </div>
        </div>
        <div className="rightflx">
          <button onClick={() => {}}>Bookmark</button>
        </div>
      </div>
      <div className="content">
        <ReactShowMoreText lines={3} truncatedEndingComponent="">
          {convertTextToLinks(item.content.trim())}
        </ReactShowMoreText>
      </div>
      {item.files && (
        <div className="image">
          <FeedItemGallery images={item.files} />
          {/* <img src={`${item.img}`} alt="ss" /> */}
        </div>
      )}
      <div className="bottomMenu">
        <div className="like">
          <img src={like} alt="" />
          <span>좋아요</span>
        </div>
        <div className="comment">
          <img src={comment} alt="" />
          <span>댓글달기</span>
        </div>
        <div className="share">
          <img src={share} alt="" />
          <span>공유하기</span>
        </div>
      </div>
    </div>
  );
}

export default FeedItemNew;
