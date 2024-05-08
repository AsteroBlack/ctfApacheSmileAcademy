export interface RequestGetListParametrages {
  user: number;
  isSimpleLoading: boolean;
  data: DataRequestGetListParametrages;
}

export interface DataRequestGetListParametrages {
  id?: string;
  createdAt?: string;
  createdBy?: string;
  delaiAttente?: string;
  isDeleted?: string;
  updatedAt?: string;
  updatedBy?: string;
  idTypeParametrage?: string;
}


export interface ResponseGetListParametrages {
  status: ResponseStatus;
  hasError: boolean;
  count: number;
  items: ParametrageGetListParametrages[];
  filePathDoc: string;
}

export interface ParametrageGetListParametrages {
  createdAt: string;
  createdBy: number;
  data: ParametrageData;
  id: number;
  idTypeParametrage: number;
  isDeleted: boolean;
  typeParametrageCode: string;
  updatedAt: string;
  updatedBy: number;
}

export interface ParametrageData {
  datas: ParametrageDataDataElement[];
  user: string;
}

export interface ParametrageDataDataElement {
  id: number;
  idTypeParametrage: number;
  hours: Hour[];
}

export interface Hour {
  key: string;
  values: number[];
}

export interface ResponseStatus {
  code?: string;
  message?: string;
}
