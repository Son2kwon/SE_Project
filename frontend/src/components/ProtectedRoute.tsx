import React, { FC } from "react";

interface Props {
  path: string | string[];
  component: FC;
  fallback: FC;
  exact?: boolean;
  isAllow: boolean;
}
const ProtectedRoute = ({
  component: Component,
  fallback: Fallback,
  isAllow
}: Props) => {
  return isAllow ? <Component /> : <Fallback />;
};
export default ProtectedRoute;