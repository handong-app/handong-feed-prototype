import { useRecoilValue } from "recoil";
import { serverRootUrl } from "../constants";
import { authJwtAtom } from "../recoil/authAtom";

export const fetchBe = (jwtValue, path, method = "GET", body) =>
  new Promise((res, rej) => {
    const initStuff = {
      headers: {},
      method,
    };
    if (body && !["GET", "HEAD"].includes(method)) {
      if (body instanceof FormData) {
        initStuff["body"] = body;
      } else {
        initStuff.headers["Content-Type"] = "application/json";
        initStuff["body"] = JSON.stringify(body);
      }
    }
    if (jwtValue) initStuff.headers.Authorization = `Bearer ${jwtValue}`;

    fetch(serverRootUrl + path, initStuff)
      .then((doc) =>
        doc.json().then((json) => {
          // If User not exist (due to db reset, etc)
          if (path === "/user/get" && !json?.email) {
            alert("유저가 존재하지 않습니다. 로그인을 다시해주세요.");
            localStorage.clear();
            window.location.reload();
            return rej("유저가 존재하지 않습니다. 로그인을 다시해주세요.");
          }
          return res(json);
        })
      )

      .catch((err) => rej(err));
  });

export const useFetchBe = () => {
  const jwtValue = useRecoilValue(authJwtAtom);
  return (path, method = "GET", body) => fetchBe(jwtValue, path, method, body);
};
