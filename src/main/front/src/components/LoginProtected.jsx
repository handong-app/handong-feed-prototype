import { useRecoilValue } from "recoil";
import { authJwtAtom } from "../recoil/authAtom";
import { Navigate } from "react-router-dom";

function LoginProtected({ comp: Comp }) {
  const jwtValue = useRecoilValue(authJwtAtom);
  // const [userData, setUserData] = useRecoilState(userDetailAtom);
  // const [userDataLoading, setUserDataLoading] = useState(true);
  // const [userDataError, setUserDataError] = useState();

  // useEffect(() => {
  //   fetchBe(jwtValue, "/userDetail/get")
  //     .then((json) => {
  //       console.log("Got User Data", json);
  //       if (json.weight) setUserData(json);
  //       else setUserData(null);
  //       setUserDataLoading(false);
  //     })
  //     .catch((e) => setUserDataError(e.message));
  // }, [jwtValue]);

  if (jwtValue) {
    if (typeof Comp === "object") return <>{Comp}</>;
    // if (userDataError)
    //   return <div style={{ color: "pink" }}>Error: {userDataError}</div>;
    // if (userDataLoading)
    //   return <div style={{ color: "white" }}>Loading User Data...</div>;

    // if (!!userData) {
    return <Comp />;
    // } else {
    //   return <Navigate to="/" />;
    // }
  }

  return <Navigate to="/land" />;
}

export default LoginProtected;
