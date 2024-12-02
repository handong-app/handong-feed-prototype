import { Box, CssBaseline } from "@mui/material";
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
        {children}
      </Box>
      {/* <Button variant="contained">Hello world</Button> */}
    </Box>
  );
}

export default MainDisplay;
