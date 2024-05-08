export interface DiffusionInfos {
  id?: number;
  idParametrage?: number;
  fullname: string;
  email: string;
  tel: string;
}

export interface RequestBodyGetListDiffusionInfos {
  user: number;
  isSimpleLoading: boolean;
  data: {
    id?: string;
    createdAt?: string;
    createdBy?: string;
    email?: string;
    isDeleted?: string;
    nomPrenom?: string;
    updatedAt?: string;
    idParametrage?: string;
    numero?: string;
  }
}

export interface RequestBodyCreateUpdateDiffusionInfos {
  user: number;
  datas: {
    id?: number
    email: string;
    nomPrenom: string;
    idParametrage: number;
    numero: string;
  }[];
}

export interface RequestBodyDeleteDiffusionInfos {
  user: number;
  datas: {
    id: number;
  }[];
}


export interface ResponseGetListDiffusionInfos {
  status: ResponseStatus;
  hasError: boolean;
  count: number;
  items: ResponseDiffusionInfos[];
}

export interface ResponseDefault{
  status: ResponseStatus;
  hasError: boolean;
  items?: any;
}

export interface ResponseStatus {
  code?: string;
  message?: string;
}

export interface ResponseDiffusionInfos {
  createdAt: string;
  createdBy: number;
  email: string;
  id: number;
  idParametrage: number;
  isDeleted: boolean;
  nomPrenom: string;
  numero: string;
}

