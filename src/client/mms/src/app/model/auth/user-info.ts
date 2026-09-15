import { JwtPayload } from "jwt-decode";

export interface UserInfo extends JwtPayload {
  id?: string;
  fullname?: string;
  email?: string;
  roles?: string[];
  expiry?: Date;
}
