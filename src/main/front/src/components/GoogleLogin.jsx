import { useNavigate } from "react-router-dom";
import { GoogleOAuthProvider, GoogleLogin } from "@react-oauth/google";
import { googleClientId } from "../constants";
import { useSetRecoilState } from "recoil";
import { authJwtAtom } from "../recoil/authAtom";

const GoogleLoginComponent = ({ noRedirect }) => {
  const navigate = useNavigate();
  const setJwt = useSetRecoilState(authJwtAtom);
  const handleLoginSuccess = (credentialResponse) => {
    console.log("Encoded JWT ID token: " + credentialResponse.credential);

    // Send the Google ID token to the server
    fetch("/api/tbuser/login/google", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ credential: credentialResponse.credential }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Response from server:", data);
        if (data.token) {
          localStorage.setItem("happ_feed_token", JSON.stringify(data.token)); // Incase of strictmode error
          setJwt(data.token);
          if (!noRedirect) navigate("/");
        } else {
          alert(
            "Login failed: " +
              (data.message || "한동 이메일로 다시 시도해보세요!")
          );
        }
      })
      .catch((error) => console.error("Error:", error));
  };

  const handleLoginError = () => {
    console.log("Login Failed");
  };

  return (
    <GoogleOAuthProvider clientId={googleClientId}>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <GoogleLogin
          onSuccess={handleLoginSuccess}
          onError={handleLoginError}
        />
      </div>
    </GoogleOAuthProvider>
  );
};

export default GoogleLoginComponent;
