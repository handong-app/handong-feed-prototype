import { Box, CssBaseline, Alert, AlertTitle } from "@mui/material";
import MainDrawer from "../components/MainDrawer";

function MainDisplay({ children, noCount = false }) {
  return (
    <Box
      sx={{
        display: {
          xs: "block",
          sm: "flex",
          minHeight: "100vh",
        },
        backgroundColor: "#eef0f3",
      }}
    >
      <CssBaseline />
      <MainDrawer noCount={noCount} />
      <Box
        component="main"
        sx={{ flexGrow: 1, px: 1.5, pb: 6, maxWidth: 700, mx: "auto" }}
      >
        <Alert severity="error" sx={{ borderRadius: 0, my: 1 }}>
          <AlertTitle>
            많은 방문자로 인해 이미지가 불러와지지 않습니다{" "}
            <a
              style={{ color: "black" }}
              href="https://board.handong.app/feed/num/32"
              target="_blank"
              rel="noopener noreferrer"
            >
              (#32)
            </a>
          </AlertTitle>
          현재 개발팀이 문제를 해결 중에 있습니다. 불편을 드려 죄송합니다.
        </Alert>
        <Alert severity="warning" sx={{ borderRadius: 0, my: 1 }}>
          <AlertTitle>
            새로운 피드 추가가 <b>지연</b>되는 오류 발생{" "}
            <a
              style={{ color: "black" }}
              href="https://board.handong.app/feed/num/31"
              target="_blank"
              rel="noopener noreferrer"
            >
              (#31)
            </a>
          </AlertTitle>
          현재 개발팀이 문제를 해결 중이나, 방학 기간으로 인해 시간이 다소 걸릴
          수 있습니다.
        </Alert>

        {children}
      </Box>
      {/* <Button variant="contained">Hello world</Button> */}
    </Box>
  );
}

export default MainDisplay;
