import * as React from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import MuiDrawer from "@mui/material/Drawer";

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import InboxIcon from "@mui/icons-material/MoveToInbox";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ViewListIcon from "@mui/icons-material/ViewList";
import {
  Badge,
  BottomNavigation,
  BottomNavigationAction,
  Paper,
  Tooltip,
} from "@mui/material";
import { Link, useLocation, useNavigate } from "react-router-dom";
import MyAppbar from "./MyAppbar";
import { useFeedCount } from "../hooks/useFeed";

const drawerWidth = 240;

const openedMixin = (theme) => ({
  width: drawerWidth,
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: "hidden",
});

const closedMixin = (theme) => ({
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: "hidden",
  width: `calc(${theme.spacing(7)} + 1px)`,
  [theme.breakpoints.up("sm")]: {
    width: `calc(${theme.spacing(8)} + 1px)`,
  },
});

// eslint-disable-next-line unused-imports/no-unused-vars
const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

const Drawer = styled(MuiDrawer, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme }) => ({
  width: drawerWidth,
  flexShrink: 0,
  whiteSpace: "nowrap",
  boxSizing: "border-box",
  variants: [
    {
      props: ({ open }) => open,
      style: {
        ...openedMixin(theme),
        "& .MuiDrawer-paper": openedMixin(theme),
      },
    },
    {
      props: ({ open }) => !open,
      style: {
        ...closedMixin(theme),
        "& .MuiDrawer-paper": closedMixin(theme),
      },
    },
  ],
}));

export default function MainDrawer() {
  const navigate = useNavigate();
  // eslint-disable-next-line unused-imports/no-unused-vars
  const [open, setOpen] = React.useState(false);
  const location = useLocation();
  const [feedNumber] = useFeedCount();

  const MENUS = [
    {
      name: "신규피드",
      icon: (
        <Badge badgeContent={feedNumber} color="primary">
          <InboxIcon />
        </Badge>
      ),
      link: "/",
    },
    {
      name: "찜한피드",
      icon: <FavoriteIcon />,
      link: "/favorite",
    },
    {
      name: "전체피드",
      icon: <ViewListIcon />,
      link: "/all",
    },
  ];

  const currentMenuIndex = MENUS.findIndex(
    (menu) => menu.link === location.pathname
  );

  return (
    <>
      <Box
        sx={{
          display: {
            xs: "none",
            sm: "block",
          },
        }}
      >
        <Drawer variant="permanent" open={open}>
          {/* TODO: Handong Feed btn goes here */}
          {/* <DrawerHeader>
            <IconButton onClick={() => null}>
            </IconButton>
          </DrawerHeader> */}
          <Box sx={{ pt: 16 }}>
            <List>
              {MENUS.map((menu, index) => (
                <Tooltip
                  key={menu.name}
                  title={menu.name}
                  placement="right"
                  arrow
                  slotProps={{
                    popper: {
                      modifiers: [
                        {
                          name: "offset",
                          options: {
                            offset: [0, -14],
                          },
                        },
                      ],
                    },
                  }}
                >
                  <ListItem disablePadding sx={{ display: "block" }}>
                    <ListItemButton
                      component={Link}
                      to={menu.link}
                      onClick={() => {
                        if (currentMenuIndex === index) {
                          console.log("reloading current page");
                          scroll(0, 0);
                          navigate(0);
                        }
                      }}
                      sx={[
                        {
                          minHeight: 48,
                          px: 2.5,
                          my: 1.1,
                        },
                        open
                          ? {
                              justifyContent: "initial",
                            }
                          : {
                              justifyContent: "center",
                            },
                      ]}
                    >
                      <ListItemIcon
                        sx={[
                          {
                            minWidth: 0,
                            justifyContent: "center",
                          },
                          open
                            ? {
                                mr: 3,
                              }
                            : {
                                mr: "auto",
                              },
                          index === currentMenuIndex && {
                            color: (theme) => theme.palette.primary.main,
                          },
                        ]}
                      >
                        {menu.icon}
                      </ListItemIcon>
                      <ListItemText
                        primary={menu.text}
                        sx={[
                          open
                            ? {
                                opacity: 1,
                              }
                            : {
                                opacity: 0,
                              },
                        ]}
                      />
                    </ListItemButton>
                  </ListItem>
                </Tooltip>
              ))}
            </List>
          </Box>
        </Drawer>
      </Box>

      <Box
        sx={{
          display: {
            xs: "block",
            sm: "none",
          },
        }}
      >
        <MyAppbar />
        <Box sx={{ height: 60 }} />
        <Paper
          sx={{ position: "fixed", bottom: 0, left: 0, right: 0, zIndex: 99 }}
          elevation={3}
        >
          <BottomNavigation
            showLabels={true}
            value={currentMenuIndex}
            // onChange={(event, newValue) => {
            //   // setValue(newValue);
            // }}
          >
            {MENUS.map((menu, index) => (
              <BottomNavigationAction
                key={menu.name}
                component={Link}
                onClick={() => {
                  if (currentMenuIndex === index) {
                    console.log("reloading current page");
                    scroll(0, 0);
                    navigate(0);
                  }
                }}
                to={menu.link}
                // label={menu.name}  // disable label for clean
                icon={menu.icon}
              />
            ))}
          </BottomNavigation>
        </Paper>
      </Box>
    </>
  );
}
