export const serverRootUrl =
  !process.env.NODE_ENV || process.env.NODE_ENV === "development"
    ? "/api"
    : "/api";

export const googleClientId =
  "542164017545-c6tsebe44kpkpk43b7u8njouir63oqq8.apps.googleusercontent.com";
