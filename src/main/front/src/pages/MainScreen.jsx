import { useRecoilValue, useResetRecoilState } from "recoil";
import { authJwtAtom } from "../recoil/authAtom";

import "./MainScreen.scss";
import GoogleLoginComponent from "../components/GoogleLogin";

const MainScreen = ({ noRedirect = false }) => {
  const authData = useRecoilValue(authJwtAtom);
  const logout = useResetRecoilState(authJwtAtom);
  // if (authData) return <>Welcome</>;
  return (
    <>
      <div className="MainScreen">
        {/* Header */}
        <header className="header">
          <nav className="navbar">
            <h1 className="logo">한동Feed</h1>
            <ul className="nav-links">
              <li>
                <a href="#home">Home</a>
              </li>
              <li>
                <a href="#features">Features</a>
              </li>
              <li>
                <a href="#contact">Contact</a>
              </li>
            </ul>
          </nav>
        </header>

        {/* Hero Section */}
        <section id="home" className="hero-section">
          <div className="hero-content">
            <h1>한동 피드에 오신 것을 환영합니다</h1>
            <p>한동의 모든 정보통을 모아두었습니다</p>
            {authData ? (
              <a href="/" className="cta-btn">
                한동 피드로 이동하기
              </a>
            ) : (
              <span className="glogin">
                <GoogleLoginComponent noRedirect={noRedirect} />
              </span>
            )}
          </div>
        </section>

        {/* Features Section */}
        <section id="features" className="features-section">
          <h2>Features</h2>
          <div className="features-grid">
            <div className="feature-card">
              <h3>정보가 한곳에</h3>
              <p>
                실명카톡방, 히즈넷 (예정) 공지사항을 한곳에서 볼 수 있습니다.
              </p>
            </div>
            <div className="feature-card">
              <h3>학생들이 직접 만드는</h3>
              <p>
                오픈소스로 관리가 되어 학생들이 직접 만들고 수정하는
                프로젝트입니다
              </p>
            </div>
            <div className="feature-card">
              <h3>보안</h3>
              <p>학교 이메일로 인증받은 사람만 이용할 수 있습니다.</p>
            </div>
          </div>
        </section>

        {/* Contact Section */}
        <section id="contact" className="contact-section">
          <h2>Contact Us</h2>
          <p>문의사항이 있으시면 아래 메일로 보내주세요</p>
          <a href="mailto:feed@handong.app" className="cta-btn">
            feed@handong.app
          </a>
        </section>

        {/* Footer */}
        <footer className="footer">
          <p>© 2024 Handong Feed. All rights reserved.</p>
        </footer>
      </div>
      <div onClick={() => logout()}>Logout</div>
    </>
  );
};

export default MainScreen;
