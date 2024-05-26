import React, { FC, ReactElement } from "react";

interface Props {
  path: string | string[];
  component: FC;
  fallback: ReactElement;
  exact?: boolean;
  isAllow: boolean;
}
const FallbackRoute = ({
  component: Component,
  fallback: Fallback,
  isAllow
}: Props) => {
  return isAllow ? <Component /> : Fallback;
};
export default FallbackRoute;