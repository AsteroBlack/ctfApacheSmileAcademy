export interface RequestBodyGetList {
  user:            number;
  isSimpleLoading: boolean;
  data:            Partial<Data>;
}

export interface Data {
  id:        string;
  libelle:   string;
  createdAt: string;
  createdBy: string;
  updatedAt: string;
  updatedBy: string;
  isDeleted: string;
}

export interface ResponseGetList {
  status:   Status;
  hasError: boolean;
  count:    number;
  items:    ResponseTypeNumero[];
}

export interface ResponseTypeNumero {
  createdAt: string;
  createdBy: number;
  id:        number;
  isDeleted: boolean;
  libelle:   TypeNumeroLabel;
}

export enum TypeNumeroLabel{
  BSCS = "BSCS",
  CORAIL= "CORAIL"
}

export interface Status {
  code: string;
  message: string;
}
