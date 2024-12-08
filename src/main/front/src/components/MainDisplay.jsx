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
        <Alert variant="filled" severity="error" sx={{ borderRadius: 0 }}>
          <AlertTitle>
            새로운 피드 추가가 되지 않는 오류 발생{" "}
            <a
              style={{ color: "white" }}
              href="https://board.handong.app/feed/num/31"
              target="_blank"
              rel="noopener noreferrer"
            >
              (#31)
            </a>
          </AlertTitle>
          현재 개발팀이 문제를 해결 중이나, 시험 기간으로 인해 시간이 다소 걸릴
          수 있습니다.
        </Alert>

        {children}
      </Box>
      {/* <Button variant="contained">Hello world</Button> */}
    </Box>
  );
}

export default MainDisplay;
