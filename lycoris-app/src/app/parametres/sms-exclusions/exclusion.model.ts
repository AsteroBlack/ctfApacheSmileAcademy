import { HttpDefaultResponse } from "src/app/core/service/rest-client.service";

export interface Exclusion {
  day: string;
  times: ExclusionTime[]
}

export interface ExclusionTime {
  type: ExclusionTimeType;
  hour?: number;
  period?: ExclusionTimePeriod;
}

export interface ExclusionTimePeriod { startHour: number, endHour: number }

export enum ExclusionTimeType {
  HOUR,
  PERIOD
}

export interface ResponseExclusion {
  id: number,
  exclusions: Exclusion[]
}

export interface RequestBodyParametrageList {
  user: number;
  isSimpleLoading: boolean;
  data: RequestBodyParametrageListData;
}

export interface RequestBodyParametrageListData {
  id?: string;
  createdAt?: string;
  createdBy?: string;
  delaiAttente?: string;
  isDeleted?: string;
  updatedAt?: string;
  updatedBy?: string;
  idTypeParametrage?: string;
}


export interface RequestBodyUpdateParametrage {
  user: number;
  datas: RequestBodyUpdateParametrageData[];
}

export interface RequestBodyUpdateParametrageData {
  id: number;
  idTypeParametrage: number;
  hours: RequestBodyUpdateParametrageDataHour[];
}

export interface RequestBodyUpdateParametrageDataHour {
  key: string;
  plages: RequestBodyUpdateParametrageDataHourPlage[]
}

export interface RequestBodyUpdateParametrageDataHourPlage {
  values: number[]
}

export interface ResponseParametrageList extends HttpDefaultResponse {
  count: number;
  items: ResponseParametrageListItem[];
}

export interface ResponseParametrageListItem {
  createdAt: string;
  createdBy: number;
  data: ResponseParametrageListItemData;
  id: number;
  idTypeParametrage: number;
  isDeleted: boolean;
  typeParametrageCode: string;
  updatedAt: string;
  updatedBy: number;
}

export interface ResponseParametrageListItemData {
  datas: ResponseParametrageListItemDataElement[];
  user: string;
}

export interface ResponseParametrageListItemDataElement {
  id: number;
  idTypeParametrage: number;
  hours: ResponseParametrageListItemDataElementHour[];
}

export interface ResponseParametrageListItemDataElementHour {
  key: string;
  plages: ResponseParametrageListItemDataElementHourPlage[];
}


export interface ResponseParametrageListItemDataElementHourPlage{
  values: number[]
}
