import React from "react";
import { Box, Button, CssBaseline, Typography } from "@mui/material";
import MyAppbar from "../components/MyAppbar";
import MainDrawer from "../components/MainDrawer";

function MainDisplay({ children }) {
  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <MainDrawer />
      <Box
        component="main"
        sx={{ flexGrow: 1, p: 3, maxWidth: 700, m: "auto" }}
      >
        {children}
      </Box>
      {/* <Button variant="contained">Hello world</Button> */}
    </Box>
  );
}

export default MainDisplay;
