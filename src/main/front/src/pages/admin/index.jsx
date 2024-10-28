import PeopleIcon from "@mui/icons-material/People";
import FeedIcon from "@mui/icons-material/Feed";
import InsertDriveFileIcon from "@mui/icons-material/InsertDriveFile";

import UsersTable from "./UsersTable";
import AdminFeed from "./AdminFeed";
import AdminFiles from "./AdminFiles";

export const ADMINMENU = [
  {
    title: "이용자 관리",
    icon: <PeopleIcon />,
    id: "users",
    comp: UsersTable,
  },
  {
    title: "게시글 관리",
    icon: <FeedIcon />,
    id: "posts",
    comp: AdminFeed,
  },
  {
    title: "파일 관리",
    icon: <InsertDriveFileIcon />,
    id: "files",
    comp: AdminFiles,
  },
];