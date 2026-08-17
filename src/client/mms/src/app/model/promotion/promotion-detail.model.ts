import { AuditModel } from "../../shared/model/audit.model";
import { PromotionModel } from "./promotion.model";

export interface PromotionDetailModel extends PromotionModel {
  description: string;
  image: string;
  audit?: AuditModel;
}
