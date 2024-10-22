import { useRecoilState } from "recoil";
import { feedCountAtom } from "../recoil/feedAtom";
import { useFetchBe } from "../tools/api";
import { useEffect } from "react";

export const useFeedCount = () => {
  const [count, setCount] = useRecoilState(feedCountAtom);
  const fetchBe = useFetchBe();

  const getCount = async () => {
    const data = await fetchBe("/kafeed/count");
    setCount(data.count);
  };

  useEffect(() => {
    // Update badge as well
    if (navigator.setAppBadge) {
      // Display the number of unread messages.
      navigator.setAppBadge(Math.max(count, 0));
    }
  }, [count]);

  if (count === -1) {
    getCount();
  }

  return [Math.max(count, 0), getCount];
};
