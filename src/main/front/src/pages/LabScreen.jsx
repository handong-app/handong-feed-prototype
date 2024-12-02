import { Card, CardContent, Typography } from "@mui/material";
import MainDisplay from "../components/MainDisplay";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { authJwtAtom } from "../recoil/authAtom";
import { fetchBe } from "../tools/api";
import LoginPage from "../components/LoginPage";
import Menu from "../components/lab/menu";
import { serverRootUrl } from "../constants";

function LabScreen() {
  const { messageId } = useParams();
  const [doc, setDoc] = useState({
    current: 0,
    avgTime: 0,
    lastUpdate: 0,
  });

  const jwtValue = useRecoilValue(authJwtAtom);

  useEffect(() => {
    if (!jwtValue) return;
  }, [jwtValue, messageId]);

  useEffect(() => {
    const eventSource = new EventSource(`${serverRootUrl}/lab/sub`);

    eventSource.onopen = async () => {
      console.log("sse opened!");
    };

    eventSource.onmessage = (event) => {
      console.log("Message from server:", event.data);
      try {
        const json = JSON.parse(event.data);
        console.log(json);
        setDoc({
          ...json,
          lastUpdate: Math.floor(Date.now() / 1000), // Current epoch time in seconds
        });
      } catch {}
    };

    eventSource.onerror = () => {
      console.error("Error occurred. Closing connection.");
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);

  if (!jwtValue) return <LoginPage />;
  return (
    <MainDisplay>
      <Card sx={{ my: 2 }}>
        <CardContent>
          <Typography variant="h6" component="div" align="center">
            에인트 대기 현황
          </Typography>
          <Typography
            variant="h4"
            component="div"
            align="center"
            sx={{ mb: 1 }}
          >
            <b>{doc.current}명 대기중</b>
          </Typography>
          <Typography variant="h6" component="div" align="center">
            <b>평균 대기시간: {convertSecondsToMinuteSecond(doc.avgTime)}</b>
          </Typography>
          <Typography variant="body2" component="div" align="center">
            <b>최근업데이트: {convertEpochToKoreanTime(doc.lastUpdate)}</b>
          </Typography>

          <Menu />
        </CardContent>
      </Card>
    </MainDisplay>
  );
}

export default LabScreen;

function convertSecondsToMinuteSecond(seconds) {
  const minutes = Math.floor(seconds / 60);
  const remainingSeconds = seconds % 60;
  return `${minutes}분 ${remainingSeconds}초`;
}
function convertEpochToKoreanTime(epochSeconds) {
  const date = new Date(epochSeconds * 1000);
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // Months are zero-indexed
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes().toString().padStart(2, "0"); // Ensure leading zero
  const seconds = date.getSeconds().toString().padStart(2, "0"); // Ensure leading zero

  const isAM = hours < 12;
  const formattedHour = hours % 12 === 0 ? 12 : hours % 12; // Convert to 12-hour format

  return `${year}년 ${month}월 ${day}일 ${
    isAM ? "오전" : "오후"
  } ${formattedHour}:${minutes}:${seconds}`;
}
