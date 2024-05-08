import { Role } from './role';

export class User {
  id?: number;
  img?: string;
  username?: string;
  categoryCode?: string
  password?: string;
  firstName?: string;
  nom?:string;
  prenom?:string;
  fonctionnaliteData?:any;
  lastName?: string;
  role?: Role;
  token?: string;
  profilLibelle?:string
}
