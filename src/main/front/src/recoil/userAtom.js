import { atom } from "recoil";

export const userDetailAtom = atom({
  key: "userDetail", // unique ID (with respect to other atoms/selectors)
  default: null, // default value (aka initial value)
});
