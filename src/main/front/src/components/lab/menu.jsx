import React, { useState } from "react";
import {
  Tabs,
  Tab,
  Typography,
  Box,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";

const Menu = () => {
  const [selectedTab, setSelectedTab] = useState(0);

  const menu = [
    {
      category: "커피류",
      items: [
        "에스프레소",
        "아메리카노",
        "카페 라떼",
        "카푸치노",
        "카페 모카",
        "카라멜 마키아토",
      ],
    },
    {
      category: "논커피 음료",
      items: [
        "핫 초콜릿",
        "그린티 라떼",
        "고구마 라떼",
        "아이스티 (레몬, 복숭아 등)",
      ],
    },
    {
      category: "기타 음료",
      items: [
        "에이드 (레몬, 자몽, 블루베리 등)",
        "스무디 (딸기, 망고, 블루베리 등)",
      ],
    },
  ];

  const handleChange = (event, newValue) => {
    setSelectedTab(newValue);
  };

  return (
    <Box sx={{ width: "100%", maxWidth: 600, mx: "auto", mt: 4 }}>
      <Tabs
        value={selectedTab}
        onChange={handleChange}
        variant="fullWidth"
        indicatorColor="primary"
        textColor="primary"
      >
        {menu.map((section, index) => (
          <Tab label={section.category} key={index} />
        ))}
      </Tabs>
      <Box sx={{ mt: 2 }}>
        {menu.map((section, index) => (
          <div role="tabpanel" hidden={selectedTab !== index} key={index}>
            {selectedTab === index && (
              <Box>
                <Typography variant="h6" sx={{ mb: 2 }}>
                  {section.category}
                </Typography>
                <List>
                  {section.items.map((item, idx) => (
                    <ListItem key={idx} disablePadding>
                      <ListItemText primary={item} />
                    </ListItem>
                  ))}
                </List>
              </Box>
            )}
          </div>
        ))}
      </Box>
    </Box>
  );
};

export default Menu;
