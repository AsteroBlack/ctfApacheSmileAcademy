export interface DashboardSectionsDatas {
  omci: DashboardSectionDatas;
  telco: DashboardSectionDatas;
}

export interface DashboardSectionDatas {
  uniques: KPIDatasGroup,
  TOTALS: KPIDatasGroup;
}

export interface KPIDatasGroup {
  unlocked: KPIDatas;
  locked: KPIDatas;
  inMachine: KPIDatas;
  TOTAL: KPIDatas;
}

export interface KPIDatas {
  BSCS?: string;
  CORAIL?: string;
  TOTAL?: string;
}

export interface DefaultAPIResponse {
  status: APIResponseStatus;
  hasError: boolean;
}

export interface APIResponseStatus {
  code: string;
  message: string;
}

export interface ResponseAPIGetDashboard extends DefaultAPIResponse {
  actionEffectue: string;
  item: ResponseAPIGetDashboardItemNew[];
}


export interface ResponseAPIGetDashboardItem {
  createdAt?: string;
  total?: string;
  MISE_EN_MACHINE?: string;
  category?: ResponseAPIGetDashboardItemCategory;
  BLOQUER?: string;
  DEBLOQUER?: string;
}

export interface ResponseAPIGetDashboardItemNew {
  SIMSWAP?: ResponseTypeCategory;
  N_SIMSWAP?: ResponseTypeCategory;
}


export interface ResponseTypeCategory {
  OMCI?: ResponseAPIGetDashboardItem;
  TELCO?: ResponseAPIGetDashboardItem
}


export enum ResponseAPIGetDashboardItemCategory {
  OMCI = "OMCI",
  TELCO = "TELCO"
}
