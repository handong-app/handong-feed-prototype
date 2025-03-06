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
        <Alert severity="info" sx={{ borderRadius: 0, my: 1 }}>
          <AlertTitle>
            우측 하단 '모두 읽음 처리' 기능 추가되었습니다{" "}
            <a
              style={{ color: "black" }}
              href="https://board.handong.app/feed/num/33"
              target="_blank"
              rel="noopener noreferrer"
            >
              (#33)
            </a>
          </AlertTitle>
          우측 하단에 알림을 한 번에 모두 읽음으로 변경할 수 있는 기능이
          추가되었습니다.
        </Alert>
        <Alert severity="warning" sx={{ borderRadius: 0, my: 1 }}>
          <AlertTitle>
            현재 새로운 피드가 <b>늦게</b> 올라가는 오류가 있습니다.
            <a
              style={{ color: "black" }}
              href="https://board.handong.app/feed/num/31"
              target="_blank"
              rel="noopener noreferrer"
            >
              (#31)
            </a>
          </AlertTitle>
          개발팀에서 하루에 한 번 수동으로 추가하고 있으니 조금만 기다려 주세요.
          이용에 불편을 드려 죄송합니다.
        </Alert>

        {children}
      </Box>
      {/* <Button variant="contained">Hello world</Button> */}
    </Box>
  );
}

export default MainDisplay;
